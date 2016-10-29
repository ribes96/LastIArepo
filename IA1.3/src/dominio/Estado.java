/**
 * 
 */
package dominio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
//import java.lang.Math;

import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

/**
 *
 */
public class Estado {
	// Atributos
	private static double precioAlmacenamiento;
	// private static ArrayList<Oferta> ofertas;
	// private static ArrayList<Paquete> paquetes;
	private static Paquetes paquetes;
	private static Transporte ofertas;

	private ArrayList<LinkedList<Integer>> ofertasAPaquetes;
	private ArrayList<Integer> paquetesAOfertas;

	/**
	 * Funcion que calcula la cantidad de días antes del máximo permitido se enviará el
	 * paquete en una asignación concreta
	 * 
	 * @param p Identificador del paquete a consultar.
	 * @return La funcion devuelve los dias antes del maximo poermitido, de otro modo devuelve 1
	 */
	private Integer diasAntes(Integer p) {
		int diasHastaEntrega = ofertas.get(paquetesAOfertas.get(p)).getDias();
		int prioridad = paquetes.get(p).getPrioridad();
		int diasMin = -1;
		if (prioridad == Paquete.PR1)
			diasMin = 1;
		else if (prioridad == Paquete.PR2)
			diasMin = 2;
		else if (prioridad == Paquete.PR3)
			diasMin = 4;
		return Integer.max(0, diasMin - diasHastaEntrega);
	}

	/**
	 * Estructura trivial para mentener los pesos actuales de cada oferta
	 */
	private double pesosActuales[];

	public static final String aleatorio = "aleatorio";
	public static final String unoAUno = "uno a uno";
	public static final String rellenar = "rellenar";
	public static String SWAP = "SWAP";
	public static String MOVE = "MOVE";

	public static final String intToString(int algoritme) {
		switch (algoritme) {
		case 1:
			return aleatorio;
		case 2:
			return unoAUno;
		case 3:
			return rellenar;
		default:
			System.out.println("Conversion de int a String invalida");
			return null;
		}
	}

	// Constructores

	/**
	 * 
	 * @param precio Precio de almacenamiento
	 * @param pacs Paquetes a enviar
	 * @param ofs Ofertas de transporte
	 * @param algoritmo Para generar la solución inicial
	 * @long seed Para generar la solución inicial
	 */
	public Estado(double precio, Paquetes pacs, Transporte ofs, String algoritmo, long seed) {
		precioAlmacenamiento = precio;
		paquetes = pacs;
		ofertas = ofs;
		pesosActuales = new double[ofs.size()];

		generarSolucionInicial(algoritmo, seed);
	}

	/**
	 * Crea un estado y una solución aleatorios según los parámetros
	 * 
	 * @param precio Precio de almacenamiento
	 * @param prop La proporción entre el peso a enviar y el máximo enviable
	 * @param seedTrans Seed para generar el transporte (las ofertas)
	 * @param npaq Cantidad de paquetes aleatorios a generar
	 * @param seedPac Seed para generar los paquetes
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
	 * 
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

	// Modificadoras

	/**
	 *
	 * @param ofsApacs
	 * @param pacsAofs
	 */
	public void hacerAsignacion(ArrayList<LinkedList<Integer>> ofsApacs, ArrayList<Integer> pacsAofs) {
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

	// Consultoras
	/**
	 * Funcion que devuelve el numero total de paquetes
	 * @return
	 */
	int getNumPaquetes() {
		return paquetes.size();
	}

	/**
	 * Funcion que devuelve el numero total de ofertas
	 * @return
	 */
	int getNumOfertas() {
		return ofertas.size();
	}

	/**
	 * Funcion que devuelve el coste economico total
	 * @return
	 */
	public double obtenerCosteEconomico() {
		double coste = 0;
		for (Oferta of : ofertas) {
			coste += of.getPrecio() + precioAlmacenamiento * of.getDias();
		}
		return coste;
	}

	/**
	 * Funcion que devuelve el indice de felicidad total
	 * Funcion que devuelve el numero total de@return
	 */
	public Integer obtenerFelicidad() {
		Integer fel = 0;
		for (Integer i = 0; i < paquetesAOfertas.size(); ++i) {
			fel += diasAntes(i);
		}
		return fel;
	}

	/**
	 * Funcion que devuelve todos los paquetes que estan asignados a la oferta t
	 * 
	 * @param  t identificador de la oferta t.
	 * @return Conjunto de paquetes assignados a la oferta t.
	 */
	Paquetes obtenerListaDePaquetes(Integer t) {
		LinkedList<Integer> lista = ofertasAPaquetes.get(t);

		ArrayList<Paquete> retorno = new ArrayList<Paquete>();
		for (Integer i : lista) {
			retorno.add(paquetes.get(i));
		}
		return (Paquetes) retorno;
	}

	/**
	 * Funcion que indica si se podra realizar el cambio de dos paquetes entre
	 * ofertas distintas
	 * 
	 * @param p1 Identificador del primer paquete.
	 * @param p2 Identificador del segundo paquete.
	 * @return El resultado sera falso si los paquetes estan en la misma oferta
	 *         o el cambio supera el peso maximo. De otro modo devolvera cierto.
	 */
	Boolean sePuedeIntercambiar(Integer p1, Integer p2) {
		Paquete pac1 = paquetes.get(p1);
		Paquete pac2 = paquetes.get(p2);
		Oferta of1 = ofertas.get(paquetesAOfertas.get(p1));
		Oferta of2 = ofertas.get(paquetesAOfertas.get(p2));

		// Los índices de las ofertas
		Integer o1 = paquetesAOfertas.get(p1);
		Integer o2 = paquetesAOfertas.get(p2);

		if (of1 == of2)
			return false; // Están en la misma oferta
		if (pesosActuales[o1] - pac1.getPeso() + pac2.getPeso() > of1.getPesomax())
			return false;
		if (pesosActuales[o2] - pac2.getPeso() + pac1.getPeso() > of2.getPesomax())
			return false;

		return true;
	}

	/**
	 * Funcion que indica si se puede mover el paquete p a la oferta t.
	 * 
	 * @param p Identificador del paquete a mover
	 * @param t Identificador de la oferta a la que queremos asignar el paquete p
	 * @return El resultado sera falso si el paquete p esta en la oferta t o si
	 *         el hecho de asignar el paquete p a la oferta t supera el peso
	 *         maximo. De otro modo devolvera cierto
	 */
	Boolean sePuedeMover(Integer p, Integer t) {
		Paquete pac = paquetes.get(p);
		Oferta of = ofertas.get(paquetesAOfertas.get(p));

		if (paquetesAOfertas.get(p) == t)
			return false; // ya está en esa oferta
		System.out.println("El tamaño de pesos es " + pesosActuales.length + " y estamos accediendo a "
				+ t);
		return pesosActuales[t] + pac.getPeso() <= of.getPesomax();

	}

	double obtenerPrecioAlmacenamiento() {
		return precioAlmacenamiento;
	}

	String aString() {
		return "Este es el resultado";
		//return null;
	}

	// Escritoras
	/**
	 * Funcion Generadora de la solucion inicial
	 * 
	 * @param algoritmo utilitzat per generar la solucio inicial
	 * @param seed
	 * @pre Algoritmo es uno de las constantes de clase
	 * @post El parametro implicito tiene solucion inicial
	 */
	void generarSolucionInicial(String algoritmo, long seed) {
		try {
			for (int i = 0; i < ofertas.size(); ++i)
				pesosActuales[i] = 0;
			switch (algoritmo) {
			case aleatorio:
				paquetesAOfertas = new ArrayList<Integer>();
				ofertasAPaquetes = new ArrayList<LinkedList<Integer>>();
				for (int r = 0; r < ofertas.size(); ++r) {
					ofertasAPaquetes.add(new LinkedList<Integer>());
				}
				Random myRandom = new Random(seed);
				System.out.println("El tamaño de paquetesAOfertas es " + paquetesAOfertas.size());
				System.out.println("Mientras que el tamano de paquetes es " + paquetes.size());
				for (int i = 0; i < paquetes.size(); ++i) {
					boolean metido = false;
					for (int j = 0; j < 1000 && !metido; ++j){ 
						int ofertaARellenar = myRandom.nextInt(ofertas.size());
						if (ofertas.get(ofertaARellenar).getPesomax() >= paquetes.get(i).getPeso() + pesosActuales[ofertaARellenar]){
							paquetesAOfertas.add(ofertaARellenar);
							ofertasAPaquetes.get(ofertaARellenar).add(i);
							metido = true;
						}
					}
					if (!metido) System.out.println("----------------------------------Alarma--------------");
					System.out.println("El tamaño de paquetesAOfertas es " + paquetesAOfertas.size() + " v2");
					pesosActuales[paquetesAOfertas.get(i)] += paquetes.get(i).getPeso();
				}
				for (int paquete : paquetesAOfertas)
					ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case unoAUno:
				paquetesAOfertas = new ArrayList<Integer>();
				for (int i = 0; i < paquetes.size(); ++i) {
					paquetesAOfertas.add(i);
					pesosActuales[paquetesAOfertas.get(i)] += paquetes.get(i).getPeso();
				}
				for (int paquete : paquetesAOfertas)
					ofertasAPaquetes.get(paquetesAOfertas.get(paquete)).add(paquete);
				break;
			case rellenar:
				ofertasAPaquetes = new ArrayList<LinkedList<Integer>>(ofertas.size());
				ArrayList<Double> espacioEnOferta = new ArrayList<Double>();
				for (int i = 0; i < ofertas.size(); ++i)
					espacioEnOferta.add(ofertas.get(i).getPesomax());
				int idPaquete = 0;
				for (int idOferta = 0; idOferta < ofertasAPaquetes.size(); ++idOferta) {
					while (idPaquete < paquetes.size()) {
						if (paquetes.get(idPaquete).getPeso() < espacioEnOferta.get(idOferta)) {
							paquetesAOfertas.set(idPaquete, new Integer(idOferta));
							ofertasAPaquetes.get(idOferta).add(idPaquete);
							espacioEnOferta.set(idOferta,
									espacioEnOferta.get(idOferta) - paquetes.get(idPaquete).getPeso());
						}
						++idPaquete;
					}
				}
				for (int i = 0; i < pesosActuales.length; ++i)
					pesosActuales[i] = ofertas.get(i).getPesomax() - espacioEnOferta.get(i);
			default:
				throw new IllegalArgumentException("No existe este algoritmo");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	void intercambiar(Integer p1, Integer p2) {
		Paquete pac1 = paquetes.get(p1);
		Paquete pac2 = paquetes.get(p2);
		// Oferta of1 = ofertas.get(paquetesAOfertas.get(p1));
		// Oferta of2 = ofertas.get(paquetesAOfertas.get(p2));

		// Los índices de las ofertas
		Integer o1 = paquetesAOfertas.get(p1);
		Integer o2 = paquetesAOfertas.get(p2);

		if (o1.equals(o2))
			return;

		LinkedList<Integer> l = ofertasAPaquetes.get(o1);
		l.remove(p1);
		l.add(p2);

		l = ofertasAPaquetes.get(o2);
		l.remove(p2);
		l.add(p1);

		paquetesAOfertas.set(p1, o2);
		paquetesAOfertas.set(p2, o1);

		System.out.println("El valor de o1 y o2 es " + o1 + " " + o2);
		System.out.println("La cantidad de ofertas que hay es " + ofertas.size());
		if (pac2 == null) System.out.println("pac2 es nulo");
		if (pac1 == null) System.out.println("pac1 es nulo");
		pesosActuales[o1] += pac2.getPeso() - pac1.getPeso();
		pesosActuales[o2] += pac1.getPeso() - pac2.getPeso();
	}

	void mover(Integer p, Integer t) {
		Paquete pac = paquetes.get(p);

		// La oferta en la que está actualmente
		// Oferta of1 = ofertas.get(paquetesAOfertas.get(p));

		// La oferta a la que quiere ir
		// Oferta of2 = ofertas.get(t);

		// Los índices de la oferta en la que está actualmente
		Integer o = paquetesAOfertas.get(p);

		ofertasAPaquetes.get(o).remove(p); // Quitarlo de donde estaba antes
		ofertasAPaquetes.get(o).add(p); // Ponerlo en la nueva oferta

		paquetesAOfertas.set(p, t); // Decir que está en la nueva oferta

		pesosActuales[o] -= pac.getPeso();
		pesosActuales[t] += pac.getPeso();
	}

	void ponerPrecioAlmacenamiento(double nuevoPrecio) {
		precioAlmacenamiento = nuevoPrecio;
	}

	// Constructores

	public Estado(ArrayList<LinkedList<Integer>> OAP, ArrayList<Integer> PAO, double[] pesos) {
		System.out.println("El tamaño de pesos es: " + pesos.length);
		System.out.println("El tamaño de OAP es: " + OAP.size());
		if (OAP == null || PAO == null) {
			System.out.println("Alguno de los parametros pasados es nulo_____________________________");
			//throw new Exception();
		}
		if (OAP.size() != pesos.length) {
			System.out.println("La cantidad de ofertas y los pesos no son iguales");
			//throw new Exception();
		}
		paquetesAOfertas = new ArrayList<Integer>();
		ofertasAPaquetes = new ArrayList<LinkedList<Integer>>();
		pesosActuales = new double[OAP.size()];
		if (OAP == null) System.out.println("Tenemos un objeto nulo");
		for (Integer i = 0; i < OAP.size(); ++i) {
			pesosActuales[i] = pesos[i];
			LinkedList<Integer> miLista = new LinkedList<Integer>();
			for (Integer miInt : OAP.get(i)) {
				miLista.add(Integer.valueOf(miInt));
			}
			ofertasAPaquetes.add(miLista);
		}

		for (Integer i = 0; i < PAO.size(); ++i) {
			paquetesAOfertas.add(Integer.valueOf(PAO.get(i)));
		}
		System.out.println("Fin del metodo");
	}

	public ArrayList<LinkedList<Integer>> getOAP() {
		if (ofertasAPaquetes == null) System.out.println("Patata");
		return ofertasAPaquetes;
	}

	public ArrayList<Integer> getPAO() {
		return paquetesAOfertas;
	}
	public double[] getPesosActuales() {
		return pesosActuales;
	}

}
