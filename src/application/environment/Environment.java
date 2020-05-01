package application.environment;

public abstract class Environment implements EnvironmentInterface {
	private static final int Width = 10;
	private static final int Height = 10;
	
	private static Environment instance;
	
	public static Environment getInstance() {
		return instance;
	};
	
	protected Environment() {}
	
	public int getWidth() {
		return Width;
	}
	
	public int getHeight() {
		return Height;
	}
}
