/**
  * 
  */
 package dominio;
 
 import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
 
 /**
  * @author becarint
  *
  */
 public class FuncionSucesoraHillClimbing implements SuccessorFunction {
 
 	@SuppressWarnings("rawtypes")
 	public List getSuccessors(Object state) {
 		Estado estado = (Estado)state;
 		ArrayList retVal = new ArrayList();
 		FuncionHeuristicaSimple FHS = new FuncionHeuristicaSimple();
 		//Todos los posibles swaps
 		for (int i = 0; i < estado.getNumPaquetes(); ++i) {
 			for (int j = i+1; j < estado.getNumPaquetes(); ++j) {
 				if (estado.sePuedeIntercambiar(i,j)) {
 					//borrar esta linea de abajo
 					if (estado.getOAP() == null) System.out.println("Tururú");
 					
 					
 					Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
 					nuevoEst.intercambiar(i, j);
 					String S = "SWAP: " + i + " " + j + " ===> Coste: " + nuevoEst.obtenerCosteEconomico();
 					retVal.add(new Successor(S, nuevoEst));
 				}
 			}
 		}
 		
 		for (int p = 0; p < estado.getNumPaquetes(); ++p) {
 			for (int o = 0; o < estado.getNumOfertas(); ++o) {
 				if (estado.sePuedeMover(p, o)) {
 					Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
 					nuevoEst.mover(p, o);
 					String S = "MOVE: pac " + p + " a  of " + o + " ===> Coste: " + nuevoEst.obtenerCosteEconomico();
 					retVal.add(new Successor(S, nuevoEst));
 				}
 			}
 		}
 		System.out.println("Hemos generado " + retVal.size() + "estados sucesores");
 		return retVal;
 	}
 
 }