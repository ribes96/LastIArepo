/**
 * 
 */
package dominio;

import java.util.*;
import IA.Azamon.*;

/**
 *
 */
public class Estado {
	//Atributos
	private double precioAlmacenamiento;
	private ArrayList<SubOferta> ofertas;
	private ArrayList<SubPaquete> paquetes;
	
	//Constructoras
	public Estado (double precio, ArrayList<SubPaquete> pacs, ArrayList<SubOferta> ofs){
		precioAlmacenamiento = precio;
		paquetes = pacs;
		ofertas = ofs;
	}
	
	//Consultoras
	double obtenerCosteEconomico(){
		double coste = 0;
		for (SubOferta of : ofertas) {
			coste += of.getCoste() + precioAlmacenamiento*of.getDias();
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
	void intercambiar (Integer p1, Integer p2){
		
	}
	
	void mover (Integer p, Integer t){
		
	}
	
	void ponerPrecioAlmacenamiento(Float nuevoPrecio){
		precioAlmacenamiento = nuevoPrecio;
	}


}