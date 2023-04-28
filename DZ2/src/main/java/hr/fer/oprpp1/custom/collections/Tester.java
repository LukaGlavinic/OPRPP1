package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za testiranje objekata
 * @author Luka
 *
 */
public interface Tester {
/**
 * metoda koja testira predani objekt
 * @param obj objekt za testiranje
 * @return true ako objekt zadovoljava uvjet testa
 */
	public abstract boolean test(Object obj);
}