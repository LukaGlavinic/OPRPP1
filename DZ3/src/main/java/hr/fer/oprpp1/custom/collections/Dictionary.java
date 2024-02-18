package hr.fer.oprpp1.custom.collections;

public class Dictionary<K, V> {

	private final ArrayIndexedCollection<Pair<K, V>> poljeMape;
	

	private static class Pair<P, S> {
		private P kljuc;
		private S vrijednost;
	}
	
	public Dictionary(){
		this.poljeMape = new ArrayIndexedCollection<>();
	}
	/**
	 * provjerava da li je rije�nik prazan
	 * @return true ako je prazan, ina�e false
	 */
	public boolean isEmpty() {
		return poljeMape.isEmpty();
	}
	/**
	 * vra�a broj elemenata u rije�niku
	 * @return trenutni broj elemenata u rije�niku
	 */
	public int size() {
		return poljeMape.size();
	}
	/**
	 * bri�e sve elemente iz rije�nika
	 */
	public void clear() {
		poljeMape.clear();
	}
	/**
	 * stavlja zadani par u rije�nik
	 * @param key zadani klju�
	 * @param value zadana vrijednost
	 * @return null ako nije postojao taj klju� ili stara vrijednost
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Predani klju� je null!");
		}
		Pair<K, V> trenutniPar;
		V povratna;
		for(int i = 0; i < poljeMape.size(); i++) {
			trenutniPar = (Dictionary.Pair<K, V>) poljeMape.get(i);
			if(trenutniPar.kljuc.equals(key)) {
				povratna = trenutniPar.vrijednost;
				trenutniPar.vrijednost = value;
				return povratna;
			}
		}
		Pair<K, V> noviPar = new Pair<>();
		noviPar.kljuc = key;
		noviPar.vrijednost = value;
		poljeMape.add(noviPar);
		return null;
	}
	/**
	 * vra�a vrijednost od zadanog klju�a iz rije�nika
	 * @param key klju� �ija se vrijednost tra�i
	 * @return vrijednost, ina�e null
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		Pair<K, V> trenutniPar;
		for(int i = 0; i < poljeMape.size(); i++) {
			trenutniPar = (Dictionary.Pair<K, V>) poljeMape.get(i);
			if(trenutniPar.kljuc == key) {
				return trenutniPar.vrijednost;
			}
		}
		return null;
	}
	/**
	 * bri�e klju� i njegovu vrijednost van iz rije�nika
	 * @param key klju� �iji se par �eli bristi
	 * @return obrisanu vrijednost, ina�e null
	 */
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		if(key == null) {
			throw new NullPointerException("Predani klju� je null!");
		}
		Pair<K, V> trenutniPar;
		V povratna;
		for(int i = 0; i < poljeMape.size(); i++) {
			trenutniPar = (Dictionary.Pair<K, V>) poljeMape.get(i);
			if(trenutniPar.kljuc == key) {
				povratna = trenutniPar.vrijednost; 
				trenutniPar.kljuc = null;
				trenutniPar.vrijednost = null;
				poljeMape.remove(i);
				return povratna;
			}
		}
		return null;
	}
}