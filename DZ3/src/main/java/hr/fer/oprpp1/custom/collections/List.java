package hr.fer.oprpp1.custom.collections;
/**
 * su�elje s metodama za rad nad kolekcijom
 * @author Luka
 *
 */
public interface List<T> extends Collection<T>{
/**
 * metoda koja vra�a objekt kolekcije na zadanom indexu
 * @param index zadan za pristup �lanu na toj poziciji kolekcije
 * @return objekt �lana kolekcije na zadanom indexu
 */
	@SuppressWarnings("hiding")
    <T> T get(int index);
	/**
	 * metoda ume�e zadanu vrijednost na zadanu poziciju u kolekciju
	 * @param value zadana vrijednost za umetnuti u kolekciju
	 * @param position pozicija umetanja
	 */
	@SuppressWarnings("hiding")
    <T> void insert(T value, int position);
	/**
	 * metoda za vra�anje indeksa predanog objekta ako postoji, ina�e baca iznimku
	 * @param value objekt �ija se pozicija traži i ako postoji vra�a
	 * @return index na kojoj se objekt value nalazi
	 */
	@SuppressWarnings("hiding")
    <T> int indexOf(T value);
	/**
	 * metoda za brisanje �lana na predanom indeksu iz kolekcije
	 * @param index indeks na kojem se iz kolekcije bri�e �lan
	 */
    void remove(int index);
}