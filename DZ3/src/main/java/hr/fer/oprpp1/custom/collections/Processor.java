package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za implementirati rad procesora
 * @author Luka
 *
 */
public interface Processor<T> {
	/**
	 * Obavlja neki posao nad objektom
	 * @param value
	 */
	@SuppressWarnings("hiding")
	public abstract <T> void process(T value);
}
