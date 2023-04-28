package hr.fer.oprpp1.custom.collections;
/**
 * suƒçelje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter {
/**
 * metoda ispitiju postojanje sljedeËeg elementa
 * @return true ako postoji sljedeÊi element
 */
	public boolean hasNextElement();
	/**
	 * metoda vraÊa objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
	public Object getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih Ëlanova
	 * @param p procesor Êija se metoda process poziva za procesiranje preostalih elemenata
	 */
	default public void processRemaining(Processor p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
