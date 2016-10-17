package vista;

import java.util.Scanner;

public class Main {
	public static void main(String [] args){
		Scanner in = new Scanner(System.in);
		int algoritmo = -1;
		while (algoritmo != 1 && algoritmo != 2) {
			System.out.println("Qué algoritmo de resolución quieres usar (1. Hill Climbing; 2. Simulated Annealing)?");
			algoritmo = in.nextInt();
		}
		
		double precioAlmacenamiento = 0, prop = 1;
		int npac = 0, seed = null;
		
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
		seed = in.nextInt();
		
		Estado est = new Estado();
	}
}
	
