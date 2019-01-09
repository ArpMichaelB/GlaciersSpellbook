package com.glacier.util;

import java.io.File;
import java.io.FileNotFoundException;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;

public class Constants {
	private static String characterName = "";
	
	private static int[] pictureDice = {4,6,8,10,12,20,100};
	
	private static String[] holdDiceForMe = {};
	
	private static String bonkersRegex = 
	"(\\d{1,})\\s*(d|D)\\s*(\\d{1,})\\s*(\\+{0,1}|-{0,1})\\s*(\\d{0,})(\\s[A-Za-z]{4,11}\\s|$)(damage|$)";
	//(\d{1,})\s*(d|D)\s*(\d{1,})\s*(\+{0,1}|-{0,1})\s*(\d{0,})(\s[A-Za-z]{4,11}\s|$)(damage|$)
	//hey you never know when you're gonna need the regex without the backslashes escaped
	public String getCharacter()
	{
		return characterName;
	}
	
	public static int[] getPictureDice()
	{
		return pictureDice;
	}
	
	public static String getBonkersRegex()
	{
		return bonkersRegex;
	}
	
	public static String[] getHeldDice()
	{
		return holdDiceForMe;
	}
	
	public static void setHeldDice(String[] heldDice)
	{
		holdDiceForMe = heldDice;
	}
	
	public static void setCharacter(String newCharacterName)
	{
		characterName = newCharacterName;
		try 
		{
			spellbookFile();
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static File spellbookFile() throws FileNotFoundException
	{
		if(Platform.isDesktop())
		{
			return new File(File.listRoots()[0] + "Glacier Nester/properties/" + characterName +"-spellbook.json");
		}
		else if(Platform.isAndroid() || Platform.isIOS())
		{
			return new File(Services.get(StorageService.class).flatMap(StorageService::getPrivateStorage).orElseThrow(() -> new FileNotFoundException("Couldn't get to storage, sorry.")).getAbsolutePath() + "/" + characterName + "-spellbook.json");
		}
		else
		{
			throw new FileNotFoundException("Unsupported Platform, guess you can carry on without saving?");
		}
	}
}
