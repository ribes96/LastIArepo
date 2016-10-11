/**
 * 
 */
package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;

/**
 *
 */
public class Estado {
	//Atributos
	private double precioAlmacenamiento;
	private static ArrayList<Oferta> ofertas;
	private static ArrayList<Paquete> paquetes;
	private ArrayList<LinkedList<Integer> > ofertasAPaquetes;
	private ArrayList<Integer> paquetesAOfertas;
	
	public static final int randomize = 1;
	
	//Constructoras
	public Estado (double precio, ArrayList<Paquete> pacs, ArrayList<Oferta> ofs){
		precioAlmacenamiento = precio;
		paquetes = pacs;
		ofertas = ofs;
	}
	
	//Consultoras
	double obtenerCosteEconomico(){
		double coste = 0;
		for (Oferta of : ofertas) {
			coste += of.getPrecio() + precioAlmacenamiento*of.getDias();
		}
		return coste;
	}
	
	Integer obtenerFelicidad(){
		return null;
	}
	
	List<Paquete> obtenerListaDePaquetes (Integer t){
		return null;
	}

	Boolean sePuedeIntercambiar (Integer p1, Integer p2){
		return null;
	}
	
	Boolean sePuedeMover (Integer p, Integer t){
		return null;
	}
	
	double obtenerPrecioAlmacenamiento (){
		return precioAlmacenamiento;
	}
	
	String aString (){
		return null;
	}

	//Escritoras
	/**
	 * Generadora de la solucion inicial
	 * @param algoritmo
	 * @param seed
	 * @pre Algoritmo es uno de los aportados
	 * @post El parametro implicito tiene solucion inicial
	 */
	void generarSolucionInicial (int algoritmo, long seed){
		try {
			switch (algoritmo){
			case randomize:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i< paquetes.size(); ++i) paquetesAOfertas.add(i);
				Collections.shuffle(paquetesAOfertas, new Random(seed));
				
				for (int paquete: paquetesAOfertas){
					ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				}
				break;
				default: throw new Exception("No existe este algoritmo");
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	void intercambiar (Integer p1, Integer p2){
		
	}
	
	void mover (Integer p, Integer t){
		
	}
	
	void ponerPrecioAlmacenamiento(Float nuevoPrecio){
		precioAlmacenamiento = nuevoPrecio;
	}


}