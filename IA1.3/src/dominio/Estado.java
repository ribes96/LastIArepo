/**
 * 
 */
package dominio;

import java.util.ArrayList;
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
	
	public static final String aleatorio = "aleatorio";
	public static final String unoAUno = "uno a uno";
	public static final String rellenar = "rellenar";
	
	//Constructora
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
	 * @pre Algoritmo es uno de las constantes de clase
	 * @post El parametro implicito tiene solucion inicial
	 */
	void generarSolucionInicial (String algoritmo, long seed){
		try {
			switch (algoritmo){
			case aleatorio:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i< paquetes.size(); ++i) paquetesAOfertas.add(new Random(seed).nextInt());
				for (int paquete: paquetesAOfertas) ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case unoAUno:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i<paquetes.size(); ++i) paquetesAOfertas.add(i);
				for (int paquete: paquetesAOfertas) ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case rellenar:
				ofertasAPaquetes = new ArrayList<LinkedList<Integer> >(ofertas.size());
				ArrayList<Double> espacioEnOferta = new ArrayList<Double>();
				for (int i = 0; i < ofertas.size(); ++i) espacioEnOferta.add(ofertas.get(i).getPesomax());
				int idOferta = 0, idPaquete = 0;				
				while (idOferta<ofertasAPaquetes.size()){
					while (idPaquete<paquetes.size()){
						if (paquetes.get(idPaquete).getPeso() < espacioEnOferta.get(idOferta)){
							paquetesAOfertas.set(idPaquete, new Integer(idOferta));
							ofertasAPaquetes.get(idOferta).add(idPaquete);
							espacioEnOferta.set(idOferta, espacioEnOferta.get(idOferta)-paquetes.get(idPaquete).getPeso());
						}
						++idPaquete;
					}
					++idOferta;
				}
			default: throw new IllegalArgumentException("No existe este algoritmo");
			}
		}
		catch (IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		catch (NullPointerException e){
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