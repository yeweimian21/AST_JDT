package edu.ncsu.csc.itrust;

import java.util.Random;

/**
 * Generates a random string of characters
 * 
 *  
 * 
 */
public class RandomPassword {
	private static final Random rand = new Random();

	/**
	 * Returns a string of random characters
	 * 
	 * @return a string of random characters
	 */
	public static String getRandomPassword() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			buf.append((char) (rand.nextInt(26) + 'a'));
		}
		return buf.toString();
	}
}
