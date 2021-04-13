package Menu.Implements;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Menu.Interfaces.Menu;
import Services.Interfaces.CardService;
import corejava.Console;

public class CardMenu extends Menu<CardService> {
	
	@Override
	public void menu()
    {
    	
		@SuppressWarnings("resource")
		ApplicationContext factory = new ClassPathXmlApplicationContext("beans-jpa.xml");

		CardService service = (CardService) factory.getBean("cardService");
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um número entre 1 e 5:");
        	
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
        			viewAllCards(service);
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
    protected void consoleOptions() 
    {
    	System.out.println("\nO que você deseja fazer?");
		System.out.println("\n1. Cadastrar um cartão");
		System.out.println("2. Alterar um cartão");
		System.out.println("3. Remover um cartão");
		System.out.println("4. Listar todos os cartões");
		System.out.println("5. Retornar ao Menu Anterior");
    }
    
	@Override
    protected void insert(CardService service)
    {
    	Card card;
    	String name = Console.readLine("\nInforme o seu nome: ");
    	
    	String insertBalance = Console.readLine("\nDeseja inserir créditos? (S ou s para Sim, Qualquer tecla para Não)");
    	if(insertBalance.toLowerCase().equals("s")) {
    		double balance = Console.readDouble("Digite o valor a ser inserido no novo cartão: ");
    		if(balance <= 0) {
    			System.out.println("\nO valor a ser inserido deve ser maior do que 0!\n");
    			return;
    		}
    		card = new Card(name, balance);
    	}else {
    		card = new Card(name);
    	}
		service.insert(card);
	
			
		System.out.println("\nCartão com código " + card.getCode() + " foi incluí­do com sucesso!");	
    }
    
	@Override
    protected void update(CardService service) 
    {
    	Card card;
    	long cardCode = Console.readInt("\nInsira o código do cartão ao qual você deseja atualizar as informações: ");
    	
    	try
		{	
    		card = service.get(cardCode);
    		
    		System.out.println(card.toString());
        	
        	System.out.println("\nO que você deseja fazer?");
    		System.out.println("\n1. Mudar Nome");
    		System.out.println("2. Inserir Créditos");
    		
    		int option = Console.readInt("\nDigite um número de 1 a 2:");
    		
    		switch(option)
    		{
    			case 1:
    			{
    				String newName = Console.readLine("Digite o novo nome: ");
    	
    				card.setName(newName);
    				
					service.update(card);
			
					System.out.println("\nAlteração de nome efetuada com sucesso!");
    					
    				break;
    			}
    			case 2:
    			{
    				double insertBalance = Console.readDouble("Digite o valor a ser inserido no cartão: ");
    		
    				
    				if(insertBalance <= 0) {
    					System.out.println("\nO valor a ser inserido deve ser maior do que 0!\n");
    					break;
    				}
    				
    				double newBalance = card.getBalance() + insertBalance;
    				card.setBalance(newBalance);
    				service.update(card);
    					
    				System.out.println("\nCréditos inseridos com sucesso!");						    		
    				
    				break;
    			}
    			default:
    				System.out.println(invalidOption);
    		}
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
    	
    	
    }
    
	@Override
    protected void delete(CardService service) 
    {
    	Card card;
    	long cardCode = Console.readInt("\nInsira o código do cartão que você deseja remover: ");
    	
    	try
		{	
    		card = service.get(cardCode);
    		
    		System.out.println(card.toString());
			
    		String resp = Console.readLine("\nConfirma a remoção do cartão? (S ou s para Sim, Qualquer tecla para Não)");

    		if(resp.toLowerCase().equals("s"))
    		{	
    				
    			service.delete(card);
    			System.out.println("\nCartão removido com sucesso!");
    			
    		}
    		else
    		{	
    			System.out.println("\nCartão não removido.");
    		}
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}						
		
    }
    
    private void viewAllCards(CardService service) 
    {
    	List<Card> cards = service.getAllCards();
    	for(Card card : cards) {
    		System.out.println(card.toString());
    	}
    }
}
