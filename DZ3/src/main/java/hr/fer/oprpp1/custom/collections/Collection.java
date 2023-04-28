package hr.fer.oprpp1.custom.collections;

/**
 * sučelje koje definira metode za rad kolekcije
 * @author Luka
 *
 */
public interface Collection<T> {
	
	/**
	 * zadana metoda za metodu proces za svakim objektom od svakog elementa kolekcije uz pomo� ElementsGettera
	 * @param processor procesor koji poziva metodu process
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	default public <T> void forEach(Processor<T> processor) {
		ElementsGetter<T> geter = (ElementsGetter<T>) this.createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			processor.process(o);
		}
	}
	
	/**
	 * zadana metoda za dodavanje svih �lanova kolekcije col koji zadovoljavaju uvijet metode test parametra tester
	 * @param col kolekcija �iji se objekti elementa dadaju u zadanu kolekciju
	 * @param tester metodom tester se testiraju objekti kolekcije col
	 */
	default public void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> geter = col.createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			if(tester.test(o)) {
				this.add(o);
			}
		}
	}
	
	/*@SuppressWarnings("unchecked")
	default public void copyTransformedIntoIfAllowed(Collection<? extends T> prva, Transformator<? extends T> t, Tester<? super T> tester) {
		ElementsGetter<? extends T> geter = prva.createElementsGetter();
		Object o, o2;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			o2 = t.transform(o);
			if(tester.test(o2)) {
				this.add(o2);
			}
		}
	}*/
	
	
	/**
	 * metoda vra�a da li je kolekcija prazna
	 * @return istina ako je prazna
	 */
	default public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * metoda vra�a broj elemenata kolekcije
	 * @return broj elemenata kolekcije
	 */
	public abstract int size();
	/**
	 * metoda za dodavanje objekta u kolekciju
	 * @param value dodani objekt
	 */
	@SuppressWarnings("hiding")
	public abstract <T> void add(T value);
	/**
	 * metoda provjerava prisutnost objekta
	 * @param value: tra�eni objekt
	 * @return istina ako objekt postoji u kolekciji
	 */
	@SuppressWarnings("hiding")
	public abstract <T> boolean contains(T value);
	/**
	 * metoda bri�e objekt iz kolekcije
	 * @param value: objekt za brisanje
	 * @return istina ako je uspjela obrisati
	 */
	@SuppressWarnings("hiding")
	public abstract <T> boolean remove(Object value);
	/**
	 * pretvara kolekciju u polje i vra�a ga
	 * @return polje objekata
	 */
	public abstract T[] toArray();
	/**
	 * briše kolekciju
	 */
	public abstract void clear();
	/**
	 * dodaje sve �lanove kolekcije other u pozvanu
	 * @param other kolekcija za dodavanje
	 */
	//@SuppressWarnings("hiding")
	default public void addAll(Collection<? extends T> other) {
		class CollectionProcessor implements Processor<T>{
			@Override
			//za�to je collection.this.add(value) isto �to i add(value)
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
	public abstract ElementsGetter<T> createElementsGetter();
}