/**
 * 
 */
package dominio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

/**
 *
 */
public class Estado {
	//Atributos
	private static double precioAlmacenamiento;
	//private static ArrayList<Oferta> ofertas;
	//private static ArrayList<Paquete> paquetes;
	private static Paquetes paquetes;
	private static Transporte ofertas;
	
	private ArrayList<LinkedList<Integer> > ofertasAPaquetes;
	private ArrayList<Integer> paquetesAOfertas;
	
	/**
	 * Retorna la cantidad de días antes del máximo permitido se enviará el paquete en una asignación concreta
	 * @param p
	 * @return
	 */
	private Integer diasAntes(Integer p) {
		return null;
	}
	
	/**
	 * Estructura trivial para mentener los pesos actuales de cada oferta
	 */
	private double pesosActuales[];
	
	public static final String aleatorio = "aleatorio";
	public static final String unoAUno = "uno a uno";
	public static final String rellenar = "rellenar";
	
	//Constructores
	
	/**
	 * 
	 * @param precio Precio de almacenamiento
	 * @param pacs Paquetes a enviar
	 * @param ofs Ofertas de transporte
	 * @param algoritmo Para generar la solución inicial
	 * @long seed Para generar la solución inicial
	 */
	public Estado (double precio, Paquetes pacs, Transporte ofs, String algoritmo, long seed){
		precioAlmacenamiento = precio;
		paquetes = pacs;
		ofertas = ofs;
		pesosActuales = new double[ofs.size()];
		
		
		
		
		generarSolucionInicial(algoritmo, seed);
	}
	
	/**
	 * Crea un estado y una solución aleatorios según los parámetros
	 * @param precio Precio de almacenamiento
	 * @param prop La proporción entre el peso a enviar y el máximo enviable
	 * @param seedtrans Seed para generar el transporte (las ofertas)
	 * @param npaq Cantidad de paquetes aleatorios a generar
	 * @param seedpac Seed para generar los paquetes
	 * @param algoritmo Indica el algoritmo a utilizar para generar la solución inicial
	 * @param seedAlg Seed para generar la solución inicial
	 */
	public Estado(double precio, double prop, int seedTrans, int npaq, int seedPac, String algoritmo, long seedAlg) {
		paquetes = new Paquetes(npaq, seedPac);
		ofertas = new Transporte(paquetes, prop, seedTrans);
		precioAlmacenamiento = precio;
		
		pesosActuales = new double[ofertas.size()];
		
		generarSolucionInicial(algoritmo, seedAlg);
	}
	
	
	/**
	 * Crea un estado y una solución aleatorios según los parámetros
	 * @param precio Precio de almacenamiento
	 * @param prop La proporción entre el peso a enviar y el máximo enviable
	 * @param npaq Cantidad de paquetes aleatorios a generar
	 * @param algoritmo Indica el algoritmo a utilizar para generar la solución inicial
	 */
	public Estado(double precio, double prop, int npaq, String algoritmo) {
		Random rnd = new Random();
		paquetes = new Paquetes(npaq, rnd.nextInt());
		ofertas = new Transporte(paquetes, prop, rnd.nextInt());
		precioAlmacenamiento = precio;
		
		pesosActuales = new double[ofertas.size()];
		
		generarSolucionInicial(algoritmo, rnd.nextLong());
	}
	
	//Modificadoras
	
	public void hacerAsignacion(ArrayList<LinkedList<Integer> > ofsApacs, ArrayList<Integer> pacsAofs) {
		ofertasAPaquetes = ofsApacs;
		paquetesAOfertas = pacsAofs;
		
		int i = 0;
		for (LinkedList<Integer> of : ofsApacs) {
			pesosActuales[i] = 0.0;
			for (Integer indice : of) {
				pesosActuales[i] += paquetes.get(indice).getPeso();
			}		
			++i;
		}
	}
	
	//Consultoras
	public double obtenerCosteEconomico(){
		double coste = 0;
		for (Oferta of : ofertas) {
			coste += of.getPrecio() + precioAlmacenamiento*of.getDias();
		}
		return coste;
	}
	
	public Integer obtenerFelicidad(){
		Integer fel = 0;
		for (Integer i = 0; i < paquetesAOfertas.size(); ++i) {
			fel += diasAntes(i);
		}
		return fel;
	}
	
	/**
	 * Retorna todos los paquetes que estan asignados a la oferta t
	 * @param t
	 * @return
	 */
	Paquetes obtenerListaDePaquetes (Integer t){
		LinkedList<Integer> lista = ofertasAPaquetes.get(t);
		
		ArrayList<Paquete> retorno = new ArrayList<Paquete>();
		for (Integer i : lista) {
			retorno.add(paquetes.get(i));
		}
		return (Paquetes)retorno;
	}

	Boolean sePuedeIntercambiar (Integer p1, Integer p2){
		Paquete pac1 = paquetes.get(p1);
		Paquete pac2 = paquetes.get(p2);		
		Oferta of1 = ofertas.get(paquetesAOfertas.get(p1));
		Oferta of2 = ofertas.get(paquetesAOfertas.get(p2));
		
		//Los índices de las ofertas
		Integer o1 = paquetesAOfertas.get(p1);
		Integer o2 = paquetesAOfertas.get(p2);
		
		if (of1 == of2) return false; //Están en la misma oferta
		if (pesosActuales[o1] - pac1.getPeso() + pac2.getPeso() > of1.getPesomax()) return false;
		if (pesosActuales[o2] - pac2.getPeso() + pac1.getPeso() > of2.getPesomax()) return false;
		
		return true;
	}
	
	Boolean sePuedeMover (Integer p, Integer t){
		Paquete pac = paquetes.get(p);
		Oferta of = ofertas.get(paquetesAOfertas.get(p));
		
		if (paquetesAOfertas.get(p) == t) return false; //ya está en esa oferta
		
		return pesosActuales[p] + pac.getPeso() <= of.getPesomax();
		
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
			for (int i = 0; i<pesosActuales.length; ++i) pesosActuales[i] = 0;
			switch (algoritmo){
			case aleatorio:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i< paquetes.size(); ++i){
					paquetesAOfertas.add(new Random(seed).nextInt());
					pesosActuales[paquetesAOfertas.get(i)] += paquetes.get(i).getPeso();
				}
				for (int paquete: paquetesAOfertas) ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case unoAUno:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i<paquetes.size(); ++i){
					paquetesAOfertas.add(i);
					pesosActuales[paquetesAOfertas.get(i)] += paquetes.get(i).getPeso();
				}
				for (int paquete: paquetesAOfertas) ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case rellenar:
				ofertasAPaquetes = new ArrayList<LinkedList<Integer> >(ofertas.size());
				ArrayList<Double> espacioEnOferta = new ArrayList<Double>();
				for (int i = 0; i < ofertas.size(); ++i) espacioEnOferta.add(ofertas.get(i).getPesomax());
				int idPaquete = 0;				
				for (int idOferta = 0; idOferta<ofertasAPaquetes.size(); ++idOferta){
					while (idPaquete<paquetes.size()){
						if (paquetes.get(idPaquete).getPeso() < espacioEnOferta.get(idOferta)){
							paquetesAOfertas.set(idPaquete, new Integer(idOferta));
							ofertasAPaquetes.get(idOferta).add(idPaquete);
							espacioEnOferta.set(idOferta, espacioEnOferta.get(idOferta)-paquetes.get(idPaquete).getPeso());
						}
						++idPaquete;
					}
				}
				for (int i = 0; i < pesosActuales.length; ++i) pesosActuales[i] = ofertas.get(i).getPesomax() - espacioEnOferta.get(i);
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
		Paquete pac1 = paquetes.get(p1);
		Paquete pac2 = paquetes.get(p2);		
		//Oferta of1 = ofertas.get(paquetesAOfertas.get(p1));
		//Oferta of2 = ofertas.get(paquetesAOfertas.get(p2));
		
		//Los índices de las ofertas
		Integer o1 = paquetesAOfertas.get(p1);
		Integer o2 = paquetesAOfertas.get(p2);
		
		if (o1.equals(o2)) return;
		
		LinkedList<Integer> l = ofertasAPaquetes.get(o1);
		l.remove(p1);
		l.add(p2);
		
		l = ofertasAPaquetes.get(o2);
		l.remove(p2);
		l.add(p1);
		
		paquetesAOfertas.set(p1, o2);
		paquetesAOfertas.set(p2, o1);
		
		pesosActuales[o1] += pac2.getPeso() - pac1.getPeso();
		pesosActuales[o1] += pac2.getPeso() - pac1.getPeso();
	}
	
	void mover (Integer p, Integer t){
		Paquete pac = paquetes.get(p);
		
		//La oferta en la que está actualmente
		//Oferta of1 = ofertas.get(paquetesAOfertas.get(p));
		
		//La oferta a la que quiere ir
		//Oferta of2 = ofertas.get(t);
		
		//Los índices de la oferta en la que está actualmente
		Integer o = paquetesAOfertas.get(p);
		
		ofertasAPaquetes.get(o).remove(p);	//Quitarlo de donde estaba antes
		ofertasAPaquetes.get(o).add(p); 	//Ponerlo en la nueva oferta
		
		paquetesAOfertas.set(p, t);	//Decir que está en la nueva oferta
		
		pesosActuales[o] -= pac.getPeso();
		pesosActuales[t] += pac.getPeso();
	}
	
	void ponerPrecioAlmacenamiento(double nuevoPrecio){
		precioAlmacenamiento = nuevoPrecio;
	}


}