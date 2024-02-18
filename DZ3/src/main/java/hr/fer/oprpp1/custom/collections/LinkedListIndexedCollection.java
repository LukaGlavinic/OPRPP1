package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedListIndexedCollection implements List{
	/**
	 * kolekcija ostvarena pomoæu povezane liste
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
	private long modificationCount;
	/**
	 * nadjaèana metoda iz suèelja Collection za stvaranje posebne inaèice ElementsGettea za pristup èlanovima liste
	 */
	@Override
	public ListElementsGetter createElementsGetter() {

        return new ListElementsGetter(this);
	}
	/**
	 * klasa koja služi kao ElementsGetter za objekt tipa LinkedListIndexedCollection
	 * @author Luka
	 *
	 */
	private static class ListElementsGetter implements ElementsGetter{
		private final LinkedListIndexedCollection l;
		private ListNode trenutniCvor;
		private final long savedModificationCount;
		/**
		 * konstruktor za stvaranje ListElementsGetter za LinkedListIndexedCollection kojeg se predaje u konstruktor
		 * @param list predano u konstruktor
		 */
		public ListElementsGetter(LinkedListIndexedCollection list) {
			l = list;
			trenutniCvor = list.first;
			savedModificationCount = list.modificationCount;
		}
		/**
		 * metoda vraæa true ako postoji sljedeæi element liste
		 */
		public boolean hasNextElement() {
			return !(trenutniCvor == null);
		}
		/**
		 * metoda vraæa objekt iz trenutnog èlana liste
		 */
		public Object getNextElement() {
			if(this.savedModificationCount != l.modificationCount) {
				throw new ConcurrentModificationException("Bilo je strukturnih izmjena nad poljem!");
			}else {
				if(trenutniCvor == null || trenutniCvor.value == null) {
					throw new NoSuchElementException("Nema više objekata u listi!");
				}
				Object o = trenutniCvor.value;
				trenutniCvor = trenutniCvor.next;
				return o;
			}
		}
	}
	/**
	 * inicijalni konstruktor
	 */
	public LinkedListIndexedCollection() {
		super();
		this.size = 0;
		this.modificationCount = 0;
		this.first = this.last = null;
	}
	/**
	 * konstruktor koji stvara listu s elementima iz kolekcije other
	 * @param other: druga kolekcija
	 */
	@SuppressWarnings("unchecked")
	public LinkedListIndexedCollection(Collection other) {
		super();
		if(other == null) {
			throw new NullPointerException("Zadana kolekcija je null!");
		}
		this.size = 0;
		this.first = this.last = null;
		this.modificationCount = 0;
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
			this.modificationCount++;
		}
	}
	/**
	 * metoda vraæa objekt na zadanoj poziciji, ne mijenja listu, baca iznimku ako je pozicija kriva
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
		this.modificationCount++;
	}
	/**
	 * metoda umeæe objekt na zadanu poziciju, baca iznimku ako je pozicija kriva
	 * @param value: objekt koji se umeæe
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
				this.modificationCount++;
				this.size++;
			}
		}
	}
	/**
	 * metoda vraæa poziciju danog objekta
	 * @param value: objekt æija se pozicija traži
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
            assert trenutni != null;
            trenutni.value = null;
			trenutni.next = trenutni.previous = null;
            this.size--;
			this.modificationCount++;
		}
	}
	/**
	 * Baca true ako je lista prazna
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}
	/**
	 * metoda vraæa broj elemenata liste
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
	 * metoda briše i vraæa istinu ako se objekt izbrisao
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
                    this.size--;
					this.modificationCount++;
					return true;
				}
			}
			return false;
		}
	}
	/**
	 * metoda vraæa polje objekata liste
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
}