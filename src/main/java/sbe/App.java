package sbe;

import Menu.Implements.CardMenu;
import corejava.Console;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	CardMenu cardMenu = new CardMenu();
    	
    	boolean execute = true;
    	
    	do {
    		consoleOptions();
    		
    		int option = Console.readInt("\nDigite um número entre 1 e 3:");
    		
    		switch (option) {
    		case 1:
    		{
    			cardMenu.menu();
    			break;
    		}
    		case 2:
    		{
    			
    			break;
    		}
    		case 3:
    		{
    			
    			break;
    		}
    		case 4:
    		{
    			
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
    
    private static void consoleOptions() 
    {
    	System.out.println("\nQual menu voc� deseja acessar?");
		System.out.println("\n1. Menu Cart�o");
		System.out.println("2. Menu Viagem");
		System.out.println("3. Menu Executar Viagem");
		System.out.println("4. Menu Embarques");
		System.out.println("5. Sair");
    }
}
