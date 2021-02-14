package Factories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
	
	private static EMFactory factory = null;
	private EntityManagerFactory emf = null;

	
	private EMFactory() {
		try 
		{
			this.emf = Persistence.createEntityManagerFactory("Entities");
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			System.out.println(">>>>>>>>>> Mensagem de erro: " + e.getMessage());
		}
	}
	
	public static EntityManager newSession()
	{
		if(factory == null)
		{
			factory = new EMFactory();
		}
		
		return factory.emf.createEntityManager();
	}
	
	public void closeEntityManagerFactory()
	{
		if((factory != null) && (factory.emf != null))
		{
			factory.emf.close();
		}
	}
}
