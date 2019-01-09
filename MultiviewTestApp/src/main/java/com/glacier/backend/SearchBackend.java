package com.glacier.backend;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.glacier.enums.BadMessages;
import com.glacier.util.Constants;

public class SearchBackend {
	
	@SuppressWarnings("unchecked")
	public static JSONObject getSpell(String searchTerm)
	{
		try {
			JSONObject ret = new JSONObject();
			String url = (String) getSpellURLFromSearchTerm(searchifySpell(searchTerm)).get("url");
			if(url.contains(BadMessages.NO_RESULTS.toString()) || url.contains(BadMessages.INTERNET_TROUBLE.toString()))
			{
				ret.put("description", BadMessages.BAD_DESCRIPTION.toString());
				ret.put("dice", BadMessages.BAD_DICE.toString());
			}
			else
			{
				String desc = getFullDescriptionFromSpellURL(url);
				if(desc.contains(BadMessages.BAD_DESCRIPTION.toString()) || desc.contains(BadMessages.INTERNET_TROUBLE.toString()))
				{
					ret.put("description", BadMessages.BAD_DESCRIPTION.toString());
					ret.put("dice", BadMessages.BAD_DICE.toString());
				}
				else
				{
					ret.put("description", CharacterBackend.cleanOutWeirdness(desc));
					String dice = "";
					String[] dicearr = extractDiceFromSpellDescription(desc);
					for(int i = 0; i<dicearr.length;i++)
					{
						if(i!= dicearr.length-1 || i!= dicearr.length-2)
						{
							dice+=dicearr[i];
						}
						else
						{
							dice+=" "+dicearr[i];
						}
					}
					ret.put("dice", dice);
				}
			}
			ret.put("name", searchTerm);
			return ret;
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
			return new JSONObject();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return new JSONObject();
		}
	}

	/**
	 * In short, capitalize every word, then replace the spaces with pluses
	 * @param s the spell that we're making it a good search string
	 * @return the spell, with every word capitalized, and all the spaces replaced with pluses
	 */
	private static String searchifySpell(String s) {
		String ret = "";
		String[] words = s.toLowerCase().split(" ");
		for(String x : words)
		{
			String temp = "";
			if(!x.equalsIgnoreCase("of"))
			{
				for(int i = 0; i<x.length();i++)
				{
					if(i==0)
					{
						temp+=x.toUpperCase().toCharArray()[i];
					}
					else
					{
						temp+=x.toCharArray()[i];
					}
				}
			}
			else
			{
				temp="of";
			}
			ret+=temp+"+";
		}
		return ret.substring(0,ret.length()-1);
	}

	/**
	 * Uses some BONKERS regular expressions in order to pull anything matching standard damage rolls
	 * out of a full spell description. (i.e. 2d6 cold damage)
	 * @param fullDesc the full spell description
	 * @return an array of strings that contain each dice roll from the description
	 */
	public static String[] extractDiceFromSpellDescription(String fullDesc) {
		ArrayList<String> arr = new ArrayList<String>();
		// this string is called bonkersRegex for a reason
		// I spent a good solid hour checking and tinkering with this regex that'll
		// match any damage as long as it goes
		// dice damage type "damage"
		// i.e. 2d6 cold damage
		// or 1d4 + 1 force damage
		// etc
		Pattern pattern = Pattern.compile(Constants.getBonkersRegex());
		Matcher matcher = pattern.matcher(fullDesc);
		while (matcher.find()) {
			arr.add(matcher.group());
		}
		return arr.toArray(new String[arr.size()]);
	}
	
	/**
	 * Returns the full description of the spell, which is in a string array for some reason
	 * We split the array of strings with a newline character for displaying the data purposes
	 * @param url the url we're getting the description from
	 * @return the spell description from the URL
	 * @throws IOException An exception in the connecting/handling the URL handed to us 
	 * @throws ParseException An exception in the parsing of the data we get handed from the URL
	 */
	@SuppressWarnings("unchecked")
	private static String getFullDescriptionFromSpellURL(String url) throws IOException, ParseException 
	{
		URL Url = new URL(url);
		HttpURLConnection con = (HttpURLConnection) Url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int responseCode = con.getResponseCode();
		if (responseCode != 200) 
		{
			return BadMessages.INTERNET_TROUBLE.toString();
		}
		String toParse = "";
		Scanner datascan = new Scanner(Url.openStream());
		while (datascan.hasNextLine()) 
		{
			toParse += datascan.nextLine();
		}
		datascan.close();
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(toParse);
		String ret = "";
		for (Object temp : (JSONArray) obj.getOrDefault("desc", BadMessages.BAD_DESCRIPTION.toString())) 
		{
			ret += (String) temp + "\n";
		}
		// for now we're just getting the description
		// perhaps at some point we'll dig into the higher level casts
		// and the improvement of cantrips based on level
		// but for now it just gets the damage based on the initial description
		return ret;
	}

	
	/**
	 * Returns the URL that we actually need to find from a given search term
	 * @param spell the spell we're searching for
	 * @return a JSONObject containing the 1 item: the URL. Either that, or a message about what went wrong. The key is url
	 * @throws ParseException thrown when the jsonparser can't parse the data we get from the api
	 * @throws IOException thrown when the connection isn't working quite right
	 */
	private static JSONObject getSpellURLFromSearchTerm(String spell) throws ParseException, IOException
	{
		URL url = new URL("http://www.dnd5eapi.co/api/spells/?name=" + spell);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int responseCode = con.getResponseCode();
		if (responseCode != 200) 
		{
			JSONObject obj = (JSONObject) new JSONParser().parse("{\"url\":\""+ BadMessages.INTERNET_TROUBLE +"\"}");
			return obj;
		}
		String toParse = "";
		Scanner datascan = new Scanner(url.openStream());
		while (datascan.hasNextLine()) 
		{
			toParse += datascan.nextLine();
		}
		datascan.close();
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(toParse);
		JSONArray array = (JSONArray) obj.get("results");
		if ((Long) obj.get("count") == 0) 
		{
			obj = (JSONObject) parser.parse("{\"url\":\""+ BadMessages.NO_RESULTS+"\"}");
		}
		else
		{
			obj = (JSONObject) array.get(0);
		}
		return obj;
	}
}
