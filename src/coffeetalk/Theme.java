package coffeetalk;

import java.awt.Color;

public class Theme {
	
	private String name;
	private Color primary;
	private Color secondary;
	private Color accent;
	
		
	public Theme(String n, Color p, Color s, Color a) {
		name = n;
		primary = p;
		secondary = s;
		accent = a;
	}
	
	public Theme() {
		this("Default", new Color(1, 1, 1, 0), new Color(1, 1, 1, 0), new Color(1, 1, 1, 0));
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public Color getPrimary() {
		return primary;
	}

	public void setPrimary(Color primary) {
		this.primary = primary;
	}

	public Color getSecondary() {
		return secondary;
	}

	public void setSecondary(Color secondary) {
		this.secondary = secondary;
	}

	public Color getAccent() {
		return accent;
	}

	public void setAccent(Color accent) {
		this.accent = accent;
	}
}
