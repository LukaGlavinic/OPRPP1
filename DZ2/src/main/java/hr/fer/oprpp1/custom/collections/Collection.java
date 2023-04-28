package hr.fer.oprpp1.custom.collections;

/**
 * suƒçelje koje definira metode za rad kolekcije
 * @author Luka
 *
 */
public interface Collection {
	
	/**
	 * zadana metoda za metodu proces za svakim objektom od svakog elementa kolekcije uz pomoÊ ElementsGettera
	 * @param processor procesor koji poziva metodu process
	 */
	default public void forEach(Processor processor) {
		ElementsGetter geter = this.createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			processor.process(o);
		}
	}
	
	/**
	 * zadana metoda za dodavanje svih Ëlanova kolekcije col koji zadovoljavaju uvijet metode test parametra tester
	 * @param col kolekcija Êiji se objekti elementa dadaju u zadanu kolekciju
	 * @param tester metodom tester se testiraju objekti kolekcije col
	 */
	default public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter geter = col.createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			if(tester.test(o)) {
				this.add(o);
			}
		}
	}
	
	/**
	 * metoda vraÊa da li je kolekcija prazna
	 * @return istina ako je prazna
	 */
	default public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * metoda vraÊa broj elemenata kolekcije
	 * @return broj elemenata kolekcije
	 */
	public abstract int size();
	/**
	 * metoda za dodavanje objekta u kolekciju
	 * @param value dodani objekt
	 */
	public abstract void add(Object value);
	/**
	 * metoda provjerava prisutnost objekta
	 * @param value: traûeni objekt
	 * @return istina ako objekt postoji u kolekciji
	 */
	public abstract boolean contains(Object value);
	/**
	 * metoda briöe objekt iz kolekcije
	 * @param value: objekt za brisanje
	 * @return istina ako je uspjela obrisati
	 */
	public abstract boolean remove(Object value);
	/**
	 * pretvara kolekciju u polje i vraÊa ga
	 * @return polje objekata
	 */
	public abstract Object[] toArray();
	/**
	 * bri≈°e kolekciju
	 */
	public abstract void clear();
	/**
	 * dodaje sve Ëlanove kolekcije other u pozvanu
	 * @param other kolekcija za dodavanje
	 */
	default public void addAll(Collection other) {
		class CollectionProcessor implements Processor{
			@Override
			//zaöto je collection.this.add(value) isto öto i add(value)
			public void process(Object value) {
				add(value);
			}
		}
		CollectionProcessor p = new CollectionProcessor();
		other.forEach(p);
	}
	/**
	 * abstraktna metoda za stvaranje objekta ElementsGetter
	 * @return ElementsGetter
	 */
	public abstract ElementsGetter createElementsGetter();
}