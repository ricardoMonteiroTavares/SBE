package sbe;

import Menu.Implements.*;
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
    	BoardingMenu boardingMenu = new BoardingMenu();
    	ExecuteTravelMenu exMenu = new ExecuteTravelMenu();
    	TravelMenu travelMenu = new TravelMenu();
    	
    	boolean execute = true;
    	
    	do {
    		consoleOptions();
    		
    		int option = Console.readInt("\nDigite um número entre 1 e 5:");
    		
    		switch (option) {
    		case 1:
    		{
    			cardMenu.menu();
    			break;
    		}
    		case 2:
    		{
    			travelMenu.menu();
    			break;
    		}
    		case 3:
    		{
    			exMenu.menu();
    			break;
    		}
    		case 4:
    		{
    			boardingMenu.menu();
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
    	System.out.println("\nQual menu você deseja acessar?");
		System.out.println("\n1. Menu Cartão");
		System.out.println("2. Menu Viagem");
		System.out.println("3. Menu Executar Viagem");
		System.out.println("4. Menu Embarques");
		System.out.println("5. Sair");
    }
}
