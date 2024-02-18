package hr.fer.oprpp1.custom.collections;


public class ArrayIndexedCollection extends Collection {
	/**
	 * Kolekcija ostvarena pomoću polja
	 */
	private int size;
	private Object[] elements;
	/**
	 * Ovo je inicijalni konstruktor koji stvara polje veličine 16
	 */
	public ArrayIndexedCollection () {
		this(16);
	}
	/**
	 * Ovo je konstruktor za početnu veličinu
	 * @param initialCapacity: početna veličina polja
	 */
	public ArrayIndexedCollection (int initialCapacity) {
		super();
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Zadan premali početni kapacitet!");
		}else {
			this.size = 0;
			this.elements = new Object[initialCapacity];
		}
	}
	/**
	 * Konstruktor koji stvara polje s elementima istim od kolekcije other
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 */
	public ArrayIndexedCollection (Collection other) {
		this(other, other.size());
	}
	/**
	 * Konstruktor koji stvara polje zadane veličine i puni ga elementima zadane kolekcije
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 * @param initialCapacity: početna veličina polja
	 */
	public ArrayIndexedCollection (Collection other, int initialCapacity) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		this.elements = new Object[initialCapacity];
		this.addAll(other);
	}
	/**
	 * Metoda dodaje objekt na kraj polja, ako je premalo mjesta, stvara novo polje dvostrukog kapaciteta
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Predani objekt je null!");
		}else if(this.size == this.elements.length){
			Object[] pom = this.elements;
			this.elements = new Object[this.size * 2];
            System.arraycopy(pom, 0, this.elements, 0, this.size);
			this.size++;
			this.elements[this.size - 1] = value;
        }else {
			this.elements[size] = value;
			this.size++;
		}
	}
	/**
	 * Metoda vraća element polja na zadanom mjestu
	 * @param index: mjesto s kojeg bi se element htio dobiti
	 * @return element sa zadanog mjesta
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index je veći od broja elemenata polja!");
		}else {
			return this.elements[index];
		}
	}
	/**
	 * metoda briše kolekciju
	 */
	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
	}
	/**
	 * metoda umeće objekt na poziciju u kolekciju
	 * @param value: objekt koji se umeće
	 * @param position: pozicija na koju se objek umeće
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Ubacuje se na krivu poziciju!");
		}else if(this.size == this.elements.length) {
			this.add(value);
			for(int j = this.size - 1; j > position; j--) {
				this.elements[j] = this.elements[j-1];
			}
			this.elements[position] = value;
		}else {
			for(int i = this.size; i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[position] = value;
			this.size++;
		}
	}
	/**
	 * metoda vraća indeks prvog nađenog zadanog objekta u polju, -1 ako ga ne nađe
	 * @param value: traženi objekt
	 * @return pozicija prvog pronalaska
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * metoda briše objekt na zadanoj poziciji ili baca iznimku
	 * @param index: indeks brisanja
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Krivi index!");
		}else {
			for(int i = index; i < this.size - 1; i++) {
				this.elements[i] = this.elements[i+1];
			}
			this.elements[this.size - 1] = null;
			this.size--;
		}
	}
	/**
	 * metoda vraća broj elemenata kolekcije
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * metoda provjerava da li je kolekcija prazna
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}
	/**
	 * metoda provjerava da li kolekcija sadrži zadani objekt
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * metoda vraća polje objekata iz kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
        System.arraycopy(this.elements, 0, newArray, 0, this.size);
		return newArray;
	}
	/**
	 * metoda briše zadani objekt iz kolekcije
	 */
	@Override
	public boolean remove(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * zove metodu process implementiranu za ovu kolekciju
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < this.size; i++) {
			processor.process(this.get(i));
		}
	}
}