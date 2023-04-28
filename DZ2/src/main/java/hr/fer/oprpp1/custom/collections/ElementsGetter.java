package hr.fer.oprpp1.custom.collections;
/**
 * sučelje za izradu metoda ElementsGettera
 * @author Luka
 *
 */
public interface ElementsGetter {
/**
 * metoda ispitiju postojanje sljede�eg elementa
 * @return true ako postoji sljede�i element
 */
	public boolean hasNextElement();
	/**
	 * metoda vra�a objekt trenutnog elementa
	 * @return objekt trenutnog elementa
	 */
	public Object getNextElement();
	/**
	 * zadana metoda za procesiranje preostalih �lanova
	 * @param p procesor �ija se metoda process poziva za procesiranje preostalih elemenata
	 */
	default public void processRemaining(Processor p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
