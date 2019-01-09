package com.glacier.enums;

public enum BadMessages {
	INTERNET_TROUBLE("Some kinda internet trouble, my guy."),
	NO_RESULTS("no results found"),
	BAD_DESCRIPTION("Had some trouble finding that spell. I don't have every spell, try the custom spell entry?"),
	BAD_DICE("0d0 null damage");
	
	private String message;
	
	BadMessages()
	{
		this.message = "default bad";
	}
	
	BadMessages(String message)
	{
		this.message = message;
	}
	
	@Override
	public String toString()
	{
		return this.message;
	}
}
