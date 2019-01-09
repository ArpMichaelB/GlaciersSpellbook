package com.glacier.enums;

public enum TenSided {
	ONE("/1d10 1.png"),
	TWO("/1d10 2.png"),
	THREE("/1d10 3.png"),
	FOUR("/1d10 4.png"),
	FIVE("/1d10 5.png"),
	SIX("/1d10 6.png"),
	SEVEN("/1d10 7.png"),
	EIGHT("/1d10 8.png"),
	NINE("/1d10 9.png"),
	TEN("/1d10 10.png");
	
	private String filename;
	
	TenSided(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
