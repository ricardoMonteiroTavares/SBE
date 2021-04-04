package Menu.Implements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

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
        	
        	int option = Console.readInt("\nDigite um nÃºmero entre 1 e 5:");
        	
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
        			getAllExecuteTravelsInDay(dao);
        			break;
        		}
        		case 5:
        		{
        			getAllExecuteTravelsInPeriod(dao);
        			break;
        		}
        		case 6:
        		{
        			execute = false;
        			break;
        		}
        		default:
					System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
        	}
        	
        }while(execute);
		
	}

	@Override
	protected void consoleOptions() {
		System.out.println("\nO que vocÃª deseja fazer?");
		System.out.println("\n1. Criar um intinerário");
		System.out.println("2. Alterar um intinerário");
		System.out.println("3. Remover um intinerário");
		System.out.println("4. Listar todos os intinerários de uma viagem em uma data");
		System.out.println("5. Listar todos os intinerários de uma viagem em um período");		
		System.out.println("6. Retornar ao Menu Anterior");	
		
	}

	@Override
	protected void insert(ExecuteTravelDAO dao) {
		ExecuteTravel ext;
		
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		try {						
			Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
			
			String company = Console.readLine("Informe a empresa que fará o intinerário: ");
			
			if(!isNotEmpty(company)) {
				System.out.println("O campo empresa não pode ficar vazia. Cancelando....");
				return;
			}
			
			String vehicleCode = Console.readLine("Informe o código do veículo que fará o intinerário: ");
			
			if(!isNotEmpty(company)) {
				System.out.println("O campo código do veículo não pode ficar vazia. Cancelando....");
				return;
			}
			
			System.out.println("\nEscolha o sentido do intinerário:");
			System.out.println("\n1. " + travel.getOrigin());
			System.out.println("2. " + travel.getDestination());
			
			String direction = null;
			int option;
			do {
				option = Console.readInt("\nDigite um número entre 1 e 2:");
				
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
						System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
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
			System.out.println("Viagem não encontrada. Cancelando....");
			return;
		}
		catch (RuntimeException e)
		{
			System.out.println(e.toString());
			return;
		}
		
		System.out.println("\nIntinerário com o id " + ext.getId() + "foi incluÃ­do com sucesso!");
	}

	@Override
	protected void update(ExecuteTravelDAO dao) {
		
		long id = Console.readInt("\nInsira o ID do intinerário ao qual vocÃª deseja atualizar as informaÃ§Ãµes: ");
		
    	try
		{	
    		ExecuteTravel ext = dao.getExTravel(id);
    		
    		System.out.println(ext.toString());
        	
        	System.out.println("\nO que vocÃª deseja fazer?");
    		System.out.println("\n1. Mudar a Viagem");
    		System.out.println("2. Mudar a Empresa");
    		System.out.println("3. Mudar o Sentido");
    		System.out.println("4. Mudar o Código do Veículo");
    		
    		int option = Console.readInt("\nDigite um nÃºmero de 1 a 4:");
    		boolean updated = false;
    		
    		switch(option)
    		{
    			case 1:    			
    			{
    				id = (long) Console.readInt("\nInforme o ID da viagem: ");
    				Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
    				
    				System.out.println("\nEscolha o novo sentido do intinerário:");
    				System.out.println("\n1. " + travel.getOrigin());
    				System.out.println("2. " + travel.getDestination());
    				
    				String newdirection = null;    			
    				do {
    					option = Console.readInt("\nDigite um número entre 1 e 2:");
    					
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
    							System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
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
    				String newCompany = Console.readLine("Informe a nova empresa que realizou o intinerário: ");
    				if(isNotEmpty(newCompany)) {
    					ext.setCompany(newCompany);
    					updated = true;
    				}			
    				break;
    			}
    			case 3:
    			{
    				Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(ext.getId_travel());
    				
    				System.out.println("\nEscolha o novo sentido do intinerário:");
    				System.out.println("\n1. " + travel.getOrigin());
    				System.out.println("2. " + travel.getDestination());
    				
    				String newdirection = null;    			
    				do {
    					option = Console.readInt("\nDigite um número entre 1 e 2:");
    					
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
    							System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
    						}
    					}
    				}while(option != 1 || option != 2);
    				
    				ext.setDirection(newdirection);
    				updated = true;
    				
    				break;
    			}
    			case 4:
    			{
    				String newVehicleCode = Console.readLine("Informe a novo código do veículo que realizou o intinerário: ");
    				if(isNotEmpty(newVehicleCode)) {
    					ext.setVehicleCode(newVehicleCode);
    					updated = true;
    				}			
    				break;
    			}
    			default:
    				System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
    		}
    		
    		if(updated)
    		{
    			dao.update(ext);
    		}
    		else 
    		{
    			System.out.println("Campos inválidos ou vazios. Cancelando....");
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
		long id = Console.readInt("\nInsira o ID do intinerário que você deseja remover: ");
		try
		{	
    		ExecuteTravel ext = dao.getExTravel(id);
				
							
			System.out.println(ext.toString());
												
			String resp = Console.readLine("\nConfirma a remoção do intinerário?");

			if(resp.toLowerCase().equals("s"))
			{			
				dao.delete(ext.getId());
				System.out.println("\nIntinerário removido com sucesso!");								
			}
			else
			{	
				System.out.println("\nIntinerário nÃ£o removido.");
			}
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
		}
	}
	
	private void getAllExecuteTravelsInDay(ExecuteTravelDAO dao) {
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		
		try {			
			Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate day = LocalDate.parse(Console.readLine("\nInforme a data desejada no padrão DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(day)) {
				System.out.println("O campo data não pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
			
			Date date = new Date(day.toEpochDay());				 
			
			List<ExecuteTravel> exts;			
			
			String resp = Console.readLine("\nDeseja informar o sentido do initinerário?");
			if(resp.toLowerCase().equals("s"))
			{			
				System.out.println("\nEscolha o sentido do intinerário:");
				System.out.println("\n1. " + travel.getOrigin());
				System.out.println("2. " + travel.getDestination());
				
				int option;
				String direction = null;    			
				do {
					option = Console.readInt("\nDigite um número entre 1 e 2:");
					
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
							System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
						}
					}
				}while(option != 1 || option != 2);
				exts = dao.getAllExTravelsByDateAndDirection(travel.getId(), direction, date);							
			}
			else
			{	
				exts = dao.getAllExTravelsByDate(travel.getId(), date);
			}
			
	    	for(ExecuteTravel ext : exts) {
	    		System.out.println(ext.toString());
	    	}
		}
		catch(ObjectNotFoundException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}
		catch (DateTimeParseException e) {
			System.out.println("\nData Inválida.");
			return;
		}	
		
	}
	
	private void getAllExecuteTravelsInPeriod(ExecuteTravelDAO dao) {
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		
		try {			
			Travel travel = DAOFactory.getDAO(TravelDAO.class).getTravel(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate initial_day = LocalDate.parse(Console.readLine("\nInforme a data inicial desejada no padrão DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(initial_day)) {
				System.out.println("O campo data inicial não pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
					
			Date date_I = new Date(initial_day.toEpochDay());	
			
			LocalDate final_day = LocalDate.parse(Console.readLine("\nInforme a data final desejada no padrão DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(final_day) && final_day.isAfter(initial_day) ) {
				System.out.println("O campo data final não pode ser superior a data de hoje e a data inicial informada, ou vazia. Cancelando....");
				return;
			}
					
			Date date_F = new Date(final_day.toEpochDay());	
			List<ExecuteTravel> exts;			
			
			String resp = Console.readLine("\nDeseja informar o sentido do initinerário?");
			if(resp.toLowerCase().equals("s"))
			{			
			
				System.out.println("\nEscolha o sentido do intinerário:");
				System.out.println("\n1. " + travel.getOrigin());
				System.out.println("2. " + travel.getDestination());
				
				int option;
				String direction = null;    			
				do {
					option = Console.readInt("\nDigite um número entre 1 e 2:");
					
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
							System.out.println("\nOpÃ§Ã£o invÃ¡lida!");
						}
					}
				}while(option != 1 || option != 2);
				exts = dao.getAllExTravelsByPeriodAndDirection(travel.getId(), direction, date_I, date_F);
				
			}
			else
			{	
				exts = dao.getAllExTravelsByPeriod(travel.getId(), date_I, date_F);
			}
	    	
	    	for(ExecuteTravel ext : exts) {
	    		System.out.println(ext.toString());
	    	}
		}
		catch(ObjectNotFoundException e) {
			System.out.println('\n' + e.getMessage());
			return;
		}
		catch (DateTimeParseException e) {
			System.out.println("\nData Inválida.");
			return;
		}		
		
	}

	private boolean isNotEmpty(String s) {
		return s.isEmpty() && !s.equals("");
	}
}
