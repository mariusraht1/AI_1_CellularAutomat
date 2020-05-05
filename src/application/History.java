package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import application.Utilities.OSType;

public class History {
    private static History instance;

    public static History getInstance() {
	if (instance == null) {
	    instance = new History();
	}

	return instance;
    }

    private List<int[]> population = new ArrayList<int[]>();

    private File file = new File("history.txt");

    public File getFile() {
	return file;
    }

    private History() {
    };

    public void clear() {
	population.clear();
    }
    
    public void add(int numOfPredator, int numOfPrey) {
	population.add(new int[] { numOfPredator, numOfPrey });
    }

    public void export() {
	try {
	    StringBuilder stringBuilder = new StringBuilder();
	    for (int[] x : population) {
		for (int i = 0; i < x.length; i++) {
		    stringBuilder.append(x[i]);
		    if (i < x.length - 1) {
			stringBuilder.append(",");
		    }
		}
		stringBuilder.append("\n");
	    }

	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    writer.append(stringBuilder);
	    writer.close();

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void showExport() {
	try {
	    if (Utilities.getInstance().getOperatingSystemType().equals(OSType.Windows)) {
		 Runtime.getRuntime().exec("explorer.exe /select, " + file);
	    } else {
		Desktop.getDesktop().open(History.getInstance().getFile());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
