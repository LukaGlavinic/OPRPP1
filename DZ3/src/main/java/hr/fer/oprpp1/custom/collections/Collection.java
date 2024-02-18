package hr.fer.oprpp1.custom.collections;

/**
 * sučelje koje definira metode za rad kolekcije
 * @author Luka
 *
 */
public interface Collection<T> {
	
	/**
	 * zadana metoda za metodu proces za svakim objektom od svakog elementa kolekcije uz pomoć ElementsGettera
	 * @param processor procesor koji poziva metodu process
	 */
	@SuppressWarnings("unchecked")
	default <G> void forEach(Processor<G> processor) {
		ElementsGetter<G> geter = (ElementsGetter<G>) createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			processor.process(o);
		}
	}
	
	/**
	 * zadana metoda za dodavanje svih članova kolekcije col koji zadovoljavaju uvijet metode test parametra tester
	 * @param col kolekcija čiji se objekti elementa dadaju u zadanu kolekciju
	 * @param tester metodom tester se testiraju objekti kolekcije col
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> geter = col.createElementsGetter();
		Object o;
		while(geter.hasNextElement()) {
			o = geter.getNextElement();
			if(tester.test(o)) {
				add(o);
			}
		}
	}

	/**
	 * metoda vraća da li je kolekcija prazna
	 * @return istina ako je prazna
	 */
	default boolean isEmpty() {
        return size() == 0;
    }
	/**
	 * metoda vraća broj elemenata kolekcije
	 * @return broj elemenata kolekcije
	 */
    int size();
	/**
	 * metoda za dodavanje objekta u kolekciju
	 * @param value dodani objekt
	 */
    <G> void add(G value);
	/**
	 * metoda provjerava prisutnost objekta
	 * @param value: traženi objekt
	 * @return istina ako objekt postoji u kolekciji
	 */
    <G> boolean contains(G value);
	/**
	 * metoda briše objekt iz kolekcije
	 * @param value: objekt za brisanje
	 * @return istina ako je uspjela obrisati
	 */
	boolean remove(Object value);
	/**
	 * pretvara kolekciju u polje i vraća ga
	 * @return polje objekata
	 */
    T[] toArray();
	/**
	 * briše kolekciju
	 */
    void clear();
	/**
	 * dodaje sve članove kolekcije other u pozvanu
	 * @param other kolekcija za dodavanje
	 */
	default void addAll(Collection<? extends T> other) {
		class CollectionProcessor implements Processor<T>{
			@Override
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
    ElementsGetter<T> createElementsGetter();
}