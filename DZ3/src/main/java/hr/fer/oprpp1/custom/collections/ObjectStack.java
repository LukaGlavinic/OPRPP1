package hr.fer.oprpp1.custom.collections;

public class ObjectStack<T>{
	
	@SuppressWarnings("rawtypes")
	private final ArrayIndexedCollection unutarnjePolje;
	/**
	 * konstruktor za stvaranje stoga objekata
	 */
	@SuppressWarnings("rawtypes")
	public ObjectStack() {
		unutarnjePolje = new ArrayIndexedCollection();
	}
	/**
	 * metoda vraća da li je stog prazan
	 * @return istina ako je prazan
	 */
	public boolean isEmpty() {
		return unutarnjePolje.isEmpty();
	}
	/**
	 * metoda vraća broj elemenata stoga
	 * @return broj elemenata stoga
	 */
	public int size() {
		return unutarnjePolje.size();
	}
	/**
	 * stavlja objekt na stog
	 * @param value: dodani objekt
	 */
	@SuppressWarnings("hiding")
	public <G> void push(G value) {
		unutarnjePolje.add(value);
	}
	/**
	 * skida objekt sa stoga
	 * @return skinuti objekt
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <G> G pop() {
		if(size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			G zadnji = (G) unutarnjePolje.get(unutarnjePolje.size() - 1);
			unutarnjePolje.remove(unutarnjePolje.size() - 1);
			return zadnji;
		}
	}
	/**
	 * vraća objekt na vrhu stoga, ne mijenja stog
	 * @return objekt na vrhu stoga
	 */
	@SuppressWarnings("unchecked")
	public T peek() {
		if(size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			return (T) unutarnjePolje.get(unutarnjePolje.size() - 1);
		}
	}
	/**
	 * briše stog
	 */
	public void clear() {
		unutarnjePolje.clear();
	}
}