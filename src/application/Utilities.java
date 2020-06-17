package application;

import java.util.Locale;
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

	private Random random = new Random();
	
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
		return random.nextInt((max - min) + 1) + min;
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

	public enum OSType {
		Windows, MacOS, Unix, Other
	};

	protected static OSType osType;

	public OSType getOperatingSystemType() {
		if (osType == null) {
			String osName = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

			if (osName.contains("mac") || osName.contains("darwin")) {
				osType = OSType.MacOS;
			} else if (osName.contains("win")) {
				osType = OSType.Windows;
			} else if (osName.contains("nux")) {
				osType = OSType.Unix;
			} else {
				osType = OSType.Other;
			}
		}

		return osType;
	}

	public int divide(int dividend, int divisor) {
		if (divisor == 0) {
			return 0;
		} else {
			return dividend / divisor;
		}
	}
}
