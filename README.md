# OPRPP1

Domaće zadaće(DZ) iz predmeta Odabrana poglavlja razvoja programske potpore 1.
Svaka zadaća sadrži datoteku pom.xml i src sa svim izvornim kodovima pisanim u javi.
Te dvije datoteke služe za pokretanjem projekta kao Maven projekta.

DZ1 - Vlastita implementacija kolekcija i jihovih glavnih funkcionalnosti, kao i razreda ArrayIndexedCollection i LinkedListIndexedCollection.
Implementacija razreda ObjectStack koji se služi obrascem strategija sa prethodno implementiranim sučeljem ArrayIndexedCollection.

DZ2 - Implementacija lexera i parsera koristeći implementacije iz DZ1 za imaginarni i pojednostavljeni programski jezik, lexer i parser imaju funkcionalnosti koje simuliraju radnje početnih operacija jezičnog procesora.

DZ3 - Parametrizirana implementacija vlastite tablice raspršnog adresiranja - razred SimpleHashtable<K, V> koja ima mogućnost praćenja popunjenosti i implementira sučelje Iterable<SimpleHashtable.TableEntry<K,V>>. Također implentacija koja prati da se kolekcija ne smije modificirati dok traje iteriranje.

DZ4 - Implementacija jednostavnog alata za rukovanje bazom podataka koja se sastoji od popisa studenata u obliku: JMBAG, ime, prezime, ocjena. Baza se nalazi u obliku tekstualne datoteke u prije zadanoj putanji. Nad bazom se mogu izvršavati upiti s ključnom riječi query, dohvaćanje studenta sa zadanim jmbagom ili filtriranje studenata po zadanim oblikom pojedinog atributa.
Nakon upita se rezultat ispisuje na zaslon sa brojem dohvaćenih studenata. Također se može zadati naredbom showing koji stupci da se ispišu kod prikaza rezultata.

DZ5 - Prvi dio zadaće se sastojao od rada sa kriptiranim datotekama i izrada alata za kriptiranje i dekriptiranje koristeći simetričnu enkripciju, također generiranje sažetka.
Drugi dio se sastojao od implementacije jednostavne ljuske i osnovnim naredbama.
Implementacija podržava naredbe: charsets, cat, ls, tree, copy, mkdir, hexdump, help i exit.
Također podržava manipulaciju simbolima ispisa ljuske na ekran.

DZ6 - Aplikacija za rad s kompleksnim brojevima i polinoma koja koristi aplikacije koja se nalazi u poddirektoriju lib, koja služi za crtanje dobivenog fraktala. Specifično je implementirano crtanje Mandelbrotovog fraktala i fraktala niza Newton-Raphson iteracije. Argumenti koji se zadaju su kompleksni brojevi koji se smatraju korijenima kompleksnog polinoma.

DZ7 - Implementacija vlastitog LayoutManager-a i jednostavnog kalkulatora koji koristi taj LayoutManager za pokretanje Swing aplikacije kalkulatora. Također aplikacija PrimDemo otvara prozor sa dvije liste koje svaka zasebno pritiskom gumba generira idući prosti broj.

DZ8 - Implementacija vlastitog uređivača teksta sa osnovnim funkcionalnostima pomoću Swing-a.
