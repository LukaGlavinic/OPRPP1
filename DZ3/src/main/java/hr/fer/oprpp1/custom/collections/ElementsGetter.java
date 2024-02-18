package hr.fer.oprpp1.custom.collections;
/**
 * suèelje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter<T> {
/**
 * metoda ispitiju postojanje sljedeèeg elementa
 * @return true ako postoji sljedeæi element
 */
	@SuppressWarnings("hiding")
	boolean hasNextElement();
	/**
	 * metoda vraæa objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
    T getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih èlanova
	 * @param p procesor æija se metoda process poziva za procesiranje preostalih elemenata
	 */
	default <G> void processRemaining(Processor<G> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
