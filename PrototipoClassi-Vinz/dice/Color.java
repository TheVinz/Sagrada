package dice;

public enum Color{
	RED('r'), BLUE('b'), GREEN('g'), PURPLE('p'), YELLOW('y');
	private char color;
	private Color(char c){
		color=c;
	}
	@Override
	public boolean equals(Object obj){
		if(this==obj) return true;
		if(this.getClass() != obj.getClass()) return false;
		Color other= (Color) obj;
		if(this.color= other.color) return true;
		else return false;
	}
}