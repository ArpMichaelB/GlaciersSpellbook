package com.glacier.enums;

public enum TwentySided {
	ONE("/1d20 1.png"),
	TWO("/1d20 2.png"),
	THREE("/1d20 3.png"),
	FOUR("/1d20 4.png"),
	FIVE("/1d20 5.png"),
	SIX("/1d20 6.png"),
	SEVEN("/1d20 7.png"),
	EIGHT("/1d20 8.png"),
	NINE("/1d20 9.png"),
	TEN("/1d20 10.png"),
	ELEVEN("/1d20 11.png"),
	TWELVE("/1d20 12.png"),
	THIRTEEN("/1d20 13.png"),
	FOURTEEN("/1d20 14.png"),
	FIFTEEN("/1d20 15.png"),
	SIXTEEN("/1d20 16.png"),
	SEVENTEEN("/1d20 17.png"),
	EIGHTEEN("/1d20 18.png"),
	NINETEEN("/1d20 19.png"),
	TWENTY("/1d20 20.png");
	
	private String filename;
	
	TwentySided(String filename)
	{
		this.filename = filename;
	}
	
	@Override
	public String toString()
	{
		return filename;
	}
}
