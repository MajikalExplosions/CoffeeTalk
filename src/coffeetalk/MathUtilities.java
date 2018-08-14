package coffeetalk;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MathUtilities {
	
	private static final String LETTERS = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-=!@#$%^&*()_+[]\\{}|;':\",./<>?`~\n";//Symbols that the cipher supports
	
	/**
	 * Reads themes.txt and returns an array of themes
	 * @return An array of theme objects
	 */
	public static Theme[] getThemes() {
		Theme[] themes = null;
		try {
			Scanner scanner = new Scanner(new FileInputStream("data/themes.txt"));
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				String name = line.substring(0, line.indexOf("|"));
				line = line.substring(line.indexOf("|") + 1);
				
				Color p = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 255);
				line = line.substring(7);
				
				Color s2 = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 255);
				line = line.substring(7);
				
				Color a = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 255);
				
				
				themes = extendThemes(themes);
				themes[themes.length - 1] = new Theme(name, p, s2, a);
			}
			scanner.close();
			
		} catch (FileNotFoundException e) {
			System.exit(0);
		}
		
		return themes;
	}
	
	/**
	 * Converts a hexadecimal to an RGB value between 0 and 1
	 * @param n String with 2 digits
	 * @return Number between 0-1
	 */
	public static int getRGB(String n) {
		String hex = "0123456789abcdef";
		int x = 0;
		for (int i = 0; i < n.length(); i++) {
			x *= 16;
			x += hex.indexOf(n.charAt(i));
		}
		return x;
	}
	
	public static Theme[] extendThemes(Theme[] t) {
		if (t == null) {
            t = new Theme[1];
        }
		else {
			Theme[] temp = new Theme[t.length + 1];
			for (int i = 0; i < t.length; i++) {
				temp[i] = t[i];
			}
			t = temp;
		}
		return t;
	}
	
	public static String cipher(String input, int key, boolean mode) {
        String output = "";//Create output
        
        //Makes key in range (0, LETTERS.length())
        while (key < 0) key += LETTERS.length();
        key %= LETTERS.length();
        
        //Flip if decoding
        if (!mode) key = LETTERS.length() - key;
        
        //For ever letter find the index, modify it, and then add the result to the output.
        for (int i = 0; i < input.length(); i++) {
            output += LETTERS.charAt((findPos(input.charAt(i)) + key) % LETTERS.length());
        }
        return output;
    }
    
    private static int findPos(char s) {
        
        for (int i = 0; i < LETTERS.length(); i++) {
            if (LETTERS.charAt(i) == s) return i;
        }
        return -1;
    }
}
