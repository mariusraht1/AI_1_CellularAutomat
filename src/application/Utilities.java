package application;

import java.util.Random;

import application.cell.Cell;

public class Utilities {
    private static Utilities instance;

    public static Utilities getInstance() {
	if (instance == null) {
	    instance = new Utilities();
	}

	return instance;
    }

    protected Utilities() {
    }

    public boolean isEmpty(Cell[] cells) {
	if (cells.length == 0 || cells[0] == null) {
	    return true;
	} else {
	    return false;
	}
    }

    public int generateRandom(int min, int max) {
	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }

    public int getArrayLength(Cell[] cells) {
	int length = 0;

	for (int i = 0; i < cells.length; i++) {
	    if (cells[i] == null) {
		break;
	    } else {
		length++;
	    }
	}

	return length;
    }
}
