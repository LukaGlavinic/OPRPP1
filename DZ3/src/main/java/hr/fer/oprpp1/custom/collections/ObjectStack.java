package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T>{
	
	@SuppressWarnings("rawtypes")
	private ArrayIndexedCollection unutarnjePolje;
	/**
	 * konstruktor za stvaranje stoga objekata
	 */
	@SuppressWarnings("rawtypes")
	public ObjectStack() {
		this.unutarnjePolje = new ArrayIndexedCollection();
	}
	/**
	 * metoda vraća da li je stog prazan
	 * @return istina ako je prazan
	 */
	public boolean isEmpty() {
		return this.unutarnjePolje.isEmpty();
	}
	/**
	 * metoda vraća broj elemenata stoga
	 * @return broj elemenata stoga
	 */
	public int size() {
		return this.unutarnjePolje.size();
	}
	/**
	 * stavlja objekt na stog
	 * @param value: dodani objekt
	 */
	@SuppressWarnings("hiding")
	public <T> void push(T value) {
		this.unutarnjePolje.add(value);
	}
	/**
	 * skida objekt sa stoga
	 * @return skinuti objekt
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T pop() {
		if(this.size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			T zadnji = (T) this.unutarnjePolje.get(unutarnjePolje.size() - 1);
			this.unutarnjePolje.remove(this.unutarnjePolje.size() - 1);
			return zadnji;
		}
	}
	/**
	 * vraća objekt na vrhu stoga, ne mijenja stog
	 * @return objekt na vrhu stoga
	 */
	@SuppressWarnings("unchecked")
	public T peek() {
		if(this.size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			return (T) this.unutarnjePolje.get(this.unutarnjePolje.size() - 1);
		}
	}
	/**
	 * briše stog
	 */
	public void clear() {
		this.unutarnjePolje.clear();
	}
}