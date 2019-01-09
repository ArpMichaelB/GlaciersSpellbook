package com.glacier.enums;

public enum SixSided {
	ONE("/1d6 1.png"),
	TWO("/1d6 2.png"),
	THREE("/1d6 3.png"),
	FOUR("/1d6 4.png"),
	FIVE("/1d6 5.png"),
	SIX("/1d6 6.png");
	
	private String filename;
	
	SixSided(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
