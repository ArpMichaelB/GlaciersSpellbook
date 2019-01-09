package com.glacier.util;

import java.util.Random;

public class Utility {
	 private static Random ran = new Random(System.nanoTime());
	    /**
	     * because I'm too lazy to type math.random and remember how to get what I want out of it
	     * @param min the smallest number you want from the function
	     * @param max the high end of the range of random numbers
	     * @return random number from min to max.
	     */
	    public static int rand(int min, int max)
	    {
	        int r = ran.nextInt(max)+min;
	        ran.setSeed(ran.nextLong());
	        return r;
	    }
}
