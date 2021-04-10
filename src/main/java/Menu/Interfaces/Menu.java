package Menu.Interfaces;

public abstract class Menu<T> {
	
	final protected String invalidOption = "\nOpção Inválida!"; 
	
	abstract public void menu();
	
	abstract protected void consoleOptions();
	
	abstract protected void insert(T dao);
	
	abstract protected void update(T dao);
	
	abstract protected void delete(T dao);
}