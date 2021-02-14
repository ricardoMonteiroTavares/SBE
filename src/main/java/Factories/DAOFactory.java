package Factories;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DAOFactory {
	private static ResourceBundle prop;

	static
	{	try
		{	prop = ResourceBundle.getBundle("dao");
		}
		catch(MissingResourceException e)
		{	System.out.println("Aquivo dao.properties não encontrado.");
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> type)
	{			
		T dao = null;
		String className = null; 
	
		try
		{	className = prop.getString(type.getSimpleName());
			dao = (T) Class.forName(className).newInstance();
		} 
		catch (InstantiationException e)
		{	System.out.println("Não foi possível criar um objeto do tipo " + className);
			throw new RuntimeException(e);
		} 
		catch (IllegalAccessException e)
		{	System.out.println("Não foi possível criar um objeto do tipo " + className);
			throw new RuntimeException(e);
		} 
		catch (ClassNotFoundException e)
		{	System.out.println("Classe " + className + " não encontrada");
			throw new RuntimeException(e);
		}
		catch(MissingResourceException e)
		{	System.out.println("Chave " + type + " não encontrada em dao.properties");
			throw new RuntimeException(e);
		}
		
		return dao;
	}
}
