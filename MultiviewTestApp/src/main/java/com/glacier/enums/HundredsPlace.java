package com.glacier.enums;

public enum HundredsPlace {
	ZERO("/1d100 00.png"),
	TEN("/1d100 10.png"),
	TWENTY("/1d100 20.png"),
	THIRTY("/1d100 30.png"),
	FORTY("/1d100 40.png"),
	FIFTY("/1d100 50.png"),
	SIXTY("/1d100 60.png"),
	SEVENTY("/1d100 70.png"),
	EIGHTY("/1d100 80.png"),
	NINETY("/1d100 90.png");
	
	String filename;
	
	HundredsPlace(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
