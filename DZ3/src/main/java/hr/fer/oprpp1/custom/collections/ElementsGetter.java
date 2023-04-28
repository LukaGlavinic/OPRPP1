package hr.fer.oprpp1.custom.collections;
/**
 * suƒçelje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter<T> {
/**
 * metoda ispitiju postojanje sljedeËeg elementa
 * @return true ako postoji sljedeÊi element
 */
	@SuppressWarnings("hiding")
	public <T> boolean hasNextElement();
	/**
	 * metoda vraÊa objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
	public T getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih Ëlanova
	 * @param p procesor Êija se metoda process poziva za procesiranje preostalih elemenata
	 */
	@SuppressWarnings("hiding")
	default public <T> void processRemaining(Processor<T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
