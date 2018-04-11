package common.utilities;


public enum Color{
	RED('r'), BLUE('b'), GREEN('g'), PURPLE('p'), YELLOW('y');
	private char color;
	private Color(char c){
		color=c;
	}

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