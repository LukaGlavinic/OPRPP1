package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class ArrayIndexedCollection<R> implements List {
	/**
	 * Kolekcija ostvarena pomo�u polja
	 */
	private int size;
	private R[] elements;
	private long modificationCount;
	/**
	 * nadja�ana metoda iz su�elja Collection za stvaranje posebne ina�ice ElementsGettea za pristup �lanovima polja
	 */
	@Override
	public ArrayElementsGetter createElementsGetter() {

        return new ArrayElementsGetter(this);
	}
	/**
	 * klasa koja slu�i kao ElementsGetter za objekt tipa ArrayIndexedCollection
	 * @author Luka
	 *
	 */
private static class ArrayElementsGetter implements ElementsGetter{
	
		private final int duljinaPolja;
        private int brojIsporucenih;
		private final ArrayIndexedCollection a;
		private final long savedModificationCount;
		/**
		 * konstruktor za stvaranje ArrayElementsGettera za ArrayIndexedCollection kojeg se predaje u konstruktor
		 * @param arr predano u konstruktor
		 */
		public ArrayElementsGetter(ArrayIndexedCollection arr) {
			a = arr;
			duljinaPolja = arr.size;
			brojIsporucenih = 0;
			savedModificationCount = arr.modificationCount;
		}
		/**
		 * metoda vra�a true ako postoji sljede�i element polja
		 */
		public boolean hasNextElement() {
            return brojIsporucenih != duljinaPolja;
        }
		/**
		 * metoda vra�a objekt iz trenutnog �lana polja
		 */
		public Object getNextElement() {
			if(savedModificationCount != a.modificationCount) {
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection (int initialCapacity) {
		super();
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Zadan premali početni kapacitet!");
		}else {
			size = 0;
			elements = (R[]) new Object[initialCapacity];
			modificationCount = 0;
		}
	}
	/**
	 * Konstruktor koji stvara polje s elementima istim od kolekcije other
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 */
	public ArrayIndexedCollection (Collection<?> other) {
		this(other, other.size());
	}
	/**
	 * Konstruktor koji stvara polje zadane veli�ine i puni ga elementima zadane kolekcije
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 * @param initialCapacity: po�etna veli�ina polja
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection (Collection<?> other, int initialCapacity) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		elements = (R[]) new Object[initialCapacity];
		modificationCount = 0;
		addAll(other);
	}
	/**
	 * Metoda dodaje objekt na kraj polja, ako je premalo mjesta, stvara novo polje dvostrukog kapaciteta
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Predani objekt je null!");
		}else if(size == elements.length){
			Object[] pom = elements;
			elements = (R[]) new Object[size * 2];
			for(int i = 0; i < size; i++) {
				elements[i] = (R) pom[i];
			}
			modificationCount++;
			size++;
			elements[size - 1] = (R) value;
        }else {
			modificationCount++;
			elements[size] = (R) value;
			size++;
		}
	}
	/**
	 * Metoda vra�a element polja na zadanom mjestu
	 * @param index: mjesto s kojeg bi se element htio dobiti
	 * @return element sa zadanog mjesta
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index je ve�i od broja elemenata polja!");
		}else {
			return elements[index];
		}
	}
	/**
	 * metoda bri�e kolekciju
	 */
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	/**
	 * metoda ume�e objekt na poziciju u kolekciju
	 * @param value: objekt koji se ume�e
	 * @param position: pozicija na koju se objek ume�e
	 */
	@SuppressWarnings("unchecked")
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Ubacuje se na krivu poziciju!");
		}else if(size == elements.length) {
			int j = size;
			add(value);
			for(; j > position; j--) {
				elements[j] = elements[j-1];
			}
			elements[position] = (R) value;
		}else {
			for(int i = size; i > position; i--) {
				elements[i] = elements[i - 1];
			}
			elements[position] = (R) value;
			size++;
			modificationCount++;
		}
	}
	/**
	 * metoda vra�a indeks prvog na�enog zadanog objekta u polju, -1 ako ga ne na�e
	 * @param value: tra�eni objekt
	 * @return pozicija prvog pronalaska
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
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
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Krivi index!");
		}else {
			for(int i = index; i < size - 1; i++) {
				elements[i] = elements[i+1];
			}
			elements[size - 1] = null;
			size--;
			modificationCount++;
		}
	}
	/**
	 * metoda vra�a broj elemenata kolekcije
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 * metoda provjerava da li je kolekcija prazna
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * metoda provjerava da li kolekcija sadr�i zadani objekt
	 */
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
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
		Object[] newArray = new Object[size];
        System.arraycopy(elements, 0, newArray, 0, size);
		return newArray;
	}
	/**
	 * metoda bri�e zadani objekt iz kolekcije
	 */
	@Override
	public boolean remove(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
}