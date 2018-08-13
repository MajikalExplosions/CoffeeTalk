package coffeetalk;

import java.io.Serializable;

public class DataStore implements Serializable {
	public String username;
	public Theme[] themes;
	public int port;
	public final int[] presetKeys = {
		34,	
		-12,	
		68,
		44,
		-56
	};
	public int currentTheme = -1;
}
