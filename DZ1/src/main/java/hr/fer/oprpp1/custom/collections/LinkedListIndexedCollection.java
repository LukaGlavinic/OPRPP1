package hr.fer.oprpp1.custom.collections;

public class LinkedListIndexedCollection extends Collection{
	/**
	 * kolekcija ostvarena pomoću povezane liste
	 * @author Luka
	 *
	 */
	private static class ListNode {
		private ListNode previous;
		private ListNode next;
		private Object value;
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	/**
	 * inicijalni konstruktor
	 */
	public LinkedListIndexedCollection() {
		super();
		this.size = 0;
		this.first = this.last = null;
	}
	/**
	 * konstruktor koji stvara listu s elementima iz kolekcije other
	 * @param other: druga kolekcija
	 */
	public LinkedListIndexedCollection(Collection other) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}
		this.size = 0;
		this.first = this.last = null;
		this.addAll(other);
	}
	/**
	 * metoda dodaje objekt u listu
	 */
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Object for adding is null!");
		}else {
			ListNode noviZadnji = new ListNode();
			noviZadnji.value = value;
			if(this.first == null) {
				this.first = this.last = noviZadnji;
				noviZadnji.next = noviZadnji.previous = null;
			}else{
				noviZadnji.previous = this.last;
				noviZadnji.next = null;
				this.last.next = noviZadnji;
				this.last = noviZadnji;
			}
			this.size++;
		}
	}
	/**
	 * metoda vraća objekt na zadanoj poziciji, ne mijenja listu, baca iznimku ako je pozicija kriva
	 * @param index: pozicija objekta
	 * @return objekt na poziciji
	 */
	public Object get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Krivi index!");
		}else {
			Object povratna = null;
			if(index >= this.size / 2) {
				ListNode trenutni = this.last;
				for(int i = this.size - 1; i > this.size / 2 - 1; i--, trenutni = trenutni.previous) {
					if(i == index) {
						povratna = trenutni.value;
					}
				}
			}else {
				ListNode trenutni = this.first;
				for(int i = 0; i < this.size / 2; i++, trenutni = trenutni.next) {
					if(i == index) {
						povratna = trenutni.value;
					}
				}
			}
			return povratna;
		}
	}
	/**
	 * metoda briše listu
	 */
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
	}
	/**
	 * metoda umeće objekt na zadanu poziciju, baca iznimku ako je pozicija kriva
	 * @param value: objekt koji se umeće
	 * @param position: pozicija umetanja
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Kriva pozicija ubacivanja!");
		}else {
			if(position == this.size) {
				this.add(value);
			}else {
				ListNode ubacaj = new ListNode();
				ubacaj.value = value;
				if(position == 0) {
					ubacaj.previous = null;
					ubacaj.next = this.first;
					this.first.previous = ubacaj;
					this.first = ubacaj;
				}else {
					ListNode trenutni = this.first.next;
					for(int i = 1; i < this.size; i++, trenutni = trenutni.next) {
						if(i == position) {
							ubacaj.previous = trenutni.previous;
							ubacaj.next = trenutni;
							trenutni.previous.next = ubacaj;
							trenutni.previous = ubacaj;
						}
					}
				}
				this.size++;
			}
		}
	}
	/**
	 * metoda vraća poziciju danog objekta
	 * @param value: objekt čija se pozicija traži
	 * @return pozicija objekta
	 */
	public int indexOf(Object value) {
		ListNode trenutni = this.first;
		for(int i = 0; i < this.size; i++, trenutni = trenutni.next) {
			if(trenutni.value.equals(value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * metoda briše objekt na danoj poziciji
	 * @param index: pozicija brisanja
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Krivi index izbacivanja!");
		}else {
			ListNode trenutni = this.first;
			if(index == 0) {
				if(this.size == 1) {
					this.first = this.last = null;
				}else {
					this.first = trenutni.next;
					trenutni.next.previous = null;
				}
			}else if(index == this.size - 1) {
				trenutni = this.last;
				trenutni.previous.next = null;
				this.last = trenutni.previous;
			}else {
				for(int i = 0; i <= index && trenutni != null; i++, trenutni = trenutni.next) {
					if(i == index) {
						trenutni.previous.next = trenutni.next;
						trenutni.next.previous = trenutni.previous;
						break;
					}
				}
			}
			trenutni.value = null;
			trenutni.next = trenutni.previous = null;
			trenutni = null;
			this.size--;
		}
	}
	/**
	 * Baca true ako je lista prazna
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0 ? true : false;
	}
	/**
	 * metoda vraća broj elemenata liste
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * metoda govori da li lista sadrži dani objekt
	 */
	@Override
	public boolean contains(Object value) {
		for(ListNode trenutni = this.first; trenutni != null; trenutni = trenutni.next) {
			if(trenutni.value.equals(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * metoda briše i vraća istinu ako se objekt izbrisao
	 */
	@Override
	public boolean remove(Object value) {
		if(value == null) {
			throw new NullPointerException("Predani objekt je null!");
		}else {
			for(ListNode trenutni = this.first; trenutni != null; trenutni = trenutni.next) {
				if(trenutni.value.equals(value)) {
					if(trenutni.previous == null && trenutni.next == null) {
						this.first = this.last = null;
					}else if(trenutni.previous == null) {
						this.first = trenutni.next;
						trenutni.next.previous = null;
					}else if(trenutni.next == null) {
						this.last = trenutni.previous;
						trenutni.previous.next = null;
					}else {
						trenutni.previous.next = trenutni.next;
						trenutni.next.previous = trenutni.previous;
					}
					trenutni.value = null;
					trenutni.next = trenutni.previous = null;
					trenutni = null;
					this.size--;
					return true;
				}
			}
			return false;
		}
	}
	/**
	 * metoda vraća polje objekata liste
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode trenutni = this.first;
		for(int i = 0; i < this.size; trenutni = trenutni.next, i++) {
			array[i] = trenutni.value;
		}
		return array;
	}
	/**
	 * posebna implementacija metode za listu
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode trenutni = this.first;
		while(trenutni != null) {
			processor.process(trenutni.value);
			trenutni = trenutni.next;
		}
	}
}