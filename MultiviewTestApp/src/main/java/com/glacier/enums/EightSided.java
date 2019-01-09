package com.glacier.enums;

public enum EightSided {

	ONE("/1d8 1.png"),
	TWO("/1d8 2.png"),
	THREE("/1d8 3.png"),
	FOUR("/1d8 4.png"),
	FIVE("/1d8 5.png"),
	SIX("/1d8 6.png"),
	SEVEN("/1d8 7.png"),
	EIGHT("/1d8 8.png");
	
	private String filename;
	
	EightSided(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
	
}
