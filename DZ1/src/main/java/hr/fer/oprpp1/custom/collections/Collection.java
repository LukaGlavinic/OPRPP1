package hr.fer.oprpp1.custom.collections;

public class Collection {
	/**
	 * konstruktor prazne kolekcije
	 */
	protected Collection() {}
	/**
	 * metoda vraća da li je kolekcija prazna
	 * @return istina ako je prazna
	 */
	public boolean isEmpty() {
		return size() == 0;
    }
	/**
	 * metoda vraća broj elemenata kolekcije
	 * @return broj elemenata kolekcije
	 */
	public int size() {
		return 0;
	}
	/**
	 * metoda za dodavanje objekta u kolekciju
	 * @param value dodani objekt
	 */
	public void add(Object value) {}
	/**
	 * metoda provjerava prisutnost objekta
	 * @param value: traženi objekt
	 * @return istina ako objekt postoji u kolekciji
	 */
	public boolean contains(Object value) {
		return false;
	}
	/**
	 * metoda briše objekt iz kolekcije
	 * @param value: objekt za brisanje
	 * @return istina ako je uspjela obrisati
	 */
	public boolean remove(Object value) {
		return false;
	}
	/**
	 * pretvara kolekciju u polje i vraća ga
	 * @return polje objekata
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	/**
	 * briše kolekciju
	 */
	public void clear() {}
	/**
	 * posebno implementirana za svaku klasu
	 * @param processor: procesira
	 */
	public void forEach(Processor processor) {}
	/**
	 * dodaje sve članove kolekcije other u pozvanu
	 * @param other kolekcija za dodavanje
	 */
	public void addAll(Collection other) {
		class CollectionProcessor extends Processor{
			public CollectionProcessor() {
				super();
			}
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		CollectionProcessor p = new CollectionProcessor();
		other.forEach(p);
	}
}