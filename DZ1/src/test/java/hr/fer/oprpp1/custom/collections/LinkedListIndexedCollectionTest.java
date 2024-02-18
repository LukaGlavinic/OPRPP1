package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
	@Test
	public void osnovniKonstruktor() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void osnovniKonstruktorSInicijalnomKolekcijom() {
		Collection c = new Collection();
		LinkedListIndexedCollection a = new LinkedListIndexedCollection(c);
		assertEquals(0, a.size());
        assertNotNull(a);
	}
	
	@Test
	public void addSNull() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, 
				() -> a.add(null));
	}
	
	@Test
	public void addSDovoljnoMjesta() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		assertEquals("Marko", a.get(0));
	}
	
	@Test
	public void getSKrivimIndexom1() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.get(-1));
	}
	
	@Test
	public void getSKrivimIndexom2() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.get(2));
	}
	
	@Test
	public void getSDobrimIndexom() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertEquals("Joso", a.get(1));
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
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
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.insert("Đuro", 3));
	}
	
	@Test
	public void insertNaKrivuPoziciju2() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.insert("Đuro", -1));
	}
	
	@Test
	public void insertNaDobruPoziciju() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Joso");
		a.insert("Đuro", 1);
		assertEquals("Đuro", a.get(1));
		assertEquals("Marko", a.get(0));
		assertEquals("Joso", a.get(2));
	}
	
	@Test
	public void indexOfTrue() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertEquals(1, a.indexOf("Pero"));
	}
	
	@Test
	public void indexOfFalse() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertEquals(-1, a.indexOf("Ana"));
	}
	
	@Test
	public void removeKriviIndex() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		assertThrows(IndexOutOfBoundsException.class, 
				() -> a.remove(2));
	}
	
	@Test
	public void removeDobarIndex() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Pero");
		a.add("Ana");
		assertEquals("Marko", a.get(0));
		assertEquals(3, a.size());
		a.remove(1);
		assertEquals("Ana", a.get(1));
	}
	
	@Test
	public void isEmtyTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        assertTrue(a.isEmpty());
		a.add("Marko");
        assertFalse(a.isEmpty());
	}
	
	@Test
	public void sizeTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		assertEquals(0, a.size());
		a.add("Marko");
		assertEquals(1, a.size());
	}
	
	@Test
	public void containsTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
        assertTrue(a.contains("Marko"));
        assertFalse(a.contains("Ana"));
	}
	
	@Test
	public void removeDobarTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Ana");
		assertEquals(2, a.size());
        assertTrue(a.remove("Marko"));
		assertEquals(1, a.size());
	}
	
	@Test
	public void removeKriviTest() {
		LinkedListIndexedCollection a = new LinkedListIndexedCollection();
		a.add("Marko");
		a.add("Ana");
		assertThrows(NullPointerException.class, 
				() -> a.remove(null));
	}
}
