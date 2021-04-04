package Menu.Implements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import DAOs.Interfaces.BoardingDAO;
import DAOs.Interfaces.CardDAO;
import DAOs.Interfaces.ExecuteTravelDAO;
import Entities.Boarding;
import Entities.Card;
import Entities.ExecuteTravel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.DAOFactory;
import Menu.Interfaces.Menu;
import corejava.Console;

public class BoardingMenu extends Menu<BoardingDAO> {

	@Override
	public void menu() {
		BoardingDAO dao =  DAOFactory.getDAO(BoardingDAO.class);
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um nÃºmero entre 1 e 6:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(dao);
        			break;
        		}        		
        		case 2:
        		{
        			delete(dao);
        			break;
        		}
        		case 3:
        		{
        			getAllBoardingsInDay(dao);
        			break;
        		}
        		case 4:
        		{
        			getAllBoardingsInPeriod(dao);
        		}
        		case 5:
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
		System.out.println("\n1. Criar um embarque");
		System.out.println("2. Remover um embarque");
		System.out.println("3. Listar todos os embarques em um dia");
		System.out.println("4. Listar todos os embarques em um período");
		System.out.println("5. Retornar ao Menu Anterior");
		
	}

	@Override
	protected void insert(BoardingDAO dao) {
		Boarding boarding;
		
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
					
		try 
		{
			CardDAO cardDao = DAOFactory.getDAO(CardDAO.class);
			Card card = cardDao.getCard(id);
			
			id = (long) Console.readInt("\nInforme o código do intinerário: ");
			
			ExecuteTravel ext = DAOFactory.getDAO(ExecuteTravelDAO.class).getExTravel(id);
			
			if((card.getBalance() - ext.getTicketValue()) < 0) {
				System.out.println("Saldo insuficiente no Cartão. Cancelando....");
				return;
			}
			
			card.setBalance(card.getBalance() - ext.getTicketValue());
						
			LocalDateTime dt = LocalDateTime.now(); 			
			
			boarding = new Boarding(ext.getId(), card.getCode(), dt.toLocalDate().toString(), dt.toLocalTime().toString());
			
			dao.insert(boarding);
			cardDao.update(card);
			
		}
		catch(ObjectNotFoundException e) {
			System.out.println("Cartão ou Intinerário não encontrado. Cancelando....");
			return;
		}
		catch (ObjectVersionException e) {
			System.out.println("\nA operaÃ§Ã£o nÃ£o foi efetuada: Os dados que vocÃª tentou salvar foram modificados por outro usuÃ¡rio");
			return;
		}
		catch (RuntimeException e)
		{
			System.out.println(e.toString());
			return;
		}
				
		System.out.println("\nO Embarque com o id " + boarding.getId() + " foi incluÃ­do com sucesso!");
		
	}

	@Override
	protected void delete(BoardingDAO dao) {
		Boarding boarding;
		Long id = (long) Console.readInt("\nInforme o código do embarque que deseja remover: ");
    	
    	try
		{	
    		boarding = dao.getBoarding(id);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
							
		System.out.println(boarding.toString());
											
		String resp = Console.readLine("\nConfirma a remoÃ§Ã£o do embarque?");

		if(resp.toLowerCase().equals("s"))
		{	
			try
			{	
			dao.delete(boarding.getId());
				System.out.println("\nEmbarque removido com sucesso!");
			}
			catch(ObjectNotFoundException e)
			{	
				System.out.println('\n' + e.getMessage());
			}
		}
		else
		{	
			System.out.println("\nEmbarque nÃ£o removido.");
		}
	}
	
	private void getAllBoardingsInDay(BoardingDAO dao) {
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
		
		try {			
			Card card = DAOFactory.getDAO(CardDAO.class).getCard(id);
			
			LocalDate today = LocalDate.now();			
			
			LocalDate day = LocalDate.parse(Console.readLine("\nInforme a data desejada no padrão DD-MM-YYYY: "), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			if(today.isBefore(day)) {
				System.out.println("O campo data não pode ser superior a data de hoje ou vazia. Cancelando....");
				return;
			}
			
			Date date = new Date(day.toEpochDay());				 
			
			List<Boarding> boardings = dao.getAllBoardingsByDate(card.getCode(), date);
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
	
	private void getAllBoardingsInPeriod(BoardingDAO dao) {
		Long id = (long) Console.readInt("\nInforme o código do cartão: ");
		
		try {			
			Card card = DAOFactory.getDAO(CardDAO.class).getCard(id);
			
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
			
			List<Boarding> boardings = dao.getAllBoardingsByPeriod(card.getCode(), date_I, date_F);
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
	protected void update(BoardingDAO dao) {}

}
