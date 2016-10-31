package dominio;
 
 import aima.search.framework.HeuristicFunction;
 
 
public class FuncionHeuristicaCombinada implements HeuristicFunction {
 private static final double constant = 1;
	/**
	 * Funcion que devuelve el coste economico en funcion del precio de almacenamiento y transporte, restando la felicidad con una constante
	 * @param state Estado que queremos calcular su coste.
	 * @return Coste economico total del estado.
	 */
 	public double getHeuristicValue(Object state) {
 		Estado estado = (Estado)state;
 		return (estado.obtenerCosteEconomico() - constant*estado.obtenerFelicidad());
 	}	
 } 