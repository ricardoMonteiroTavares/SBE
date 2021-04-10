package Menu.Interfaces;

public abstract class Menu<T> {
	
	final protected String invalidOption = "\nOp��o Inv�lida!"; 
	
	abstract public void menu();
	
	abstract protected void consoleOptions();
	
	abstract protected void insert(T service);
	
	abstract protected void update(T service);
	
	abstract protected void delete(T service);
}