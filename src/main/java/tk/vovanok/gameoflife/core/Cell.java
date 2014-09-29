package tk.vovanok.gameoflife.core;

import java.awt.Color;
import java.util.Random;

public class Cell {

    private int liveStatus;
    private Color cellColour;
    private Color beforeColour;

    private int colorLive = 0;

    Random rand = new Random();

    public Cell() {
    }

    public Cell(Cell c) {
        this.liveStatus = c.getLiveStatus();
        this.cellColour = c.getCellColour();
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Color getCellColour() {

        if (liveStatus == 0) {
            return null;
        }
        return cellColour;
    }

    void setCellColour(Color cellColour) {

        if (this.cellColour != null) {
            this.beforeColour = this.cellColour;
        }
        this.cellColour = cellColour;
    }

    public Color getBeforeColour() {
        return beforeColour;
    }

    public void setBeforeColour(Color beforeColour) {
        this.beforeColour = beforeColour;
    }

    public void reset() {
        this.liveStatus = 0;
        this.cellColour = null;
        this.beforeColour = null;
    }

    public void random() {
        this.liveStatus = 1;
        this.cellColour = new Color(Math.abs(rand.nextInt(255)), Math.abs(rand
                .nextInt(255)), Math.abs(rand.nextInt(255)));
    }

    void updateTimeColorLive() {
        if (getColorLive() < 5) {
            setColorLive(getColorLive() + 1);
        } else {
            this.beforeColour = null;
            setColorLive(0);
        }
    }

    public int getColorLive() {
        return colorLive;
    }

    public void setColorLive(int colorLive) {
        this.colorLive = colorLive;
    }
}
