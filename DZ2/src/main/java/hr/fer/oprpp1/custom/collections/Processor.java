package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za implementirati rad procesora
 * @author Luka
 *
 */
public interface Processor {
	/**
	 * Obavlja neki posao nad objektom
	 * @param value
	 */
	public abstract void process(Object value);
}
