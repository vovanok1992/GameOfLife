package tk.vovanok.gameoflife.rules;

public class Maze implements LifeRule {

	@Override
	public boolean willDie(int x) {
		if(x<1 || x>5) return true;
		else return false;
	}

	@Override
	public boolean willAlive(int x) {
		if(x==3) return true;
		else return false;
	}

}