package Menu.Implements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Entities.ExecuteTravel;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Menu.Interfaces.Menu;
import Services.Interfaces.ExecuteTravelService;
import Services.Interfaces.TravelService;
import corejava.Console;

public class ExecuteTravelMenu extends Menu<ExecuteTravelService> {

	@Override
	public void menu() {
		@SuppressWarnings("resource")
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
		ExecuteTravelService service = (ExecuteTravelService) factory.getBean("executeTravelService");

    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um n�mero entre 1 e 5:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(service);
        			break;
        		}
        		case 2:
        		{
        			update(service);
        			break;
        		}
        		case 3:
        		{
        			delete(service);
        			break;
        		}
        		case 4:
        		{
        			getAllExecuteTravelsInDay(service);
        			break;
        		}
        		case 5:
        		{
        			getAllExecuteTravelsInPeriod(service);
        			break;
        		}
        		case 6:
        		{
        			execute = false;
        			break;
        		}
        		default:
					System.out.println(invalidOption);
        	}
        	
        }while(execute);
		
	}

	@Override
	protected void consoleOptions() {
		System.out.println("\nO que voc� deseja fazer?");
		System.out.println("\n1. Criar um intiner�rio");
		System.out.println("2. Alterar um intiner�rio");
		System.out.println("3. Remover um intiner�rio");
		System.out.println("4. Listar todos os intiner�rios de uma viagem em uma data");
		System.out.println("5. Listar todos os intiner�rios de uma viagem em um per�odo");		
		System.out.println("6. Retornar ao Menu Anterior");	
		
	}

	@Override
	protected void insert(ExecuteTravelService service) {
		ExecuteTravel ext;
		
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		try {					
			@SuppressWarnings("resource")
			ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
			TravelService travelService = (TravelService) factory.getBean("travelService");
			Travel travel = travelService.get(id);
			
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
			
			String direction = selectDirection(travel);
			
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
			service.insert(ext);
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
		
		System.out.println("\nIntiner�rio com o id " + ext.getId() + "foi inclu�do com sucesso!");
	}

	@Override
	protected void update(ExecuteTravelService service) {
		
		long id = Console.readInt("\nInsira o ID do intiner�rio ao qual voc� deseja atualizar as informa��es: ");
		@SuppressWarnings("resource")
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
		TravelService travelService = (TravelService) factory.getBean("travelService");
    	try
		{	
    		ExecuteTravel ext = service.get(id);
    		
    		System.out.println(ext.toString());
        	
        	System.out.println("\nO que voc� deseja fazer?");
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
    				Travel travel = travelService.get(id);
    				
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
    							System.out.println(invalidOption);
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
    				
    				Travel travel = travelService.get(id);
    				
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
    							System.out.println(invalidOption);
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
    				System.out.println(invalidOption);
    		}
    		
    		if(updated)
    		{
    			service.update(ext);
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
		}    	    
		
	}

	@Override
	protected void delete(ExecuteTravelService service) {
		long id = Console.readInt("\nInsira o ID do intiner�rio que voc� deseja remover: ");
		try
		{	
    		ExecuteTravel ext = service.get(id);
				
							
			System.out.println(ext.toString());
												
			String resp = Console.readLine("\nConfirma a remo��o do intiner�rio? (S ou s para Sim, Qualquer tecla para N�o)");

			if(resp.toLowerCase().equals("s"))
			{			
				service.delete(ext);
				System.out.println("\nIntiner�rio removido com sucesso!");								
			}
			else
			{	
				System.out.println("\nIntiner�rio n�o removido.");
			}
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
		}
	}
	
	private void getAllExecuteTravelsInDay(ExecuteTravelService service) {
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		
		try {
			@SuppressWarnings("resource")
			ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
			TravelService travelService = (TravelService) factory.getBean("travelService");
			Travel travel = travelService.get(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate day = LocalDate.parse(Console.readLine("\nInforme a data desejada no padr�o DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(day)) {
				System.out.println("O campo data n�o pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
			
			Date date = new Date(day.toEpochDay());				 
			
			List<ExecuteTravel> exts;			
			
			String resp = Console.readLine("\nDeseja informar o sentido do initiner�rio? (S ou s para Sim, Qualquer tecla para N�o)");
			if(resp.toLowerCase().equals("s"))
			{			
				String direction = selectDirection(travel);
				exts = service.getAllExTravelsByDateAndDirection(travel.getId(), direction, date);							
			}
			else
			{	
				exts = service.getAllExTravelsByDate(travel.getId(), date);
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
			System.out.println("\nData Inv�lida.");
			return;
		}	
		
	}
	
	private void getAllExecuteTravelsInPeriod(ExecuteTravelService service) {
		Long id = (long) Console.readInt("\nInforme o ID da viagem: ");
		
		try {			
			@SuppressWarnings("resource")
			ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
			TravelService travelService = (TravelService) factory.getBean("travelService");
			Travel travel = travelService.get(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate initial_day = LocalDate.parse(Console.readLine("\nInforme a data inicial desejada no padr�o DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(initial_day)) {
				System.out.println("O campo data inicial n�o pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
					
			Date date_I = new Date(initial_day.toEpochDay());	
			
			LocalDate final_day = LocalDate.parse(Console.readLine("\nInforme a data final desejada no padr�o DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(final_day) && final_day.isAfter(initial_day) ) {
				System.out.println("O campo data final n�o pode ser superior a data de hoje e a data inicial informada, ou vazia. Cancelando....");
				return;
			}
					
			Date date_F = new Date(final_day.toEpochDay());	
			List<ExecuteTravel> exts;			
			
			String resp = Console.readLine("\nDeseja informar o sentido do initiner�rio? (S ou s para Sim, Qualquer tecla para N�o)");
			if(resp.toLowerCase().equals("s"))
			{			
				String direction = selectDirection(travel);
				exts = service.getAllExTravelsByPeriodAndDirection(travel.getId(), direction, date_I, date_F);
				
			}
			else
			{	
				exts = service.getAllExTravelsByPeriod(travel.getId(), date_I, date_F);
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
			System.out.println("\nData Inv�lida.");
			return;
		}		
		
	}

	private boolean isNotEmpty(String s) {
		return !s.isEmpty() && !s.equals("");
	}
	
	private String selectDirection(Travel travel) {
		System.out.println("\nEscolha o sentido do intiner�rio:");
		System.out.println("\n1. " + travel.getOrigin());
		System.out.println("2. " + travel.getDestination());
		
		int option;
		String direction = null;    			
		do {
			option = Console.readInt("\nDigite um n�mero entre 1 e 2:");
			
			switch(option) {
				case 1: 
				{
					direction = travel.getOrigin();
					break;
				}
				case 2:
				{
					direction = travel.getDestination();
					break;
				}
				default:
				{
					System.out.println(invalidOption);
				}
			}
		}while(direction == null);
		return direction;
	}
}
