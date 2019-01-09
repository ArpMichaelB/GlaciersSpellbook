package com.glacier.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.glacier.util.Constants;

public class CharacterBackend {
	public static ArrayList<String> getCharacterNames()
	{
		ArrayList<String> ret = new ArrayList<String>();
		try {
			File storage = Constants.spellbookFile().getParentFile();
			File[] files = storage.listFiles();
			for(File f : files)
			{
				if(f.getName().contains("-spellbook.json") && !(f.getName().endsWith("/-spellbook.json") || f.getName().endsWith("\\-spellbook.json")))
				{
					ret.add(f.getName().substring(0,f.getName().lastIndexOf("-")));
				}
				else if(f.getName().endsWith("/-spellbook.json") || f.getName().endsWith("\\-spellbook.json"))
				{
					System.out.println("We've made a nameless character, heck");
				}
			}
			//for every file in the directory where we keep spellbooks, if the file is a spellbook
			//add the name of the character to the list
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return ret;
	}
	
	public static void deleteCharacter(String s)
	{
		try 
		{
			Constants.spellbookFile().delete();
			ArrayList<String> characters = getCharacterNames();
			if(!characters.isEmpty())
			{
				if(!characters.get(0).equals(s) && characters.size()!=1)
				{
					setCharacter(characters.get(0));
				}
				else if(characters.size()!=1)
				{
					setCharacter(characters.get(characters.size()-1));
				}
				else
				{
					setCharacter("");
				}
			}
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setCharacter(String s)
	{
		Constants.setCharacter(s);
		System.out.println("Character set to " + s);
	}
	
	public static void makeNewCharacter(String s)
	{
		try {
			Constants.setCharacter(s);
			Constants.spellbookFile().createNewFile();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void writeSpell(String name, String details)
	{
		try 
		{
			File json = Constants.spellbookFile();
			FileWriter writer = new FileWriter(json,true);
			JSONArray spellList = new JSONArray();
			JSONObject spellDetails = new JSONObject();
			String desc = cleanOutWeirdness(details);
			spellDetails.put("description", desc);
			String dice = "";
			for(String s : SearchBackend.extractDiceFromSpellDescription(details))
			{
				dice += s + " ";
			}
			spellDetails.put("dice", dice);
			JSONObject spell = new JSONObject();
			spell.put(name, spellDetails);
			spellList.add(spell);
			boolean nyoom = false;
			Scanner scan = new Scanner(new FileReader(json));
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				if(line.contains("]"))
				{
					nyoom = true;
				}
			}
			if(nyoom)
			{
				String spellListHold = "";
				Scanner s = new Scanner(new FileReader(json));
				while(s.hasNextLine())
				{
					spellListHold += s.nextLine();
				}
				spellListHold = spellListHold.substring(0, spellListHold.lastIndexOf("]"));
				spellListHold += ", "+spell.toJSONString() + "]";
				spellListHold = spellListHold.replace("\uFEFF", "");
				writer = new FileWriter(json);
				//listen. I don't know why i need to have the writer able to append to make it actually notice
				//that there's stuff in there to append to
				//then make it not append since we're rewriting the whole list
				//but oh boy i sure do have to do it that way
				writer.write(spellListHold);
				writer.flush();
				writer.close();
				s.close();
				scan.close();
			}
			//basically if there's already a spell list in the file, append the spell to the file 
			else
			{
				writer.write(spellList.toJSONString().replace("\uFEFF", ""));
				writer.flush();
				writer.close();
			}
			//otherwise write the whole list of spells to the file
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException e) 
		{	
			e.printStackTrace();
		}
	}
	
	public static String cleanOutWeirdness(String desc) 
	{
		String ret = desc;
		ret = ret.replace("Ã¢â\\u201A¬â\\u201E¢","'");
		ret = ret.replace("Ã¢â‚¬â„¢","'");
		ret = ret.replace("Ã¢â\\u201A¬â\\u20ACœ", "(");
		ret = ret.replace("Ã¢â‚¬â€œ", "(");
		ret = ret.replace("â\\u20AC™", "'");
		ret = ret.replace("â€™", "'");
		ret = ret.replace("Ã¢â\\u201A¬â\\u20AC?",", ");
		ret = ret.replace("Ã¢â‚¬â€?",", ");
		return ret;
	}
	
	public static void deleteSpell(String name)
	{
		JSONArray spells = getSpellList();
		int toRemove = -1;
		for(int i = 0; i< spells.size(); i++)
		{
			if(((JSONObject)spells.get(i)).toJSONString().contains(name))
			{
				toRemove = i;
			}
		}
		if(toRemove>=0)
		{
			spells.remove(toRemove);
			try 
			{
				FileWriter temp = new FileWriter(Constants.spellbookFile());
				temp.write(spells.toJSONString());
				temp.flush();
				temp.close();
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Didn't contain that spell");
		}
	}
	
	public static JSONArray getSpellList()
	{
		JSONArray ret = new JSONArray();
		JSONParser parse = new JSONParser();
		try {
			String temp = "";
			Object obj;
			if(Constants.spellbookFile().getName().equals("-spellbook.json"))
			{
				return ret;
			}
			Scanner scan = new Scanner(new FileReader(Constants.spellbookFile()));
			while(scan.hasNextLine())
			{
				temp+=scan.nextLine() + "\n";
			}
			if(!temp.isEmpty())
			{
				obj = parse.parse(new FileReader(Constants.spellbookFile().getAbsolutePath()));
				ret = (JSONArray) obj;
			}
			scan.close();
			return ret;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return new JSONArray();
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
			return new JSONArray();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return new JSONArray();
		}
	}
}
