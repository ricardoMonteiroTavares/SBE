package Menu.Interfaces;

public abstract class Menu<T> {
	
	abstract public void menu();
	
	abstract protected void consoleOptions();
	
	abstract protected void insert(T obj);
	
	abstract protected void update(T obj);
	
	abstract protected void delete(T obj);
}