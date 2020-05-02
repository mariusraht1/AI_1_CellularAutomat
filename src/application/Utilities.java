package application;

public class Utilities {
	private static Utilities instance;
	
	public static Utilities getInstance()
	{
		if (instance == null) {
			instance = new Utilities();
		}
		
		return instance;
	}
	
	protected Utilities() {}
	
	public boolean isEmpty(Cell[] cells)
	{
		if (cells.length == 0 || cells[0] == null) {
			return true;
		} else {
			return false;
		}
	}
}
