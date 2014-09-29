package tk.vovanok.gameoflife.core;
import java.util.Random;

import tk.vovanok.gameoflife.rules.*;

public class FieldCalculator {
	
	public static final int DIE=0;
	public static final int ALIVE=1;
	public static final int WILL_ALIVE=2;
	public static final int WILL_DIE=3;
	
	public static final int RULE_CLASSIC=0;
	public static final int RULE_GNARL=1;
	public static final int RULE_MAZE = 2;
	public static final int RULE_COAGULATIONS = 3;
        
	public Field gameField;
	Field gameBeforeOneStep;
	LifeRule rule;
	ColorCalculator colCalc;
	
	
	public FieldCalculator(int x, int y) {
		rule = new ClassicLife();
		this.gameField=new Field(x,y);
		this.gameBeforeOneStep = new Field(x,y);
		colCalc = new ColorCalculator(gameField);
	}
	
    int calcNeighbors(int x,int y){
		
        int count = 0;

        if(gameField.getCell(x-1, y-1)==ALIVE||gameField.getCell(x-1, y-1)==WILL_DIE) count++;
        if(gameField.getCell(x-1, y)==ALIVE ||gameField.getCell(x-1, y)==WILL_DIE) count++;
        if(gameField.getCell(x-1, y+1)==ALIVE|| gameField.getCell(x-1, y+1)==WILL_DIE) count++;

        if(gameField.getCell(x, y-1)==ALIVE ||gameField.getCell(x, y-1)==WILL_DIE) count++;
        if(gameField.getCell(x, y+1)==ALIVE|| gameField.getCell(x, y+1)==WILL_DIE) count++;

        if(gameField.getCell(x+1, y)==ALIVE ||gameField.getCell(x+1, y)==WILL_DIE) count++;
        if(gameField.getCell(x+1, y+1)==ALIVE|| gameField.getCell(x+1, y+1)==WILL_DIE) count++;
        if(gameField.getCell(x+1, y-1)==ALIVE|| gameField.getCell(x+1, y-1)==WILL_DIE) count++;

        return count;
    }
	
    void estimate() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == ALIVE || gameField.getCell(i, j) == WILL_DIE) {
                    if (rule.willDie(calcNeighbors(i, j))) {
                        gameField.setCell(i, j, WILL_DIE);
                    }
                }

                if (gameField.getCell(i, j) == DIE || gameField.getCell(i, j) == WILL_ALIVE) {
                    if (rule.willAlive(calcNeighbors(i, j))) {
                        gameField.setCell(i, j, WILL_ALIVE);
                    }

                }
            }
        }
    }
	
    public void calcCols() {
        colCalc.calculateColors();
    }

    public void calculate() {
        estimate();
        decrementColorsLifeTime();
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == WILL_DIE) {
                    gameField.setCell(i, j, DIE);
                    gameField.getCellInstance(i, j).setCellColour(null);
                }

                if (gameField.getCell(i, j) == WILL_ALIVE) {
                    gameField.setCell(i, j, ALIVE);
                }
            }
        }
        colCalc.calculateColors();
    }


    private void decrementColorsLifeTime() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (gameField.getCell(i, j) == DIE) {
                    gameField.getCellInstance(i, j).updateTimeColorLive();
                }

            }
        }
    }

    public Cell[][] getGameArray() {
        return gameField.getGameArray();
    }

    public void reverseGameArrayValue(int x, int y) {

        if (gameField.getCell(x, y) == ALIVE) {
            gameField.clearCell(x, y);
        } else {
            gameField.setCell(x, y, ALIVE);
        };

    }
    
    public void erase(int x, int y, int size){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                gameField.clearCell(x + i - (size / 2), y + j - (size / 2));
            }
        }
            
    }

    public void randomize() {

        Random r = new Random();

        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                if (r.nextInt(5) == 1) {
                    gameField.setRandCell(i, j);
                }

            }
        }

    }

    public void clearField() {
        for (int i = 0; i < gameField.sizeX; i++) {
            for (int j = 0; j < gameField.sizeY; j++) {

                gameField.clearCell(i, j);

            }
        }

    }

    public void setRule(int ruleClassic) {

        switch (ruleClassic) {

            case RULE_CLASSIC:
                rule = new ClassicLife();
                break;
            case RULE_GNARL:
                rule = new Gnarl();
                break;
            case RULE_MAZE:
                rule = new Maze();
                break;
            case RULE_COAGULATIONS:
                rule = new Coagulations();
                break;
        }

    }

    public boolean isAlive(int x, int y) {
        return gameField.getCell(x, y) == ALIVE;
    }
    
    

}

