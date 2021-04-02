package Menu.Implements;

import DAOs.Interfaces.TravelDAO;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.DAOFactory;
import Menu.Interfaces.Menu;
import corejava.Console;

public class TravelMenu extends Menu<TravelDAO> {

	@Override
	public void menu() {
		TravelDAO travelDao =  DAOFactory.getDAO(TravelDAO.class);
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um número entre 1 e 5:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(travelDao);
        			break;
        		}
        		case 2:
        		{
        			update(travelDao);
        			break;
        		}
        		case 3:
        		{
        			delete(travelDao);
        			break;
        		}
        		case 4:
        		{
        			//viewAllCards(travelDao);
        			break;
        		}
        		case 5:
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
		System.out.println("\n1. Criar uma viagem");
		System.out.println("2. Alterar uma viagem");
		System.out.println("3. Remover um viagem");
		System.out.println("4. Listar todas as viagens");
		System.out.println("5. Retornar ao Menu Anterior");
		
	}

	@Override
	protected void insert(TravelDAO dao) {
		Travel travel;
		String line = Console.readLine("\nInforme o c�digo da viagem: ");
		String origin = Console.readLine("\nInforme a origem da viagem: ");	
		String destination = Console.readLine("\nInforme o destino da viagem: ");
		
		
		if(isNotEmpty(line) && isNotEmpty(origin) && isNotEmpty(destination)) {
			double ticketValue = Console.readDouble("\nInforme o valor da passagem da viagem: ");
			if(ticketValue > 0) {
				travel = new Travel(line, origin, destination, ticketValue);
				dao.insert(travel);
			}
			else {
				System.out.println("Campos inv�lidos ou Vazios. Cancelando....");
				return;
			}
		}
		else {
			System.out.println("Campos inv�lidos ou Vazios. Cancelando....");
			return;
		}
		
		System.out.println("\nViagem com o id " + travel.getId() + " e c�digo " + travel.getLine() + " foi incluído com sucesso!");
		
	}

	@Override
	protected void update(TravelDAO dao) {
		Travel travel;
    	long id = Console.readInt("\nInsira o ID da viagem ao qual você deseja atualizar as informações: ");
    	
    	try
		{	
    		travel = dao.getTravel(id);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
    	
    	System.out.println(travel.toString());
    	
    	System.out.println("\nO que você deseja fazer?");
		System.out.println("\n1. Mudar C�digo");
		System.out.println("2. Mudar a Origem");
		System.out.println("3. Mudar o Destino");
		System.out.println("4. Mudar o valor da Tarifa");
		
		int option = Console.readInt("\nDigite um número de 1 a 4:");
		boolean updated = false;
		
		switch(option)
		{
			case 1:
				String newLine = Console.readLine("Digite o novo c�digo da viagem: ");
				if(isNotEmpty(newLine)) {
					travel.setLine(newLine);
					updated = true;
				}			
				break;
				
			case 2:				
				String newOrigin = Console.readLine("Informe a nova origem da viagem: ");
				if(isNotEmpty(newOrigin) && !newOrigin.equals(travel.getDestination())) {
					travel.setOrigin(newOrigin);
					updated = true;
				}			
				break;
			case 3:
				String newDestination = Console.readLine("Informe o novo destino da viagem: ");
				if(isNotEmpty(newDestination) && !newDestination.equals(travel.getOrigin())) {
					travel.setDestination(newDestination);
					updated = true;
				}			
				break;
			case 4:
				double newTicketValue = Console.readDouble("Digite a nova tarifa da viagem: ");					
				if(newTicketValue > 0) {
					travel.setTicketValue(newTicketValue);
					updated = true;
					break;
				}											
				break;
			default:
				System.out.println("\nOpção inválida!");
		}
		
		if(updated) {
			try
			{	
				dao.update(travel);
		
				System.out.println("\nAlteração efetuada com sucesso!");
			}
			catch(ObjectNotFoundException e)
			{	
				System.out.println('\n' + e.getMessage());
			} 
			catch (ObjectVersionException e) 
			{
				System.out.println("\nA operação não foi efetuada: Os dados que você tentou salvar foram modificados por outro usuário");
			}
		}else {
			System.out.println("Campos inv�lidos, Vazios ou Origem � igual ao Destino. Cancelando....");
			return;
		}			
			
	}

	@Override
	protected void delete(TravelDAO dao) {
		Travel travel;
    	long id = Console.readInt("\nInsira o ID da viagem que você deseja remover: ");
    	
    	try
		{	
    		travel = dao.getTravel(id);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
							
		System.out.println(travel.toString());
											
		String resp = Console.readLine("\nConfirma a remoção da viagem?");

		if(resp.toLowerCase().equals("s"))
		{	
			try
			{	
			dao.delete(travel.getId());
				System.out.println("\nViagem removido com sucesso!");
			}
			catch(ObjectNotFoundException e)
			{	
				System.out.println('\n' + e.getMessage());
			}
		}
		else
		{	
			System.out.println("\nViagem não removida.");
		}

		
	}
	
	private boolean isNotEmpty(String s) {
		return s.isEmpty() && !s.equals("");
	}

}
