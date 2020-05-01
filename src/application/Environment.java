package application;

public abstract class Environment implements EnvironmentInterface {
	
	private static Environment instance;
	
	public static Environment getInstance() {		
		return instance;
	}
	
	protected Environment() {}
}
