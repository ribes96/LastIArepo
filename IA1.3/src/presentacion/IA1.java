package presentacion;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import dominio.Estado;
import dominio.FuncionHeuristicaSimple;
import dominio.FuncionSucesoraHillClimbing;
import dominio.FuncionSucesoraSimulatedAnnealing;
import dominio.PruebaDeFin;

public class IA1 {
    
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);


    	double precioAlmacenamiento = 0, prop = 1;
		int npac = 0;
		
		while (precioAlmacenamiento <= 0) {
			System.out.println("Qué precio de almacenamiento? (>0)");
			precioAlmacenamiento = in.nextDouble();
		}
		
		while (prop <= 1) {
			System.out.println("Qué proporción? (>1)");
			prop = in.nextDouble();
		}
		
		while (npac <= 0) {
			System.out.println("Cuántos paquetes? (>0)");
			npac = in.nextInt();
		}
		
		System.out.println("Qué seed)");
		int seed = in.nextInt();
		
		int algoritmoInicial;
		do {
			System.out.println("Qué algoritmo de generación del estado inicial quieres usar (1. Aleatorio; 2. Rellenando ofertas por orden 3. Alternando paquetes y ofertas)?");
			algoritmoInicial = in.nextInt();
		} while (algoritmoInicial != 1 && algoritmoInicial != 2 && algoritmoInicial != 3);
		
		
		Estado estado = new Estado(precioAlmacenamiento, prop, seed, npac, seed, Estado.intToString(algoritmoInicial), seed);
		//System.out.println("Hola");
    	
    	int algoritmo;
		do {
			System.out.println("Qué algoritmo de resolución quieres usar (1. Hill Climbing; 2. Simulated Annealing)?");
			algoritmo = in.nextInt();
		} while (algoritmo != 1 && algoritmo != 2);

        long startTime = System.currentTimeMillis();

		switch (algoritmo){
		case 1:
			IA1HillClimbing(estado);
			break;
		case 2:
			IA1SimulatedAnnealing(estado);
			break;
		default:
			System.out.println("Error intern");
		}
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
		in.close();
    }
    
    private static void IA1HillClimbing(Estado estado) {
    	System.out.println("\nIA1 HillClimbing  -->");
        try {
        	System.out.print(estado.aString());
        	System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            Problem problem =  new Problem(estado,new FuncionSucesoraHillClimbing(), new PruebaDeFin(), new FuncionHeuristicaSimple());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            System.out.println("Hemos terminado");
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}

	private static void IA1SimulatedAnnealing(Estado estado) {
		Scanner in = new Scanner(System.in);
		int k;
		double lambda;
		int iteraciones;
		
		do {
			System.out.println("Indica la k, la lambda, la cantidad de iteraciones");
			k = in.nextInt();
			lambda = in.nextDouble();
			iteraciones = in.nextInt();
		} while (k < 1 || lambda <= 0.0 || iteraciones < 10);
		System.out.println("\nIA1 Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(estado,new FuncionSucesoraSimulatedAnnealing(), new PruebaDeFin(), new FuncionHeuristicaSimple());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(iteraciones,1000,k,lambda);
            SearchAgent agent = new SearchAgent(problem,search);
            System.out.println("TAMANY AGENT" + ' ' + agent.getActions().size() );

            System.out.println();

            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
	}
    
    @SuppressWarnings("rawtypes")
    private static void printInstrumentation(Properties properties) {
		Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
    
    @SuppressWarnings("rawtypes")
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
    
    
}
