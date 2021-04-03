package Menu.Implements;

import java.util.List;

import DAOs.Interfaces.CardDAO;
import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;
import Factories.DAOFactory;
import Menu.Interfaces.Menu;
import corejava.Console;

public class CardMenu extends Menu<CardDAO> {
	
	@Override
	public void menu()
    {
    	
    	CardDAO cardDao =  DAOFactory.getDAO(CardDAO.class);
    	
        boolean execute = true;
        do {
        	consoleOptions();
        	
        	int option = Console.readInt("\nDigite um número entre 1 e 5:");
        	
        	switch (option) {
        		case 1:
        		{
        			insert(cardDao);
        			break;
        		}
        		case 2:
        		{
        			update(cardDao);
        			break;
        		}
        		case 3:
        		{
        			delete(cardDao);
        			break;
        		}
        		case 4:
        		{
        			viewAllCards(cardDao);
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
    protected void insert(CardDAO dao)
    {
    	Card card;
    	String name = Console.readLine("\nInforme o seu nome: ");
    	
    	String insertBalance = Console.readLine("\nDeseja inserir créditos? ");
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
		dao.insert(card);
	
			
		System.out.println("\nCartão com código " + card.getCode() + " foi incluído com sucesso!");	
    }
    
	@Override
    protected void update(CardDAO dao) 
    {
    	Card card;
    	long cardCode = Console.readInt("\nInsira o código do cartão ao qual você deseja atualizar as informações: ");
    	
    	try
		{	
    		card = dao.getCard(cardCode);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
    	
    	System.out.println(card.toString());
    	
    	System.out.println("\nO que você deseja fazer?");
		System.out.println("\n1. Mudar Nome");
		System.out.println("2. Inserir Créditos");
		
		int option = Console.readInt("\nDigite um número de 1 a 2:");
		
		switch(option)
		{
			case 1:
				String newName = Console.readLine("Digite o novo nome: ");
	
				card.setName(newName);
			
				try
				{	
					dao.update(card);
			
					System.out.println("\nAlteração de nome efetuada com sucesso!");
				}
				catch(ObjectNotFoundException e)
				{	
					System.out.println('\n' + e.getMessage());
				} 
				catch (ObjectVersionException e) 
				{
					System.out.println("\nA operação não foi efetuada: Os dados que você tentou salvar foram modificados por outro usuário");
				}
					
				break;
				
			case 2:
				double insertBalance = Console.readDouble("Digite o valor a ser inserido no cartão: ");
		
				
				if(insertBalance <= 0) {
					System.out.println("\nO valor a ser inserido deve ser maior do que 0!\n");
					break;
				}
				
				double newBalance = card.getBalance() + insertBalance;
				card.setBalance(newBalance);
		
				try
				{	
					dao.update(card);
					
					System.out.println("\nCréditos inseridos com sucesso!");						
				}
				catch(ObjectNotFoundException e)
				{	
					System.out.println('\n' + e.getMessage());
				}
				catch (ObjectVersionException e) 
				{
					System.out.println("\nA operação não foi efetuada: Os dados que você tentou salvar foram modificados por outro usuário");
				}
				
				break;
			default:
				System.out.println("\nOpção inválida!");
		}
    }
    
	@Override
    protected void delete(CardDAO dao) 
    {
    	Card card;
    	long cardCode = Console.readInt("\nInsira o código do cartão que você deseja remover: ");
    	
    	try
		{	
    		card = dao.getCard(cardCode);
		}
		catch(ObjectNotFoundException e)
		{	
			System.out.println('\n' + e.getMessage());
			return;
		}
							
		System.out.println(card.toString());
											
		String resp = Console.readLine("\nConfirma a remoção do cartão?");

		if(resp.toLowerCase().equals("s"))
		{	
			try
			{	
			dao.delete(card.getCode());
				System.out.println("\nCartão removido com sucesso!");
			}
			catch(ObjectNotFoundException e)
			{	
				System.out.println('\n' + e.getMessage());
			}
		}
		else
		{	
			System.out.println("\nCartão não removido.");
		}
		
    }
    
    private void viewAllCards(CardDAO dao) 
    {
    	List<Card> cards = dao.getAllCards();
    	for(Card card : cards) {
    		System.out.println(card.toString());
    	}
    }
}
