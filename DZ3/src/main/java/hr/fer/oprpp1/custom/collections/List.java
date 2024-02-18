package hr.fer.oprpp1.custom.collections;
/**
 * suèelje s metodama za rad nad kolekcijom
 * @author Luka
 *
 */
public interface List<T> extends Collection<T>{
/**
 * metoda koja vraæa objekt kolekcije na zadanom indexu
 * @param index zadan za pristup èlanu na toj poziciji kolekcije
 * @return objekt èlana kolekcije na zadanom indexu
 */
	@SuppressWarnings("hiding")
    <T> T get(int index);
	/**
	 * metoda umeæe zadanu vrijednost na zadanu poziciju u kolekciju
	 * @param value zadana vrijednost za umetnuti u kolekciju
	 * @param position pozicija umetanja
	 */
	@SuppressWarnings("hiding")
    <T> void insert(T value, int position);
	/**
	 * metoda za vraæanje indeksa predanog objekta ako postoji, inaèe baca iznimku
	 * @param value objekt èija se pozicija traÅ¾i i ako postoji vraèa
	 * @return index na kojoj se objekt value nalazi
	 */
	@SuppressWarnings("hiding")
    <T> int indexOf(T value);
	/**
	 * metoda za brisanje èlana na predanom indeksu iz kolekcije
	 * @param index indeks na kojem se iz kolekcije briše èlan
	 */
    void remove(int index);
}