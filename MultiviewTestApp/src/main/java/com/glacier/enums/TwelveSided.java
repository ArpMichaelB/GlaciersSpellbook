package com.glacier.enums;

public enum TwelveSided {
	ONE("/1d12 1.png"),
	TWO("/1d12 2.png"),
	THREE("/1d12 3.png"),
	FOUR("/1d12 4.png"),
	FIVE("/1d12 5.png"),
	SIX("/1d12 6.png"),
	SEVEN("/1d12 7.png"),
	EIGHT("/1d12 8.png"),
	NINE("/1d12 9.png"),
	TEN("/1d12 10.png"),
	ELEVEN("/1d12 11.png"),
	TWELVE("/1d12 12.png");
	
	private String filename;
	
	TwelveSided(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
