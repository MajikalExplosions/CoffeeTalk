package coffeetalk;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MathUtilities {
	
	/**
	 * Reads themes.txt and returns an array of themes
	 * @return An array of theme objects
	 */
	public static Theme[] getThemes() {
		Theme[] themes = null;
		try {
			Scanner s = new Scanner(new FileInputStream("themes.txt"));
			while(s.hasNext()) {
				String line = s.nextLine();
				String name = line.substring(0, line.indexOf("|"));
				line = line.substring(line.indexOf("|") + 1);
				
				Color p = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 0);
				line = line.substring(7);
				
				Color s2 = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 0);
				line = line.substring(7);
				
				Color a = new Color(getRGB(line.substring(0, 2)), getRGB(line.substring(2, 4)), getRGB(line.substring(4, 6)), 0);
				
				
				themes = extendThemes(themes);
				themes[themes.length - 1] = new Theme(name, p, s2, a);
			}
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
}
