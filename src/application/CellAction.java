package application;

public class CellAction {
	private static CellAction instance;
	
	public static CellAction getInstance()
	{
		if (instance == null) {
			instance = new CellAction();
		}
		
		return instance;
	}
	
	protected CellAction() {}
	
	// TODO Cell dies before it could reproduce itself
	public void step()
	{
		for (int i = 0; i < Main.getEnvironment().getWidth(); i++) {
			for (int j = 0; j < Main.getEnvironment().getHeight(); j++) {
				int x = 0;
				boolean actionDone = false;
				Cell cell = CellList.getInstance().getCell(i, j);
				
				switch (cell.getType())
				{
				case PREDATOR:
					// 1. Beute vorhanden -> essen (W=1)
					// 2. Nachwuchs zeugen (W=0-1)
					// 3. Sterben (W=0-1)
					// 4. Weitergehen (W=1; zufälliges freies Feld)
					while (!actionDone) {
						switch (x)
						{
						case 0:
							actionDone = cell.goTo(CellType.PREY);
							break;
						case 1:
							actionDone = cell.reproduce();
							break;
						case 2:
							actionDone = cell.die();
							break;
						default:
							actionDone = true;
							cell.goTo(CellType.DUMMY);
							break;
						}
						
						x++;
					}
					break;
				case PREY:
					// 1. Nachwuchs zeugen -> paaren (W=0-1)
					// 2. Sterben (W=0-1)
					// 3. Weitergehen (zufälliges freies Feld)
					while (!actionDone) {
						switch (x)
						{
						case 0:
							actionDone = cell.reproduce();
							break;
						case 1:
							actionDone = cell.die();
							break;
						default:
							actionDone = true;
							cell.goTo(CellType.DUMMY);
							break;
						}
						
						x++;
					}
					break;
				default:
					break;
				}
			}
		}
	}
}
