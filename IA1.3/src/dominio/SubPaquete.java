package dominio;

import IA.Azamon.Paquete;


public class SubPaquete extends Paquete {
	
	private SubOferta miOferta;
	
	public SubPaquete(double peso, int prioridad) {
		super(peso, prioridad);
		miOferta = null;
	}
	
	/**
	 * Asigna este paquete a la oferta especificada. Si ya estaba asignado a una Oferta deja de estarlo
	 * @param oferta
	 */
	public void asignar(SubOferta oferta) {
		if (miOferta != null) {
			miOferta.quitarPaquete(this);
		}
		oferta.anadirPaquete(this);
		miOferta = oferta;
	}
	
	/**
	 * El paquete dejará de estar asignado a nada
	 */
	public void desasignar() {
		if (miOferta != null) {
			miOferta.quitarPaquete(this);
			miOferta = null;
		}
	}
}