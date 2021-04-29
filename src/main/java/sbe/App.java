package sbe;

import java.util.ArrayList;
import java.util.List;

import Menu.Implements.BoardingMenu;
import Menu.Implements.CardMenu;
import Menu.Implements.ExecuteTravelMenu;
import Menu.Implements.TravelMenu;
import Singletons.ProfileSingleton;
import corejava.Console;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	List<String> profiles = new ArrayList<String>();
    	
    	System.out.println("Bem-vindo ao sistema de login");
    	
    	boolean login = true;
    	
    	while(login)
    	{
    		String profile = Console.readLine("\nInforme um perfil para entrar no sistema: ");
    		if(isNotEmpty(profile))
    		{
    			profiles.add(profile);
    		}
    		else
    		{
    			System.out.println("Perfil inválido");
    		}
    		
    		String resp = Console.readLine("Deseja adicionar mais um perfil? (S ou s para Sim, Qualquer tecla para Não)");
    		
    		if(!resp.toLowerCase().equals("s")) {
    			login = false;
    		}
    	}
    	
    	if(profiles.isEmpty()) 
    	{
    		System.out.println("Não exitem perfis logados no sistema. Encerrando....");
    		return;
    	}
    	
    	String[] arr = new String[profiles.size()];
    	arr = profiles.toArray(arr); 
    	
    	ProfileSingleton singleton = ProfileSingleton.getSingleton();
    	singleton.setProfiles(arr);
    	
    	CardMenu cardMenu = new CardMenu();
    	BoardingMenu boardingMenu = new BoardingMenu();
    	ExecuteTravelMenu exMenu = new ExecuteTravelMenu();
    	TravelMenu travelMenu = new TravelMenu();
    	
    	boolean execute = true;
    	
    	do {
    		consoleOptions();
    		
    		int option = Console.readInt("\nEscolha uma opção:");
    		
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
    
	private static boolean isNotEmpty(String s) {
		return !s.isEmpty() && !s.equals("");
	}
}
