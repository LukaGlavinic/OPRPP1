package hr.fer.oprpp1.custom.collections;

public class ObjectStack{
	
	private ArrayIndexedCollection unutarnjePolje;
	/**
	 * konstruktor za stvaranje stoga objekata
	 */
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
	public void push(Object value) {
		this.unutarnjePolje.add(value);
	}
	/**
	 * skida objekt sa stoga
	 * @return skinuti objekt
	 */
	public Object pop() {
		if(this.size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			Object zadnji = this.unutarnjePolje.get(unutarnjePolje.size() - 1);
			this.unutarnjePolje.remove(this.unutarnjePolje.size() - 1);
			return zadnji;
		}
	}
	/**
	 * vraća objekt na vrhu stoga, ne mijenja stog
	 * @return objekt na vrhu stoga
	 */
	public Object peek() {
		if(this.size() == 0) {
			throw new EmptyStackException("Stog je prazan!");
		}else {
			return this.unutarnjePolje.get(this.unutarnjePolje.size() - 1);
		}
	}
	/**
	 * briše stog
	 */
	public void clear() {
		this.unutarnjePolje.clear();
	}
}