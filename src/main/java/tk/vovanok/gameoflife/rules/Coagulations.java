package tk.vovanok.gameoflife.rules;

public class Coagulations implements LifeRule {

	@Override
	public boolean willDie(int x) {
		if(!(x==2 || x==3 || x==5|| x==6 || x==7|| x==8)) return true;
		else return false;
	}

	@Override
	public boolean willAlive(int x) {
		if(x==3||x==7||x==8) return true;
		else return false;
	}

}
