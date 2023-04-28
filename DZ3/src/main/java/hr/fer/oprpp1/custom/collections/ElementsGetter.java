package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter<T> {
/**
 * metoda ispitiju postojanje sljede�eg elementa
 * @return true ako postoji sljede�i element
 */
	@SuppressWarnings("hiding")
	public <T> boolean hasNextElement();
	/**
	 * metoda vra�a objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
	public T getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih �lanova
	 * @param p procesor �ija se metoda process poziva za procesiranje preostalih elemenata
	 */
	@SuppressWarnings("hiding")
	default public <T> void processRemaining(Processor<T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
