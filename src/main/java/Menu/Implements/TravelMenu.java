package Menu.Implements;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Entities.Travel;
import Exceptions.ObjectNotFoundException;
import Menu.Interfaces.Menu;
import Services.Interfaces.TravelService;
import corejava.Console;

public class TravelMenu extends Menu<TravelService> {

	@Override
	public void menu() {
		@SuppressWarnings("resource")
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");
		TravelService service = (TravelService) factory.getBean("travelService");
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um nÃºmero entre 1 e 5:");
        	
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
        			viewAllTravels(service);
        			break;
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
		System.out.println("\n1. Criar uma viagem");
		System.out.println("2. Alterar uma viagem");
		System.out.println("3. Remover um viagem");
		System.out.println("4. Listar todas as viagens");
		System.out.println("5. Retornar ao Menu Anterior");
		
	}

	@Override
	protected void insert(TravelService service) {
		Travel travel;
		String line = Console.readLine("\nInforme o código da viagem: ");
		String origin = Console.readLine("\nInforme a origem da viagem: ");	
		String destination = Console.readLine("\nInforme o destino da viagem: ");
		
		
		if(isNotEmpty(line) && isNotEmpty(origin) && isNotEmpty(destination)) {
			double ticketValue = Console.readDouble("\nInforme o valor da passagem da viagem: ");
			if(ticketValue > 0) {
				travel = new Travel(line, origin, destination, ticketValue);
				service.insert(travel);
			}
			else {
				System.out.println("Campos inválidos ou Vazios. Cancelando....");
				return;
			}
		}
		else {
			System.out.println("Campos inválidos ou Vazios. Cancelando....");
			return;
		}
		
		System.out.println("\nViagem com o id " + travel.getId() + " e código " + travel.getLine() + " foi incluí­do com sucesso!");
		
	}

	@Override
	protected void update(TravelService service) {
		Travel travel;
    	long id = Console.readInt("\nInsira o ID da viagem ao qual você deseja atualizar as informações: ");
    	
    	try
		{	
    		travel = service.get(id);
    		
    		System.out.println(travel.toString());
        	
        	System.out.println("\nO que você deseja fazer?");
    		System.out.println("\n1. Mudar Código");
    		System.out.println("2. Mudar a Origem");
    		System.out.println("3. Mudar o Destino");
    		System.out.println("4. Mudar o valor da Tarifa");
    		
    		int option = Console.readInt("\nDigite um número de 1 a 4:");
    		boolean updated = false;
    		
    		switch(option)
    		{
    			case 1:
    			{
    				String newLine = Console.readLine("Digite o novo código da viagem: ");
    				if(isNotEmpty(newLine)) {
    					travel.setLine(newLine);
    					updated = true;
    				}			
    				break;
    			}
    			case 2:	
    			{
    				String newOrigin = Console.readLine("Informe a nova origem da viagem: ");
    				if(isNotEmpty(newOrigin) && !newOrigin.equals(travel.getDestination())) {
    					travel.setOrigin(newOrigin);
    					updated = true;
    				}			
    				break;
    			}
    			case 3:
    			{
    				String newDestination = Console.readLine("Informe o novo destino da viagem: ");
    				if(isNotEmpty(newDestination) && !newDestination.equals(travel.getOrigin())) {
    					travel.setDestination(newDestination);
    					updated = true;
    				}			
    				break;
    			}
    			case 4:
    			{
    				double newTicketValue = Console.readDouble("Digite a nova tarifa da viagem: ");					
    				if(newTicketValue > 0) {
    					travel.setTicketValue(newTicketValue);
    					updated = true;
    					break;
    				}											
    				break;
    			}
    			default:
    				System.out.println(invalidOption);
    		}
    		
    		if(updated) {
			
				service.update(travel);
		
				System.out.println("\nAlteração efetuada com sucesso!");
			
    		}else {
    			System.out.println("Campos inválidos, Vazios ou Origem é igual ao Destino. Cancelando....");
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
	protected void delete(TravelService service) {
		Travel travel;
    	long id = Console.readInt("\nInsira o ID da viagem que vocÃª deseja remover: ");
    	
    	try
		{	
    		travel = service.get(id);
    		System.out.println(travel.toString());
			
    		String resp = Console.readLine("\nConfirma a remoção da viagem? (S ou s para Sim, Qualquer tecla para Não)");

    		if(resp.toLowerCase().equals("s"))
    		{	
    			try
    			{	
    			service.delete(travel);
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
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}					
		
	}
	
	 private void viewAllTravels(TravelService service) 
	    {
	    	List<Travel> travels = service.getAllTravels();
	    	for(Travel travel : travels) {
	    		System.out.println(travel.toString());
	    	}
	    }
	
	private boolean isNotEmpty(String s) {
		return s.isEmpty() && !s.equals("");
	}

}
