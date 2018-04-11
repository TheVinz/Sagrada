package common.utilities;


public enum Color{
	RED, BLUE, GREEN, PURPLE, YELLOW;
	public Color asColor(char c){
		switch(c){
			case 'r' :
				return RED;
			case 'b' :
				return BLUE;
			case 'g' :
				return GREEN;
			case 'p' :
				return PURPLE;
			case 'y' :
				return YELLOW;
			default :
				return null;
		}
	}
}