package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class ArrayIndexedCollection implements List {
	/**
	 * Kolekcija ostvarena pomoću polja
	 */
	private int size;
	private Object[] elements;
	private long modificationCount;
	/**
	 * nadjačana metoda iz sučelja Collection za stvaranje posebne inačice ElementsGettea za pristup članovima polja
	 */
	@Override
	public ArrayElementsGetter createElementsGetter() {

        return new ArrayElementsGetter(this);
	}
	/**
	 * klasa koja služi kao ElementsGetter za objekt tipa ArrayIndexedCollection
	 * @author Luka
	 *
	 */
public static class ArrayElementsGetter implements ElementsGetter{
	
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
		 * metoda vraća true ako postoji sljedeći element polja
		 */
		public boolean hasNextElement() {
            return brojIsporucenih != duljinaPolja;
        }
		/**
		 * metoda vraća objekt iz trenutnog člana polja
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
	 * Ovo je inicijalni konstruktor koji stvara polje velićine 16
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
			size = 0;
			elements = new Object[initialCapacity];
			modificationCount = 0;
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
	 * Konstruktor koji stvara polje zadane velićine i puni ga elementima zadane kolekcije
	 * @param other: kolekcija koja se kopira u stvoreno polje
	 * @param initialCapacity: početna velićina polja
	 */
	public ArrayIndexedCollection (Collection other, int initialCapacity) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		elements = new Object[initialCapacity];
		modificationCount = 0;
		this.addAll(other);
	}
	/**
	 * Metoda dodaje objekt na kraj polja, ako je premalo mjesta, stvara novo polje dvostrukog kapaciteta
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Predani objekt je null!");
		}else if(size == elements.length){
			Object[] pom = elements;
			elements = new Object[size * 2];
            System.arraycopy(pom, 0, elements, 0, size);
			modificationCount++;
			size++;
			elements[size - 1] = value;
        }else {
			modificationCount++;
			elements[size] = value;
			size++;
		}
	}
	/**
	 * Metoda vraća element polja na zadanom mjestu
	 * @param index: mjesto s kojeg bi se element htio dobiti
	 * @return element sa zadanog mjesta
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index je veći od broja elemenata polja!");
		}else {
			return elements[index];
		}
	}
	/**
	 * metoda briše kolekciju
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
	 * metoda umeće objekt na poziciju u kolekciju
	 * @param value: objekt koji se umeće
	 * @param position: pozicija na koju se objek umeće
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Ubacuje se na krivu poziciju!");
		}else if(size == elements.length) {
			int j = size;
			this.add(value);
			for(; j > position; j--) {
				elements[j] = elements[j-1];
			}
			elements[position] = value;
		}else {
			for(int i = size; i > position; i--) {
				elements[i] = elements[i - 1];
			}
			elements[position] = value;
			size++;
			modificationCount++;
		}
	}
	/**
	 * metoda vraća indeks prvog nađenog zadanog objekta u polju, -1 ako ga ne nađe
	 * @param value: traženi objekt
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
	 * metoda briše objekt na zadanoj poziciji ili baca iznimku
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
	 * metoda vraća broj elemenata kolekcije
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
	 * metoda provjerava da li kolekcija sadrži zadani objekt
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
	 * metoda vraća polje objekata iz kolekcije
	 */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[size];
        System.arraycopy(elements, 0, newArray, 0, size);
		return newArray;
	}
	/**
	 * metoda briše zadani objekt iz kolekcije
	 */
	@Override
	public boolean remove(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}
}