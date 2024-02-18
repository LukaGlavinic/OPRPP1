package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

public class EvenIntegerTester implements Tester{

	public boolean test(Object obj) {
		if(!(obj instanceof Integer i)) return false;
        return i % 2 == 0;
		}
}