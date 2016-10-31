package dominio;

import aima.search.framework.GoalTest;


public class PruebaDeFin implements GoalTest {
	/**
	 * Funcion que siempre devuelve falso.
	 * @param state Estado actual.
	 * @return Siempre falso.
	 */
	public boolean isGoalState(Object state) {
		return false;
	}

}
