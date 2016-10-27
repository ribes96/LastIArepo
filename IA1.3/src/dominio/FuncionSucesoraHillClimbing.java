/**
  * 
  */
 package dominio;
 
 import java.util.ArrayList;
 import java.util.List;
 
 import aima.search.framework.SuccessorFunction;
 
 /**
  * @author becarint
  *
  */
 public class FuncionSucesoraHillClimbing implements SuccessorFunction {
 
 	@SuppressWarnings("rawtypes")
 	public List getSuccessors(Object state) {
 		Estado estado = (Estado)state;
 		ArrayList<Estado> estados = new ArrayList<Estado>();
 		//Todos los posibles swaps
 		for (int i = 0; i < estado.getNumPaquetes(); ++i) {
 			for (int j = i+1; j < estado.getNumPaquetes(); ++j) {
 				if (estado.sePuedeIntercambiar(i,j)) {
 					//borrar esta linea de abajo
 					if (estado.getOAP() == null) System.out.println("Tururú");
 					Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO());
 					nuevoEst.intercambiar(i, j);
 					estados.add(nuevoEst);
 				}
 			}
 		}
 		
 		for (int p = 0; p < estado.getNumPaquetes(); ++p) {
 			for (int o = 0; o < estado.getNumOfertas(); ++o) {
 				if (estado.sePuedeMover(p, o)) {
 					Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO());
 					nuevoEst.mover(p, o);
 					estados.add(nuevoEst);
 				}
 			}
 		}
 		return estados;
 	}
 
 }