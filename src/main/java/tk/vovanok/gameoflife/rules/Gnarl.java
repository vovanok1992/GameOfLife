package tk.vovanok.gameoflife.rules;

public class Gnarl implements LifeRule {

	@Override
	public boolean willDie(int x) {
		if(x!=1) return true;
		else return false;
	}

	@Override
	public boolean willAlive(int x) {
		if(x==1) return true;
		else return false;
	}

}
