package dominio;

import aima.search.framework.HeuristicFunction;


public class FuncionHeuristicaSimple implements HeuristicFunction {

	public double getHeuristicValue(Object state) {
		Estado estado = (Estado)state;
		return estado.obtenerCosteEconomico();
	}
	
} 