package tk.vovanok.gameoflife.core;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ColorCalculator {

	Field gameField;
	List<ColorCell> tempColCells;
	List<ColorCell> tempColCellsBefore;
	Random rand;

	ColorCalculator(Field gameField) {
		this.gameField = gameField;
		tempColCells=Collections.synchronizedList(new ArrayList<ColorCell>());
		tempColCellsBefore=Collections.synchronizedList(new ArrayList<ColorCell>());
		rand=new Random();
	
	}
	
	public void calculateColors(){
		
		for (int i = 0; i < gameField.sizeX; i++) {
			for (int j = 0; j < gameField.sizeY; j++) {
				if(gameField.getCellInstance(i, j).getLiveStatus()==0) {gameField.getCellInstance(i, j).setCellColour(null); }
				else if(gameField.getCellInstance(i, j).getLiveStatus()==1 && gameField.getCellInstance(i, j).getCellColour()!=null) continue;
				else setColor(i, j);
			}
		}
		
	}

	void setColor(int x, int y) {

		loadNearbyCells(x, y);
		loadBeforeNearbyCells(x, y);

		if (gameField.getCellInstance(x, y).getCellColour() != null)
			return;

		if (isAlone(tempColCells)) {

			if (isAlone(tempColCellsBefore)) {

				gameField.getCellInstance(x, y).setCellColour(
						new Color(Math.abs(rand.nextInt(255)), Math.abs(rand
								.nextInt(255)), Math.abs(rand.nextInt(255))));
			}

			else
				gameField.getCellInstance(x, y).setCellColour(
						getMax(tempColCellsBefore));

			return;
		}

		gameField.getCellInstance(x, y).setCellColour(getMax(tempColCells));

	}

	boolean isAlone(List<ColorCell> tempColCells){
		
		if(tempColCells.size()==0) return true;
		else return false;
	}
	
	void addColor(ColorCell col,List<ColorCell> tempColCells){
		for(int i=0;i<tempColCells.size();i++){
			if(tempColCells.get(i).getCol()!=null && col.getCol()!=null && tempColCells.get(i).getCol().equals(col.getCol())){ tempColCells.get(i).inc(); return;}
		}
		if(col.getCol()!=null) tempColCells.add(col);
	}
	
	void loadNearbyCells(int x, int y) {
		
		tempColCells.clear();
		
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y-1)
				.getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y).getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y+1)
				.getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x, y - 1)
				.getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x, y + 1)
				.getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y + 1)
				.getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y).getCellColour()),tempColCells);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y - 1)
				.getCellColour()),tempColCells);
	}
	
	void loadBeforeNearbyCells(int x, int y) {
		
		tempColCellsBefore.clear();
		
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y-1)
				.getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y).getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x - 1, y+1)
				.getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x, y - 1)
				.getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x, y + 1)
				.getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y + 1)
				.getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y).getBeforeColour()),tempColCellsBefore);
		addColor(new ColorCell(gameField.getCellInstance(x+1, y - 1)
				.getBeforeColour()),tempColCellsBefore);
	}

	Color getMax(List<ColorCell> tempColCells) {

		ColorCell temp = tempColCells.get(0);

		for (ColorCell cc : tempColCells) {
			if (cc.getCount() > temp.getCount())
				temp = cc;
		}

		return temp.getCol();

	}
}

class ColorCell {

	private Color col;
	int count;

	int getCount(){
		return count;
	}
	
	void inc(){
		count++;
	}
	
	public ColorCell(Color col) {
		count = 0;
		this.setCol(col);
	}

	public Color getCol() {
		return col;
	}

	void setCol(Color col) {
		this.col = col;
	}
	
	@Override
	public String toString() {
	return col.toString()+" x="+count;
	}
	

}