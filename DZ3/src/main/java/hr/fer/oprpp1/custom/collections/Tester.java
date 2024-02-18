package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za testiranje objekata
 * @author Luka
 *
 */
public interface Tester<T> {
	/**
	 * metoda koja testira predani objekt
	 * @param obj objekt za testiranje
	 * @return true ako objekt zadovoljava uvjet testa
	 */
    <G> boolean test(G obj);
}