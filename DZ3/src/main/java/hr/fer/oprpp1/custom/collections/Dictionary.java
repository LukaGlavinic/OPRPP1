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
	 * provjerava da li je rijeènik prazan
	 * @return true ako je prazan, inaèe false
	 */
	public boolean isEmpty() {
		return poljeMape.isEmpty();
	}
	/**
	 * vraæa broj elemenata u rijeèniku
	 * @return trenutni broj elemenata u rijeèniku
	 */
	public int size() {
		return poljeMape.size();
	}
	/**
	 * briše sve elemente iz rijeènika
	 */
	public void clear() {
		poljeMape.clear();
	}
	/**
	 * stavlja zadani par u rijeènik
	 * @param key zadani kljuè
	 * @param value zadana vrijednost
	 * @return null ako nije postojao taj kljuè ili stara vrijednost
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Predani kljuè je null!");
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
	 * vraæa vrijednost od zadanog kljuèa iz rijeènika
	 * @param key kljuè èija se vrijednost traži
	 * @return vrijednost, inaèe null
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
	 * briše kljuè i njegovu vrijednost van iz rijeènika
	 * @param key kljuè èiji se par želi bristi
	 * @return obrisanu vrijednost, inaèe null
	 */
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		if(key == null) {
			throw new NullPointerException("Predani kljuè je null!");
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