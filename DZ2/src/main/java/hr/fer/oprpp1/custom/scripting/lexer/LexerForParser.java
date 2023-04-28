package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class LexerForParser {

	public char[] data;
	
	private int currentIndex;
	
	private LexToken token;
	
	private boolean uTagu;
	
	public LexerForParser(String text) {
		if(text == null) {
			throw new NullPointerException("Predani tekst je null!");
		}else if(text == "") {
			this.data = new char[1];
			this.data[0] = 0;
		}else{
			this.data = text.toCharArray();
		}
		this.currentIndex = 0;
		this.uTagu = false;
	}
	
	public LexToken nextToken() {
		if(!(this.token == null) && this.token.getType() == LexTokenType.EOF) {
			throw new SmartScriptParserException("Kraj niza!");
		}
		if(this.currentIndex >= this.data.length || this.data[this.currentIndex] == 0) {
			this.token = new LexToken(LexTokenType.EOF, null);
			this.uTagu = false;
			return this.token;
		}
		//while(this.currentIndex != this.data.length) {
			if(!this.uTagu) {
				if(!(this.data[this.currentIndex] == '{' && this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '$')) {//obrađuj tekst
					String rijec;
					if(this.data[this.currentIndex] == '\\') {
						if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == '\\'
								|| this.data[this.currentIndex + 1] == '{')) {
							rijec = Character.toString(this.data[this.currentIndex + 1]);
							this.currentIndex += 2;
						}else {
							throw new SmartScriptParserException("Kriva uporaba znaka \\");
						}
					}else {
					rijec = Character.toString(this.data[this.currentIndex]);
					this.currentIndex++;
					}
					while(this.currentIndex != this.data.length && !(this.data[this.currentIndex] == '{' 
							&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '$')) {
						if(this.data[this.currentIndex] == '\\') {
							if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == '\\'
									|| this.data[this.currentIndex + 1] == '{')) {
								rijec += Character.toString(this.data[this.currentIndex + 1]);
								this.currentIndex += 2;
							}else {
								throw new SmartScriptParserException("Kriva uporaba znaka \\");
							}
						}else {
							rijec += Character.toString(this.data[this.currentIndex]);
							this.currentIndex++;
						}
					}
					this.token = new LexToken(LexTokenType.TEKST, new ElementString(rijec, false));
					this.uTagu = false;
					return this.token;
				}else {//obrađuj tag
					this.currentIndex += 2;
					//while(this.data[this.currentIndex] == '$' && this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(Character.toLowerCase(this.data[this.currentIndex]) == 'e'
								&& this.currentIndex + 1 < this.data.length
								&& !(Character.toLowerCase(this.data[this.currentIndex + 1]) != 'n')
								&& this.currentIndex + 2 < this.data.length
								&& !(Character.toLowerCase(this.data[this.currentIndex + 2]) != 'd')) {//ako je END
							this.currentIndex += 3;
							while(this.data[this.currentIndex] == ' ') {//otkloni razmake
								this.currentIndex++;
							}
							if(!(this.data[this.currentIndex] == '$' && this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}')) {
								throw new SmartScriptParserException("Krivo definiran tag END!");
							}else {
								this.token = new LexToken(LexTokenType.TAGEND, new ElementString("END", false));
								this.uTagu = false;
								this.currentIndex += 2;
								return this.token;
							}
						}else if(Character.toLowerCase(this.data[this.currentIndex]) == 'f'
								&& this.currentIndex + 1 < this.data.length
								&& !(Character.toLowerCase(this.data[this.currentIndex + 1]) != 'o')
								&& this.currentIndex + 2 < this.data.length
								&& !(Character.toLowerCase(this.data[this.currentIndex + 2]) != 'r')) {//ako je FOR
							this.currentIndex += 3;
							while(this.data[this.currentIndex] == ' ') {//otkloni razmake
								this.currentIndex++;
							}
							this.token = new LexToken(LexTokenType.TAGFOR, new ElementString("FOR", false));
							this.uTagu = true;
							return this.token;
						}else if(this.data[this.currentIndex] == '=') {//ako je ECHO
							this.currentIndex++;
							while(this.data[this.currentIndex] == ' ') {//otkloni razmake
								this.currentIndex++;
							}
							this.token = new LexToken(LexTokenType.TAGECHO, new ElementString("ECHO", false));
							this.uTagu = true;
							return this.token;
						}else {
							throw new SmartScriptParserException("KRIVO DEFINIRAN TAG!");
						}
					//}
				}
			}else {//DO TUDA
				//dovrši
				while(this.data[this.currentIndex] == ' ') {//otkloni razmake
					this.currentIndex++;
				}
				/*if(this.data[this.currentIndex] == '$'
						&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
					
				}*/
				if(Character.isLetter(this.data[this.currentIndex])) {//ako je VARIJABLA
					String varijabla = Character.toString(this.data[this.currentIndex]);
					this.currentIndex++;
					while(this.data[this.currentIndex] != ' ' && this.data[this.currentIndex] != '$') {
						if(Character.isLetter(this.data[this.currentIndex]) 
								|| Character.isDigit(this.data[this.currentIndex])
								|| this.data[this.currentIndex] == '_') {
							varijabla += Character.toString(this.data[this.currentIndex]);
						}else {
							throw new SmartScriptParserException("KRIVO DEFINIRANA VARIJABLA!");
						}
						this.currentIndex++;
					}
					while(this.data[this.currentIndex] == ' ') {//otkloni razmake
						this.currentIndex++;
					}
					if(this.data[this.currentIndex] == '$' 
							&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}'){
						this.currentIndex += 2;
						this.token = new LexToken(LexTokenType.VARIABLA, new ElementVariable(varijabla));
						this.uTagu = false;
						return this.token;
					}else {
						this.token = new LexToken(LexTokenType.VARIABLA, new ElementVariable(varijabla));
						this.uTagu = true;
						return this.token;
					}/*else{
						throw new SmartScriptParserException("KRIVO DEFINIRAN KRAJ TAGA!");
					}*/
				}else if(this.data[this.currentIndex] == '@') {//ako je FUNKCIJA
					String funkcija = Character.toString(this.data[this.currentIndex]);;
					//this.currentIndex++;
					if(Character.isLetter(this.data[this.currentIndex + 1])) {
						this.currentIndex++;
						funkcija += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
						while(this.data[this.currentIndex] != ' ' && this.data[this.currentIndex] != '$') {
							if(Character.isLetter(this.data[this.currentIndex]) 
									|| Character.isDigit(this.data[this.currentIndex])
									|| this.data[this.currentIndex] == '_') {
								funkcija += Character.toString(this.data[this.currentIndex]);
							}else {
								throw new SmartScriptParserException("KRIVO DEFINIRANA FUNKCIJA!");
							}
							this.currentIndex++;
						}
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '$' 
								&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}'){
							this.currentIndex += 2;
							this.token = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction(funkcija));
							this.uTagu = false;
							return this.token;
						}else {
							this.token = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction(funkcija));
							this.uTagu = true;
							return this.token;
						}/*else{
							throw new SmartScriptParserException("KRIVO DEFINIRAN KRAJ TAGA!");
						}*/
					}else {
						throw new SmartScriptParserException("KRIVO DEFINIRANA FUNKCIJA!");
					}
				}else if(this.data[this.currentIndex] == '+' 
						|| this.data[this.currentIndex] == '-'
						|| this.data[this.currentIndex] == '*'
						|| this.data[this.currentIndex] == '/'
						|| this.data[this.currentIndex] == '^') {//ako je OPERATOR
					if((this.data[this.currentIndex] == '+' 
							|| this.data[this.currentIndex] == '-') 
							&& this.currentIndex + 1 < this.data.length
							&& Character.isDigit(this.data[this.currentIndex + 1])) {//parsiraj kao broj
						String broj = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
						while(Character.isDigit(this.data[this.currentIndex])) {
							broj += Character.toString(this.data[this.currentIndex]);
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '.') {
							broj += Character.toString(this.data[this.currentIndex]);
							while(Character.isDigit(this.data[this.currentIndex])) {
								broj += Character.toString(this.data[this.currentIndex]);
								this.currentIndex++;
							}
							double brojD = Double.parseDouble(broj);
							while(this.data[this.currentIndex] == ' ') {//otkloni razmake
								this.currentIndex++;
							}
							if(this.data[this.currentIndex] == '$' 
									&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
								this.currentIndex += 2;
								this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantDouble(brojD));
								this.uTagu = false;
							}else {
								this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantDouble(brojD));
								this.uTagu = true;
							}
						}
						int brojI = Integer.parseInt(broj);
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '$' 
								&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
							this.currentIndex += 2;
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(brojI));
							this.uTagu = false;
						}else {
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(brojI));
							this.uTagu = true;
						}
					}else {
						String operator = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
						//parsiraj kao operator
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '$' 
								&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
							this.currentIndex += 2;
							this.token = new LexToken(LexTokenType.OPERATOR, new ElementOperator(operator));
							this.uTagu = false;
							return this.token;
						}else {
							//this.currentIndex++;
							this.token = new LexToken(LexTokenType.OPERATOR, new ElementOperator(operator));
							this.uTagu = true;
							return this.token;
						}
						//this.currentIndex++;
					}
				}else if(this.data[this.currentIndex] == '"') {//ako je string
					this.currentIndex++;
					String string;
					//String slovo;
					if(this.data[this.currentIndex] == '\\') {
						if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == '\\'
								|| this.data[this.currentIndex + 1] == '"')) {
							string = Character.toString(this.data[this.currentIndex + 1]);
							this.currentIndex += 1;
						}else if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == 'n'
								|| this.data[this.currentIndex + 1] == 't'
								|| this.data[this.currentIndex + 1] == 'r')) {
							if(this.data[this.currentIndex + 1] == 'n') {
								string = Character.toString(10);
							}else if(this.data[this.currentIndex + 1] == 'r') {
								string = Character.toString(13);
							}else {
								string = Character.toString(9);
							}
							this.currentIndex++;
						}else{
							throw new SmartScriptParserException("Kriva uporaba znaka \\");
						}
					}else {
						string = Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					while(this.data[this.currentIndex] != '"') {
						if(this.data[this.currentIndex] == '\\') {
							if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == '\\'
									|| this.data[this.currentIndex + 1] == '"')) {
								string += Character.toString(this.data[this.currentIndex + 1]);
								this.currentIndex += 1;
							}else if(this.currentIndex + 1 < this.data.length && (this.data[this.currentIndex + 1] == 'n'
									|| this.data[this.currentIndex + 1] == 't'
									|| this.data[this.currentIndex + 1] == 'r')) {
								if(this.data[this.currentIndex + 1] == 'n') {
									string += Character.toString(10);
								}else if(this.data[this.currentIndex + 1] == 'r') {
									string += Character.toString(13);
								}else {
									string += Character.toString(9);
								}
								this.currentIndex++;
							}else {
								throw new SmartScriptParserException("Kriva uporaba znaka \\");
							}
						}else {
							string += Character.toString(this.data[this.currentIndex]);
						}
						//string += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					this.currentIndex++;
					while(this.data[this.currentIndex] == ' ') {//otkloni razmake
						this.currentIndex++;
					}
					if(this.data[this.currentIndex] == '$' 
							&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}'){
						this.currentIndex += 2;
						this.token = new LexToken(LexTokenType.STRINGUTAGU, new ElementString(string, false));
						this.uTagu = false;
						return this.token;
					}else {
						this.token = new LexToken(LexTokenType.STRINGUTAGU, new ElementString(string, true));
						this.uTagu = true;
						return this.token;
					}/*else{
						throw new SmartScriptParserException("KRIVO DEFINIRAN KRAJ TAGA!");
					}*/
				}else if(Character.isDigit(this.data[this.currentIndex])) {//ako je samo broj
					String broj = Character.toString(this.data[this.currentIndex]);
					this.currentIndex++;
					while(Character.isDigit(this.data[this.currentIndex])) {
						broj += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
					}
					if(this.data[this.currentIndex] == '.') {
						broj += Character.toString(this.data[this.currentIndex]);
						this.currentIndex++;
						while(Character.isDigit(this.data[this.currentIndex])) {
							broj += Character.toString(this.data[this.currentIndex]);
							this.currentIndex++;
						}
						double brojD = Double.parseDouble(broj);
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '$' 
								&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
							this.currentIndex += 2;
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantDouble(brojD));
							this.uTagu = false;
						}else {
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantDouble(brojD));
							this.uTagu = true;
						}
					}else {
						int brojI = Integer.parseInt(broj);
						while(this.data[this.currentIndex] == ' ') {//otkloni razmake
							this.currentIndex++;
						}
						if(this.data[this.currentIndex] == '$' 
								&& this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '}') {
							this.currentIndex += 2;
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(brojI));
							this.uTagu = false;
						}else {
							this.token = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(brojI));
							this.uTagu = true;
						}
					}
				}else{
					throw new SmartScriptParserException("KRIVO DEFINIRAN TAG!");
				}
			}
		//}
		return this.token;
	}
	
	public boolean isuTagu() {
		return uTagu;
	}

	public LexToken getToken() {
		return this.token;
	}
}