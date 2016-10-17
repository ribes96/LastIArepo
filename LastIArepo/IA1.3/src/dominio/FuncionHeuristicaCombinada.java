package dominio;

import aima.search.framework.HeuristicFunction;


public class FuncionHeuristicaCombinada implements HeuristicFunction {
private static final double constant = 1;
	
	public double getHeuristicValue(Object state) {
		Estado estado = (Estado)state;
		return (estado.obtenerCosteEconomico() - constant*estado.obtenerFelicidad());
	}	
}