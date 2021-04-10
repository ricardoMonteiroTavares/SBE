package Menu.Implements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import DAOs.Interfaces.CardDAO;
import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.Boarding;
import Entities.Card;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Factories.ServiceFactory;
import Menu.Interfaces.Menu;
import Services.Interfaces.BoardingService;
import Services.Interfaces.CardService;
import corejava.Console;

public class BoardingMenu extends Menu<BoardingService> {

	@Override
	public void menu() {
		BoardingService service =  ServiceFactory.getService(BoardingService.class);
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um nÃºmero entre 1 e 6:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(service);
        			break;
        		}        		
        		case 2:
        		{
        			delete(service);
        			break;
        		}
        		case 3:
        		{
        			getAllBoardingsInDay(service);
        			break;
        		}
        		case 4:
        		{
        			getAllBoardingsInPeriod(service);
        		}
        		case 5:
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
		System.out.println("\nO que você deseja fazer?");
		System.out.println("\n1. Criar um embarque");
		System.out.println("2. Remover um embarque");
		System.out.println("3. Listar todos os embarques em um dia");
		System.out.println("4. Listar todos os embarques em um período");
		System.out.println("5. Retornar ao Menu Anterior");
		
	}

	@Override
	protected void insert(BoardingService service) {
		Boarding boarding;
		
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
					
		try 
		{
			CardService cardService = ServiceFactory.getService(CardService.class);
			Card card = cardService.get(id);
			
			id = (long) Console.readInt("\nInforme o código do intinerário: ");
			
			ExecuteTravel ext = ServiceFactory.getService(ExecuteTravelDAO.class).get(id);
			
			if((card.getBalance() - ext.getTicketValue()) < 0) {
				System.out.println("Saldo insuficiente no Cartão. Cancelando....");
				return;
			}
			
			card.setBalance(card.getBalance() - ext.getTicketValue());
						
			LocalDateTime dt = LocalDateTime.now(); 			
			
			boarding = new Boarding(ext.getId(), card.getCode(), dt.toLocalDate().toString(), dt.toLocalTime().toString());
			
			service.insert(boarding);
			cardService.update(card);
			
		}
		catch(ObjectNotFoundException e) {
			System.out.println(e.toString());
			return;
		}
		catch (RuntimeException e)
		{
			System.out.println(e.toString());
			return;
		}
				
		System.out.println("\nO Embarque com o id " + boarding.getId() + " foi incluído com sucesso!");
		
	}

	@Override
	protected void delete(BoardingService service) {
		Boarding boarding;
		Long id = (long) Console.readInt("\nInforme o código do embarque que deseja remover: ");
    	
    	try
		{	
    		boarding = service.get(id);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
							
		System.out.println(boarding.toString());
											
		String resp = Console.readLine("\nConfirma a remoção do embarque? (S ou s para Sim, Qualquer tecla para Não)");

		if(resp.toLowerCase().equals("s"))
		{	
			try
			{	
			service.delete(boarding);
				System.out.println("\nEmbarque removido com sucesso!");
			}
			catch(ObjectNotFoundException e)
			{	
				System.out.println('\n' + e.getMessage());
			}
		}
		else
		{	
			System.out.println("\nEmbarque não removido.");
		}
	}
	
	private void getAllBoardingsInDay(BoardingService service) {
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
		
		try {			
			Card card = ServiceFactory.getService(CardDAO.class).get(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate day = LocalDate.parse(Console.readLine("\nInforme a data desejada no padrão DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(day)) {
				System.out.println("O campo data não pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
			
			Date date = new Date(day.toEpochDay());				 
			
			List<Boarding> boardings = service.getAllBoardingsByDate(card.getCode(), date);
	    	for(Boarding boarding : boardings) {
	    		System.out.println(boarding.toString());
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
	
	private void getAllBoardingsInPeriod(BoardingService service) {
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
		
		try {			
			Card card = ServiceFactory.getService(CardDAO.class).get(id);
			
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
			
			List<Boarding> boardings = service.getAllBoardingsByPeriod(card.getCode(), date_I, date_F);
	    	for(Boarding boarding : boardings) {
	    		System.out.println(boarding.toString());
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

	@Override
	protected void update(BoardingService service) {}

}
