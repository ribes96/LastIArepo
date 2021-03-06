package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

public class FuncionSucesoraSimulatedAnnealing implements SuccessorFunction {

	@SuppressWarnings("rawtypes")
	public List getSuccessors(Object state) {
		Estado estado = (Estado)state;
		ArrayList retVal = new ArrayList();
		FuncionHeuristicaSimple FHS = new FuncionHeuristicaSimple();
		Random myRandom = new Random();
		Integer numMoves = estado.getNumPaquetes()*estado.getNumOfertas();
		Integer numSwaps = (estado.getNumPaquetes()*estado.getNumPaquetes()-1)/2;
		Integer cosa = myRandom.nextInt(numMoves + numSwaps);
		if (cosa < numMoves) {
			//moves
			/*
			Integer aleaPac = myRandom.nextInt(estado.getNumPaquetes());
			Integer aOferta;
			do {
				aOferta = myRandom.nextInt(estado.getNumOfertas());
			} while (aOferta.equals(estado.getPAO().get(aleaPac)) || !estado.sePuedeMover(aleaPac, aOferta));
			Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
			
			
			nuevoEst.mover(aleaPac, aOferta);
			double v = FHS.getHeuristicValue(nuevoEst);
			String S = Estado.MOVE + " " + aleaPac + " " + aOferta + " Coste(" + v +") ---> " + nuevoEst.aString();
			retVal.add(new Successor(S, nuevoEst));
			*/
			Integer aleaPac; 
			Integer aOferta;
			do {
				aleaPac = myRandom.nextInt(estado.getNumPaquetes());
				aOferta = myRandom.nextInt(estado.getNumOfertas());
			} while (aOferta.equals(estado.getPAO().get(aleaPac)) || !estado.sePuedeMover(aleaPac, aOferta));
			Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
			nuevoEst.mover(aleaPac, aOferta);
			double v = FHS.getHeuristicValue(nuevoEst);
			//String S = "MOVE  " + aleaPac + " " + aOferta + " Coste:" + v +" => " + nuevoEst.obtenerCosteEconomico();
			String S = "MOVE  " + aleaPac + " a " + aOferta + " Coste:" + v;
			//Sacamos por terminal el resultado, puesto que no hemos sido capaces de
			//solucionar el error que teníamos, y no podíamos ver el coste final.
			System.out.println(S);
			retVal.add(new Successor(S, nuevoEst));
		}
		else {
			//swaps
			/*
			Integer i = myRandom.nextInt(estado.getNumPaquetes());
			Integer j;
			do {
				j = myRandom.nextInt(estado.getNumPaquetes());
			} while (i.equals(j) || !estado.sePuedeIntercambiar(i, j));
			Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
				nuevoEst.intercambiar(i, j);
				double v = FHS.getHeuristicValue(nuevoEst);
				String S = Estado.SWAP + " " + i + " " + j + " Coste:" + v +" ---> " + nuevoEst.aString();
				retVal.add(new Successor(S, nuevoEst));
				*/
			Integer i;
			Integer j;
			do {
				i = myRandom.nextInt(estado.getNumPaquetes());
				j = myRandom.nextInt(estado.getNumPaquetes());
			} while (i.equals(j) || !estado.sePuedeIntercambiar(i, j));
			Estado nuevoEst = new Estado(estado.getOAP(),estado.getPAO(), estado.getPesosActuales());
			nuevoEst.intercambiar(i, j);
			double v = FHS.getHeuristicValue(nuevoEst);
			//String S = "SWAP  " + i + " " + j + " Coste:" + v +"=> " + nuevoEst.obtenerCosteEconomico();
			String S = "SWAP  " + i + " " + j + " Coste:" + v;;
			System.out.println(S);
			//Sacamos por terminal el resultado, puesto que no hemos sido capaces de
			//solucionar el error que teníamos, y no podíamos ver el coste final.
			retVal.add(new Successor(S, nuevoEst));
		}
		return retVal;
	}
}
