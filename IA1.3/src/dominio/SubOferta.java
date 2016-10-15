package dominio;

import java.util.LinkedList;

import IA.Azamon.Oferta;

/**
 * 
 * @author Albert Ribes
 *
 */
public class SubOferta extends Oferta {
	private double pesoActual;
	private LinkedList<SubPaquete> misPaquetes;
	
	
	public SubOferta(double pesomax, double precio, int dias) {
		super(pesomax, precio, dias);
		pesoActual = 0;
		misPaquetes = new LinkedList<SubPaquete>();
	}
	
	/**
	 * Retorna una excepción si p ya está asignado a la oferta
	 * @param p
	 */
	public void anadirPaquete(SubPaquete p) {
		try {
			if (misPaquetes.contains(p))throw new Exception();
			misPaquetes.add(p);
			pesoActual += p.getPeso();
		}
		catch(Exception e) {
			System.out.println("El paquete ya está asignado a esta oferta");
		}
	}
	
	/**
	 * Retorna una excepción si p no esta asignado a la oferta
	 * @param p
	 */
	public void quitarPaquete(SubPaquete p) {
		try {
			boolean found = misPaquetes.remove(p);
			if (!found) throw new Exception();
			pesoActual -= p.getPeso();
		}
		catch(Exception e) {
			System.out.println("El paquete que pretendes quitar no esta " +
		"asignado a esta oferta");
		}
	}
	
	/**
	 * 
	 * @param p
	 * @return Retorna si p está asignado a la Oferta
	 */
	public boolean asignado(SubPaquete p) {
		return misPaquetes.contains(p);

	}
	public double getCoste() {
		return getPrecio()*pesoActual;
	}
}
