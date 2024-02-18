package hr.fer.oprpp1.custom.collections;
/**
 * su�elje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter<T> {
/**
 * metoda ispitiju postojanje sljede�eg elementa
 * @return true ako postoji sljede�i element
 */
	@SuppressWarnings("hiding")
	boolean hasNextElement();
	/**
	 * metoda vra�a objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
    T getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih �lanova
	 * @param p procesor �ija se metoda process poziva za procesiranje preostalih elemenata
	 */
	default <G> void processRemaining(Processor<G> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
