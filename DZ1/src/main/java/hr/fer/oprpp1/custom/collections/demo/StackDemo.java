package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
/**
 * 
 * @param args Izraz za izračun
 * @throws NumberFormatException ako je u izrazu neki krivi simbol
 */
	public static void main(String[] args) throws NumberFormatException {
		String izraz = args[0];
		ObjectStack stog = new ObjectStack();
		String[] clanoviIzraza = izraz.split(" ");
		int prvi, drugi;
		for(int i = 0; i < clanoviIzraza.length; i++) {
			if(!clanoviIzraza[i].equals("+") && !clanoviIzraza[i].equals("-")
					&& !clanoviIzraza[i].equals("*") && !clanoviIzraza[i].equals("/")
					&& !clanoviIzraza[i].equals("%")
					&& !clanoviIzraza[i].equals("cubed")
					&& !clanoviIzraza[i].equals("bigger")) {
				prvi = Integer.parseInt(clanoviIzraza[i]);
				stog.push(prvi);
			}else {
				if(clanoviIzraza[i].equals("+") && stog.size() > 1) {
					drugi = (int) stog.pop();
					prvi = (int) stog.pop();
					prvi = prvi + drugi;
					stog.push(prvi);
				}else if(clanoviIzraza[i].equals("-") && stog.size() > 1) {
					drugi = (int) stog.pop();
					prvi = (int) stog.pop();
					prvi = prvi - drugi;
					stog.push(prvi);
				}else if(clanoviIzraza[i].equals("*") && stog.size() > 1) {
					drugi = (int) stog.pop();
					prvi = (int) stog.pop();
					prvi = prvi * drugi;
					stog.push(prvi);
				}else if(clanoviIzraza[i].equals("/") && stog.size() > 1) {
					drugi = (int) stog.pop();
					if(drugi == 0) {
						System.out.println("Ne smije se dijeliti s 0!");
						break;
					}else {
						prvi = (int) stog.pop();
						prvi = prvi / drugi;
						stog.push(prvi);
					}
				}else if(clanoviIzraza[i].equals("%") && stog.size() > 1) {
					drugi = (int) stog.pop();
					if(drugi == 0) {
						System.out.println("Ne smije se dijeliti s 0!");
						break;
					}else {
						prvi = (int) stog.pop();
						prvi = prvi % drugi;
						stog.push(prvi);
					}
				}else if(clanoviIzraza[i].equals("bigger") && stog.size() > 1) {
					drugi = (int) stog.pop();
					prvi = (int) stog.pop();
					int rezultat = prvi >= drugi ? prvi : drugi;
					stog.push(rezultat);
				}else if(clanoviIzraza[i].equals("cubed") && stog.size() > 0) {
					prvi = (int) stog.pop();
					int rezultat = (int) Math.pow(prvi, 3);
					stog.push(rezultat);
				}else {
					System.out.println("Nepravilan izraz!");
					break;
				}
			}
		}
		if(stog.size() == 1) {
			System.out.println("Rezultat izraza je: " + stog.pop());
		}else {
			System.out.println("Nešto nije u redu s izrazom!");
		}
	}
}