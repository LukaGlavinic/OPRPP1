package hr.fer.oprpp1.hw02.prob1;

public class Lexer {

	public char[] data; // ulazni tekst
	
	private Token token; // trenutni token
	
	private int currentIndex; // indeks prvog neobrađenog znaka
	
	private LexerState trenStanje;
	
	// konstruktor prima ulazni tekst koji se tokenizira
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Predani tekst je null!");
		}else if(text == "") {
			this.data = new char[1];
			this.data[0] = 0;
		}else{
			this.data = text.toCharArray();
		}
		this.currentIndex = 0;
		this.trenStanje = LexerState.BASIC;
	}
	/**
	 * generira i vraća sljedeći token, baca grešku ako je pogrešno pozvan, radi u dva stanja rada
	 * @return
	 */
	public Token nextToken() {
		if(!(this.token == null) && this.token.getType() == TokenType.EOF) {
			throw new LexerException("Kraj niza!");
		}
		//preskoči preznine
		while(this.currentIndex < this.data.length) {
			if(this.data[this.currentIndex] == '\r' || 
					this.data[this.currentIndex] == '\n' || 
					this.data[this.currentIndex] == '\t' || 
					this.data[this.currentIndex] == ' ') {
				this.currentIndex++;
				continue;
			}
			break;
		}
		if(this.currentIndex >= this.data.length || this.data[this.currentIndex] == 0) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		
		while(this.currentIndex != this.data.length) {
			if(this.trenStanje == LexerState.BASIC) {//RAD U STANJE BASIC
				if(Character.isLetter(this.data[this.currentIndex])
						|| this.data[this.currentIndex] == '\\') {
					String rijec;
					if(Character.isLetter(this.data[this.currentIndex])) {
						rijec = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}else if((this.currentIndex + 1 < this.data.length)
							&& (this.data[this.currentIndex + 1] == '\\'
							|| Character.isDigit(this.data[this.currentIndex + 1]))) {
						rijec = Character.toString(this.data[this.currentIndex + 1]);
						this.currentIndex += 2;
					}else {
						throw new LexerException("Samo escape!");
					}
					while(this.currentIndex < this.data.length && (Character.isLetter(this.data[this.currentIndex])
							|| (this.data[this.currentIndex] == '\\'))) {
						if(Character.isLetter(this.data[this.currentIndex])) {
							rijec += Character.toString(this.data[this.currentIndex]);
							this.currentIndex++;
						}else if((this.currentIndex + 1 < this.data.length)
								&& (this.data[this.currentIndex + 1] == '\\'
								|| Character.isDigit(this.data[this.currentIndex + 1]))){
							rijec += Character.toString(this.data[this.currentIndex + 1]);
							this.currentIndex += 2;
						}else {
							throw new LexerException("Samo escape!");
						}
					}
					this.token = new Token(TokenType.WORD, rijec);
					return this.token;
				}else if(this.currentIndex < this.data.length && ((Character.isDigit(this.data[this.currentIndex])) 
						|| 
						((this.currentIndex + 1 < this.data.length) 
								&& ((this.data[this.currentIndex] == '+' && Character.isDigit(this.data[this.currentIndex + 1])))))) {
					String broj;
					if(Character.isDigit(this.data[this.currentIndex])) {
						broj = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}else {
						this.currentIndex++;
						broj = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					while(this.currentIndex < this.data.length && Character.isDigit(this.data[this.currentIndex])) {
						broj += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					try {
						Long brojL = Long.parseLong(broj);
						this.token = new Token(TokenType.NUMBER, brojL);
						return this.token;
					}catch(Exception e) {
						throw new LexerException("Prevelik broj!");
					}
				}else if(this.data[this.currentIndex] == '#') {
					this.token = new Token(TokenType.SYMBOL, this.data[this.currentIndex]);
					this.currentIndex++;
					this.setState(LexerState.EXTENDED);
					return this.token;
				}else {
					this.token = new Token(TokenType.SYMBOL, this.data[this.currentIndex]);
					this.currentIndex++;
					return this.token;
				}
			}else {//dio za Lexer u stanju EXTENDED
				String rijec = null;
				if(this.currentIndex < this.data.length && this.data[this.currentIndex] != '#') {
					rijec = Character.toString(this.data[this.currentIndex]);
					this.currentIndex++;
					while(this.currentIndex < this.data.length && !(this.data[this.currentIndex] == '\r' || 
							this.data[this.currentIndex] == '\n' || 
							this.data[this.currentIndex] == '\t' || 
							this.data[this.currentIndex] == ' ' || this.data[this.currentIndex] == '#')) {
						rijec += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					this.token = new Token(TokenType.WORD, rijec);
					return this.token;
				}else if(this.data[this.currentIndex] == '#') {
					this.token = new Token(TokenType.SYMBOL, this.data[this.currentIndex]);
					this.setState(LexerState.EXTENDED);
					this.currentIndex++;
					return this.token;
				}
			}
		}
		return this.token;
	}
	/**
	 * može se pozivati više puta; ne pokreće generiranje sljedećeg tokena
	 * @return zadnji generirani token
	 */
	public Token getToken() {
		return this.token;
	}
	/**
	 * postavlja stanje rada lexera
	 * @param state
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Predano stanje je null!");
		}
		this.trenStanje = state;
	}
}