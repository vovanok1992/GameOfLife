package tk.vovanok.gameoflife.rules;

public class ClassicLife implements LifeRule {

	@Override
	public boolean willDie(int x) {
		if(x<2||x>3) return true;
		else return false;
	}

	@Override
	public boolean willAlive(int x) {
		if(x==3) return true;
		else return false;
	}

}
