package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class ArrayIndexedCollection implements List {
	/**
	 * Kolekcija ostvarena pomo�u polja
	 */
	private int size;
	private Object[] elements;
	private long modificationCount;
	/**
	 * nadja�ana metoda iz su�elja Collection za stvaranje posebne ina�ice ElementsGettea za pristup �lanovima polja
	 */
	@Override
	public ArrayElementsGetter createElementsGetter() {
		
		ArrayIndexedCollection.ArrayElementsGetter elementsGetter = new ArrayIndexedCollection.ArrayElementsGetter(this);
		return elementsGetter;
	}
	/**
	 * klasa koja slu�i kao ElementsGetter za objekt tipa ArrayIndexedCollection
	 * @author Luka
	 *
	 */
private static class ArrayElementsGetter implements ElementsGetter{
	
		private int duljinaPolja, brojIsporucenih;
		private ArrayIndexedCollection a;
		private long savedModificationCount;
		/**
		 * konstruktor za stvaranje ArrayElementsGettera za ArrayIndexedCollection kojeg se predaje u konstruktor
		 * @param arr predano u konstruktor
		 */
		public ArrayElementsGetter(ArrayIndexedCollection arr) {
			this.a = arr;
			this.duljinaPolja = arr.size;
			this.brojIsporucenih = 0;
			this.savedModificationCount = arr.modificationCount;
		}
		/**
		 * metoda vra�a true ako postoji sljede�i element polja
		 */
		public boolean hasNextElement() {
			if(brojIsporucenih == duljinaPolja) {
				return false;
			}
			return true;
		}
		/**
		 * metoda vra�a objekt iz trenutnog �lana polja
		 */
		public Object getNextElement() {
			if(this.savedModificationCount != a.modificationCount) {
				throw new ConcurrentModificationException("Bilo je strukturnih izmjena nad poljem!");
			}else {
				if(brojIsporucenih == duljinaPolja || a.elements[brojIsporucenih] == null) {
					throw new NoSuchElementException("Nema više objekata u polju!");
				}
				Object o = a.elements[brojIsporucenih];
				brojIsporucenih++;
				return o;
			}
		}
	}
	
	/**
	 * Ovo je inicijalni konstruktor koji stvara polje veli�ine 16
	 */
	public ArrayIndexedCollection () {
		this(16);
	}
	/**
	 * Ovo je konstruktor za po�etnu veli�inu
	 * @param initialCapacity: po�etna veli�ina polja
	 */
	public ArrayIndexedCollection (int initialCapacity) {
		super();
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Zadan premali početni kapacitet!");
		}else {
			this.size = 0;
			this.elements = new Object[initialCapacity];
			this.modificationCount = 0;
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
	 * Konstruktor koji stvara polje zadane veli�ine i puni ga elementima zadane kolekcije
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 * @param initialCapacity: po�etna veli�ina polja
	 */
	public ArrayIndexedCollection (Collection other, int initialCapacity) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		this.elements = new Object[initialCapacity];
		this.modificationCount = 0;
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
			for(int i = 0; i < this.size; i++) {
				this.elements[i] = pom[i];
			}
			this.modificationCount++;
			this.size++;
			this.elements[this.size - 1] = value;
			pom = null;
		}else {
			this.modificationCount++;
			this.elements[size] = value;
			this.size++;
		}
	}
	/**
	 * Metoda vra�a element polja na zadanom mjestu
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
	 * metoda bri�e kolekciju
	 */
	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.modificationCount++;
		this.size = 0;
	}
	/**
	 * metoda ume�e objekt na poziciju u kolekciju
	 * @param value: objekt koji se ume�e
	 * @param position: pozicija na koju se objek ume�e
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Ubacuje se na krivu poziciju!");
		}else if(this.size == this.elements.length) {
			int j = this.size;
			this.add(value);
			for(; j > position; j--) {
				this.elements[j] = this.elements[j-1];
			}
			this.elements[position] = value;
		}else {
			for(int i = this.size; i > position; i--) {
				this.elements[i] = this.elements[i - 1];
			}
			this.elements[position] = value;
			this.size++;
			this.modificationCount++;
		}
	}
	/**
	 * metoda vra�a indeks prvog na�enog zadanog objekta u polju, -1 ako ga ne na�e
	 * @param value: tra�eni objekt
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
	 * metoda bri�e objekt na zadanoj poziciji ili baca iznimku
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
			this.modificationCount++;
		}
	}
	/**
	 * metoda vra�a broj elemenata kolekcije
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
		return this.size == 0 ? true : false;
	}
	/**
	 * metoda provjerava da li kolekcija sadr�i zadani objekt
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
	 * metoda vra�a polje objekata iz kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			newArray[i] = this.elements[i];
		}
		return newArray;
	}
	/**
	 * metoda bri�e zadani objekt iz kolekcije
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
}