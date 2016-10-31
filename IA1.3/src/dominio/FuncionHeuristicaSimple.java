package dominio;

import aima.search.framework.HeuristicFunction;


public class FuncionHeuristicaSimple implements HeuristicFunction {
	/**
	 * Funcion que devuele el coste economico del transporte y almacenamiento de un estado.
	 * @param state Estado que queremos calcular su coste.
	 * @return Coste economico total del estado.
	 */
	public double getHeuristicValue(Object state) {
		Estado estado = (Estado)state;
		return estado.obtenerCosteEconomico();
	}
	
} 