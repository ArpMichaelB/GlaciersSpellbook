package com.glacier.backend;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glacier.enums.EightSided;
import com.glacier.enums.FourSided;
import com.glacier.enums.HundredsPlace;
import com.glacier.enums.SixSided;
import com.glacier.enums.TenSided;
import com.glacier.enums.TwelveSided;
import com.glacier.enums.TwentySided;
import com.glacier.util.Constants;
import com.glacier.util.Utility;
import com.gluonhq.charm.down.Platform;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DiceBackend 
{
	public static VBox getResult(String dice) throws URISyntaxException
	{
		VBox resultsHolder = new VBox();
        String[] setsOfDice = SearchBackend.extractDiceFromSpellDescription(dice);
        //reusing that method is 110% why it's public
        //i should probably put it in the utility class honestly
        for(String s : setsOfDice)
        {
        	Text results = new Text("yoooo");
        	results.setId("results");
        	int[] dicedeets = getDetails(s);
			String numberHolder = "You Rolled:\n";
	        int numdice = dicedeets[0];
	        int numface = dicedeets[1];
	        int bonus = dicedeets[2];
	        int total = 0;
	        //get the number of dice being rolled and the number of faces each die has
	        if(!Platform.isDesktop())
	        {
		        for(int i = 0; i <numdice; i++)
		        {
		            int temp = Utility.rand(1,numface);
		            numberHolder += temp;
		            numberHolder += "\n";
		            total+=temp;
		        }
		        //however many dice are being rolled, pick a number between 1 and the number of faces each die has at random
		        //i.e. roll the dice
		        //then add the number to a holder
		        numberHolder+="Your total is: ";
		        if(s.contains("-"))
		        {
		        	total-=bonus;
		        }
		        else
		        {
		        	total += bonus;
		        }
		        numberHolder+=(total);
		        numberHolder+=getDiceType(s);
		        results.setText(numberHolder);//make the results text part of the display
		        resultsHolder.getChildren().add(results);
			}
	        //I spent TOO MUCH TIME on the images to not include them
	        //but they crash the app on android so,,, I guess only on desktop
	        else
	        {
	        	Arrays.sort(Constants.getPictureDice());
	            if(Arrays.binarySearch(Constants.getPictureDice(),numface) < 0)
	            {
	    	        //get the number of dice being rolled and the number of faces each die has
	    	        for(int i = 0; i <numdice; i++)
	    	        {
	    	            int temp = Utility.rand(1,numface);
	    	            numberHolder += temp;
	    	            numberHolder += "\n";
	    	            total+=temp;
	    	        }
	    	        //however many dice are being rolled, pick a number between 1 and the number of faces each die has at random
	    	        //i.e. roll the dice
	    	        //then add the number to a holder
	    	        numberHolder+="Your total is: ";
	    	        if(s.contains("-"))
	    	        {
	    	            numberHolder+=(total-bonus);
	    	        }
	    	        else
	    	        {
	    	        	numberHolder+=(total+bonus);
	    	        }
	    	        numberHolder+=getDiceType(s);
	    	        results.setText(numberHolder);//make the results text part of the display
	    	        resultsHolder.getChildren().add(results);
	    		}
	            //if it's not a number with photos, just spit out the numbers straight up and down
		        else if(numface == 4)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            //if option 1
			            if(Utility.rand(1,3) == 1)
			            {
			            	switch(temp)
			            	{
			            	case 1:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.ONE_OPTION_ONE.toString())));
			            		break;
			            	case 2:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.TWO_OPTION_ONE.toString())));
			            		break;
			            	case 3: 
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.THREE_OPTION_ONE.toString())));
			            		break;
			            	case 4:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.FOUR_OPTION_ONE.toString())));
			            		break;
			            	}
			            }
			            //if option 2
			            else if(Utility.rand(1,3) == 2)
			            {
			            	switch(temp)
			            	{
			            	case 1:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.ONE_OPTION_TWO.toString())));
			            		break;
			            	case 2:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.TWO_OPTION_TWO.toString())));
			            		break;
			            	case 3: 
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.THREE_OPTION_TWO.toString())));
			            		break;
			            	case 4:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.FOUR_OPTION_TWO.toString())));
			            		break;
			            	}
			            }
			            //if option 3
			            else if(Utility.rand(1,3) == 3)
			            {
			            	switch(temp)
			            	{
			            	case 1:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.ONE_OPTION_THREE.toString())));
			            		break;
			            	case 2:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.TWO_OPTION_THREE.toString())));
			            		break;
			            	case 3: 
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.THREE_OPTION_THREE.toString())));
			            		break;
			            	case 4:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.FOUR_OPTION_THREE.toString())));
			            		break;
			            	}
			            }
			            else
			            {
			            	switch(temp)
			            	{
			            	case 1:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.ONE_OPTION_ONE.toString())));
			            		break;
			            	case 2:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.TWO_OPTION_ONE.toString())));
			            		break;
			            	case 3: 
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.THREE_OPTION_ONE.toString())));
			            		break;
			            	case 4:
			            		imgV.setImage(new Image(Object.class.getResourceAsStream(FourSided.FOUR_OPTION_ONE.toString())));
			            		break;
			            	}
			            }
			            //for some reason this last option is necessary, i seem to have hecked up
			            //oh well, so it'll pick option one a bit more often, nbd
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	results.setText("Your total is: " + total+getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        //if it's a d4, pick from one of three options for each roll 1-4 for the image
		        else if(numface == 6)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            imgV.setImage(new Image(Object.class.getResourceAsStream(SixSided.values()[temp-1].toString())));
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	results.setText("Your total is: " + total + getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        else if(numface == 8)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            imgV.setImage(new Image(Object.class.getResourceAsStream(EightSided.values()[temp-1].toString())));
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	total+=bonus;
		        	results.setText("Your total is: " + total + getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        else if(numface == 10)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            imgV.setImage(new Image(Object.class.getResourceAsStream(TenSided.values()[temp-1].toString())));
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	results.setText("Your total is: " + total + getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        else if(numface == 12)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            imgV.setImage(new Image(Object.class.getResourceAsStream(TwelveSided.values()[temp-1].toString())));
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	results.setText("Your total is: " + total + getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        else if(numface == 20)
		        {
		        	for(int i = 0; i <numdice; i++)
			        {
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            int temp = Utility.rand(1,numface);
			            imgV.setImage(new Image(Object.class.getResourceAsStream(TwentySided.values()[temp-1].toString())));
			            resultsHolder.getChildren().add(imgV);
			            total+=temp;
			        }
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	results.setText("Your total is: " + total + getDiceType(s));
		        	resultsHolder.getChildren().add(results);
		        }
		        //if it's a d6, d8, d10, d12, or d20, show the corresponding image
		        else if(numface == 100)
		        {
		        	for(int i = 0; i<numdice;i++)
		        	{
		        		HBox aech = new HBox();
		        		ImageView imgV = new ImageView();
		        		imgV.setFitWidth(100);
		        		imgV.setFitHeight(100);
			            ImageView imgVTwo = new ImageView();
			            imgVTwo.setFitHeight(100);
			            imgVTwo.setFitWidth(100);
		        		int temp = Utility.rand(1,numface);
		        		if(temp == 100)
		        		{
		        			imgV.setImage(new Image(Object.class.getResourceAsStream(HundredsPlace.ZERO.toString())));
		        			imgVTwo.setImage(new Image(Object.class.getResourceAsStream(TenSided.ONE.toString())));
		        		}
		        		else
		        		{
				            //since we know integer division truncates, we can just divide temp by 10 to get the first half of the percentile
		        			imgV.setImage(new Image(Object.class.getResourceAsStream(HundredsPlace.values()[temp/10].toString())));
		        			imgVTwo.setImage(new Image(Object.class.getResourceAsStream(TenSided.values()[temp%10].toString())));
		        		}
		        		aech.getChildren().addAll(imgV,imgVTwo);
			            resultsHolder.getChildren().add(aech);
		        		total+=temp;
			            aech = new HBox();
			            imgV = new ImageView();
			            imgVTwo = new ImageView();
		        	}
		        	if(s.contains("-"))
			        {
			        	total-=bonus;
			        }
			        else
			        {
			        	total += bonus;
			        }
		        	resultsHolder.getChildren().add(new Text("Your total is: " + total + getDiceType(s)));
		        }
		        //if it's a d100, show the two corresponding d10s
	        }
        }
        return resultsHolder;
	}

	private static String getDiceType(String dice) {
		String ret;
		Pattern pattern = Pattern.compile(Constants.getBonkersRegex());
		Matcher matcher = pattern.matcher(dice);
		if(matcher.matches())
		{
			ret = " " + matcher.group(6) +" "+ matcher.group(7);
		}
		else
		{
			ret = "";
		}
		return ret;
	}

	private static int[] getDetails(String dice) {
		int[] ret = new int[3];
		Pattern pattern = Pattern.compile(Constants.getBonkersRegex());
		Matcher matcher = pattern.matcher(dice);
		if(matcher.matches())
		{
			if(!matcher.group(5).isEmpty())
			{
				ret[2] = Integer.parseInt(matcher.group(5));
				ret[1] = Integer.parseInt(matcher.group(3));
				ret[0] = Integer.parseInt(matcher.group(1));
			}
			else
			{
				ret[2] = 0;
				ret[1] = Integer.parseInt(matcher.group(3));
				ret[0] = Integer.parseInt(matcher.group(1));
			}
		}
		else
		{
			System.out.println("no dice?");
		}
		return ret;
	}
}
