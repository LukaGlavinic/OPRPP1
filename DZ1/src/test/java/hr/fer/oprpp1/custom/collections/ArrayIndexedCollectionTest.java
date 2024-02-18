package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayIndexedCollectionTest {
	@Test
	public void osnovniKonstruktor() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void konstruktorSInicijalnimKapacitetom() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(7);
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void konstruktorSInicijalnomKolekcijom() {
		Collection c = new Collection();
		ArrayIndexedCollection a = new ArrayIndexedCollection(c);
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void konstruktorSInicijalnimKapacitetomIKolekcijom() {
		Collection c = new Collection();
		ArrayIndexedCollection a = new ArrayIndexedCollection(c, 7);
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void addSNull() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, 
				() -> a.add(null));
	}
	
	@Test
	public void addSDovoljnoMjesta() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		assertEquals("Marko", a.get(0));
	}
	
	@Test
	public void addSNeDovoljnoMjesta() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(1);
		a.add("Marko");
		assertEquals(1, a.toArray().length);
		a.add("Ana");
		assertEquals(2, a.toArray().length);
		assertEquals("Marko", a.get(0));
		assertEquals("Ana", a.get(1));
	}
	
	@Test
	public void getSKrivimIndexom1() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(2);
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.get(-1));
	}
	
	@Test
	public void getSKrivimIndexom2() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(2);
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.get(2));
	}
	
	@Test
	public void getSDobrimIndexom() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(2);
		a.add("Marko");
		a.add("Joso");
		assertEquals("Joso", a.get(1));
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(2);
		a.add("Marko");
		a.add("Joso");
		a.clear();
		assertEquals(0, a.size());
		Object[] actual = a.toArray();
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.get(1));
		int i = 0;
        while (i <= actual.length) {
            i++;
        }
        assertEquals(1, i);
	}
	
	@Test
	public void insertNaKrivuPoziciju1() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.insert("Đuro", 3));
	}
	
	@Test
	public void insertNaKrivuPoziciju2() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.insert("Đuro", -1));
	}
	
	@Test
	public void insertNaDobruPoziciju() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		a.insert("Đuro", 1);
		assertEquals("Đuro", a.get(1));
		assertEquals("Marko", a.get(0));
		assertEquals("Joso", a.get(2));
	}
	
	@Test
	public void insertNaDobruPozicijuSMalimKapacitetom() {
		ArrayIndexedCollection a = new ArrayIndexedCollection(3);
		a.add("Marko");
		a.add("Joso");
		a.add("Ana");
		a.insert("Đuro", 0);
		assertEquals("Đuro", a.get(0));
		assertEquals("Marko", a.get(1));
		assertEquals("Joso", a.get(2));
		assertEquals("Ana", a.get(3));
		int i = a.toArray().length;
		assertEquals(4, i);
	}
	
	@Test
	public void indexOfTrue() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertEquals(1, a.indexOf("Pero"));
	}
	
	@Test
	public void indexOfFalse() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertEquals(-1, a.indexOf("Ana"));
	}
	
	@Test
	public void removeKriviIndex() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.remove(2));
	}
	
	@Test
	public void removeDobarIndex() {
		ArrayIndexedCollection a = new ArrayIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		a.add("Ana");
		a.remove(1);
		assertEquals("Marko", a.get(0));
		assertEquals("Ana", a.get(1));
	}
}