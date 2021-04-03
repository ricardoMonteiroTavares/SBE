package Menu.Implements;

import java.time.LocalDateTime;

import DAOs.Interfaces.ExecuteTravelDAO;
import DAOs.Interfaces.TravelDAO;
import Entities.ExecuteTravel;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.DAOFactory;
import Menu.Interfaces.Menu;
import corejava.Console;

public class ExecuteTravelMenu extends Menu<ExecuteTravelDAO> {

	@Override
	public void menu() {
		ExecuteTravelDAO dao =  DAOFactory.getDAO(ExecuteTravelDAO.class);
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um número entre 1 e 5:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(dao);
        			break;
        		}
        		case 2:
        		{
        			update(dao);
        			break;
        		}
        		case 3:
        		{
        			delete(dao);
        			break;
        		}
        		case 4:
        		{
        			//TODO: Listar todos os intiner�rios de uma viagem em uma data
        			break;
        		}
        		case 5:
        		{
        			//TODO: Listar todos os intiner�rios de uma viagem em um per�odo
        			break;
        		}
        		case 6:
        		{
        			execute = false;
        			break;
        		}
        		default:
					System.out.println("\nOpção inválida!");
        	}
        	
        }while(execute);
		
	}

	@Override
	protected void consoleOptions() {
		System.out.println("\nO que você deseja fazer?");
		System.out.println("\n1. Criar um intiner�rio");
		System.out.println("2. Alterar um intiner�rio");
		System.out.println("3. Remover um intiner�rio");
		System.out.println("4. Listar todos os intiner�rios de uma viagem em uma data");
		System.out.println("5. Listar todos os intiner�rios de uma viagem em um per�odo");		
		System.out.println("6. Retornar ao Menu Anterior");	
		
	}

	@Override
	protected void insert(ExecuteTravelDAO dao) {
		ExecuteTravel ext;
		
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		try {						
			Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
			
			String company = Console.readLine("Informe a empresa que far� o intiner�rio: ");
			
			if(!isNotEmpty(company)) {
				System.out.println("O campo empresa n�o pode ficar vazia. Cancelando....");
				return;
			}
			
			String vehicleCode = Console.readLine("Informe o c�digo do ve�culo que far� o intiner�rio: ");
			
			if(!isNotEmpty(company)) {
				System.out.println("O campo c�digo do ve�culo n�o pode ficar vazia. Cancelando....");
				return;
			}
			
			System.out.println("\nEscolha o sentido do intiner�rio:");
			System.out.println("\n1. " + travel.getOrigin());
			System.out.println("2. " + travel.getDestination());
			
			String direction = null;
			int option;
			do {
				option = Console.readInt("\nDigite um n�mero entre 1 e 2:");
				
				switch(option) {
					case 1: 
					{
						direction = travel.getOrigin();
					}
					case 2:
					{
						direction = travel.getDestination();
					}
					default:
					{
						System.out.println("\nOpção inválida!");
					}
				}
			}while(option != 1 || option != 2);
			
			
			LocalDateTime dt = LocalDateTime.now();
			ext = new ExecuteTravel(
					travel.getId(),
					company,
					vehicleCode,
					dt.toLocalDate().toString(),
					dt.toLocalTime().toString(),
					direction,
					travel.getTicketValue()
			);
			dao.insert(ext);
		}
		catch(ObjectNotFoundException e) {
			System.out.println("Viagem n�o encontrada. Cancelando....");
			return;
		}
		catch (RuntimeException e)
		{
			System.out.println(e.toString());
			return;
		}
		
		System.out.println("\nIntiner�rio com o id " + ext.getId() + "foi incluído com sucesso!");
	}

	@Override
	protected void update(ExecuteTravelDAO dao) {
		
		long id = Console.readInt("\nInsira o ID do intiner�rio ao qual você deseja atualizar as informações: ");
		
    	try
		{	
    		ExecuteTravel ext = dao.getExTravel(id);
    		
    		System.out.println(ext.toString());
        	
        	System.out.println("\nO que você deseja fazer?");
    		System.out.println("\n1. Mudar a Viagem");
    		System.out.println("2. Mudar a Empresa");
    		System.out.println("3. Mudar o Sentido");
    		System.out.println("4. Mudar o C�digo do Ve�culo");
    		
    		int option = Console.readInt("\nDigite um número de 1 a 4:");
    		boolean updated = false;
    		
    		switch(option)
    		{
    			case 1:    			
    			{
    				id = (long) Console.readInt("\nInforme o ID da viagem: ");
    				Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
    				
    				System.out.println("\nEscolha o novo sentido do intiner�rio:");
    				System.out.println("\n1. " + travel.getOrigin());
    				System.out.println("2. " + travel.getDestination());
    				
    				String newdirection = null;    			
    				do {
    					option = Console.readInt("\nDigite um n�mero entre 1 e 2:");
    					
    					switch(option) {
    						case 1: 
    						{
    							newdirection = travel.getOrigin();
    						}
    						case 2:
    						{
    							newdirection = travel.getDestination();
    						}
    						default:
    						{
    							System.out.println("\nOpção inválida!");
    						}
    					}
    				}while(option != 1 || option != 2);
    				
    				ext.setId_travel(travel.getId());
    				ext.setTicketValue(travel.getTicketValue());
    				ext.setDirection(newdirection);
    				updated = true;		
    				break;
    			}
    			case 2:	
    			{
    				String newCompany = Console.readLine("Informe a nova empresa que realizou o intiner�rio: ");
    				if(isNotEmpty(newCompany)) {
    					ext.setCompany(newCompany);
    					updated = true;
    				}			
    				break;
    			}
    			case 3:
    			{
    				Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(ext.getId_travel());
    				
    				System.out.println("\nEscolha o novo sentido do intiner�rio:");
    				System.out.println("\n1. " + travel.getOrigin());
    				System.out.println("2. " + travel.getDestination());
    				
    				String newdirection = null;    			
    				do {
    					option = Console.readInt("\nDigite um n�mero entre 1 e 2:");
    					
    					switch(option) {
    						case 1: 
    						{
    							newdirection = travel.getOrigin();
    						}
    						case 2:
    						{
    							newdirection = travel.getDestination();
    						}
    						default:
    						{
    							System.out.println("\nOpção inválida!");
    						}
    					}
    				}while(option != 1 || option != 2);
    				
    				ext.setDirection(newdirection);
    				updated = true;
    				
    				break;
    			}
    			case 4:
    			{
    				String newVehicleCode = Console.readLine("Informe a novo c�digo do ve�culo que realizou o intiner�rio: ");
    				if(isNotEmpty(newVehicleCode)) {
    					ext.setVehicleCode(newVehicleCode);
    					updated = true;
    				}			
    				break;
    			}
    			default:
    				System.out.println("\nOpção inválida!");
    		}
    		
    		if(updated)
    		{
    			dao.update(ext);
    		}
    		else 
    		{
    			System.out.println("Campos inv�lidos ou vazios. Cancelando....");
    			return;
    		}
    		
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		} catch (ObjectVersionException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}    	    
		
	}

	@Override
	protected void delete(ExecuteTravelDAO dao) {
		long id = Console.readInt("\nInsira o ID do intiner�rio que voc� deseja remover: ");
		try
		{	
    		ExecuteTravel ext = dao.getExTravel(id);
				
							
			System.out.println(ext.toString());
												
			String resp = Console.readLine("\nConfirma a remo��o do intiner�rio?");

			if(resp.toLowerCase().equals("s"))
			{			
				dao.delete(ext.getId());
				System.out.println("\nIntiner�rio removido com sucesso!");								
			}
			else
			{	
				System.out.println("\nIntiner�rio não removido.");
			}
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
		}
	}

	private boolean isNotEmpty(String s) {
		return s.isEmpty() && !s.equals("");
	}
}
