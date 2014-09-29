package tk.vovanok.gameoflife.rules;

public interface LifeRule {
	boolean willDie(int x);
	boolean willAlive(int x);
}

