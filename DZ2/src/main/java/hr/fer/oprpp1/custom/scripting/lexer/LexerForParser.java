package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class LexerForParser {

	public char[] data;
	
	private int currentIndex;
	
	private LexToken token;
	
	private boolean uTagu;
	
	public LexerForParser(String text) {
		if(text == null) {
			throw new NullPointerException("Predani tekst je null!");
		}else if(text.isEmpty()) {
			data = new char[1];
		}else{
			data = text.toCharArray();
		}
		currentIndex = 0;
		uTagu = false;
	}
	
	public LexToken nextToken() {
		if(!(token == null) && token.getType() == LexTokenType.EOF) {
			throw new SmartScriptParserException("Kraj niza!");
		}
		if(currentIndex >= data.length || data[currentIndex] == 0) {
			token = new LexToken(LexTokenType.EOF, null);
			uTagu = false;
			return token;
		}
			if(!uTagu) {
				if(!(data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$')) {//obrađuj tekst
					StringBuilder rijec;
					if(data[currentIndex] == '\\') {
						if(currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\'
								|| data[currentIndex + 1] == '{')) {
							rijec = new StringBuilder(Character.toString(data[currentIndex + 1]));
							currentIndex += 2;
						}else {
							throw new SmartScriptParserException("Kriva uporaba znaka \\");
						}
					}else {
					rijec = new StringBuilder(Character.toString(data[currentIndex]));
					currentIndex++;
					}
					while(currentIndex != data.length && !(data[currentIndex] == '{' 
							&& currentIndex + 1 < data.length && data[currentIndex + 1] == '$')) {
						if(data[currentIndex] == '\\') {
							if(currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\'
									|| data[currentIndex + 1] == '{')) {
								rijec.append(data[currentIndex + 1]);
								currentIndex += 2;
							}else {
								throw new SmartScriptParserException("Kriva uporaba znaka \\");
							}
						}else {
							rijec.append(data[currentIndex]);
							currentIndex++;
						}
					}
					token = new LexToken(LexTokenType.TEKST, new ElementString(rijec.toString(), false));
					uTagu = false;
					return token;
				}else {//obrađuj tag
					currentIndex += 2;
						while(data[currentIndex] == ' ') {//otkloni razmake
							currentIndex++;
						}
						if(Character.toLowerCase(data[currentIndex]) == 'e'
								&& currentIndex + 1 < data.length
								&& Character.toLowerCase(data[currentIndex + 1]) == 'n'
								&& currentIndex + 2 < data.length
								&& Character.toLowerCase(data[currentIndex + 2]) == 'd') {//ako je END
							currentIndex += 3;
							while(data[currentIndex] == ' ') {//otkloni razmake
								currentIndex++;
							}
							if(!(data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}')) {
								throw new SmartScriptParserException("Krivo definiran tag END!");
							}else {
								token = new LexToken(LexTokenType.TAGEND, new ElementString("END", false));
								uTagu = false;
								currentIndex += 2;
								return token;
							}
						}else if(Character.toLowerCase(data[currentIndex]) == 'f'
								&& currentIndex + 1 < data.length
								&& Character.toLowerCase(data[currentIndex + 1]) == 'o'
								&& currentIndex + 2 < data.length
								&& Character.toLowerCase(data[currentIndex + 2]) == 'r') {//ako je FOR
							currentIndex += 3;
							while(data[currentIndex] == ' ') {//otkloni razmake
								currentIndex++;
							}
							token = new LexToken(LexTokenType.TAGFOR, new ElementString("FOR", false));
							uTagu = true;
							return token;
						}else if(data[currentIndex] == '=') {//ako je ECHO
							currentIndex++;
							while(data[currentIndex] == ' ') {//otkloni razmake
								currentIndex++;
							}
							token = new LexToken(LexTokenType.TAGECHO, new ElementString("ECHO", false));
							uTagu = true;
							return token;
						}else {
							throw new SmartScriptParserException("KRIVO DEFINIRAN TAG!");
						}
				}
			}else {//DO TUDA
				while(data[currentIndex] == ' ') {//otkloni razmake
					currentIndex++;
				}
				if(Character.isLetter(data[currentIndex])) {//ako je VARIJABLA
					String varijabla = Character.toString(data[currentIndex]);
					currentIndex++;
					while(data[currentIndex] != ' ' && data[currentIndex] != '$') {
						if(Character.isLetter(data[currentIndex]) 
								|| Character.isDigit(data[currentIndex])
								|| data[currentIndex] == '_') {
							varijabla += Character.toString(data[currentIndex]);
						}else {
							throw new SmartScriptParserException("KRIVO DEFINIRANA VARIJABLA!");
						}
						currentIndex++;
					}
					while(data[currentIndex] == ' ') {//otkloni razmake
						currentIndex++;
					}
					if(data[currentIndex] == '$' 
							&& currentIndex + 1 < data.length && data[currentIndex + 1] == '}'){
						currentIndex += 2;
						token = new LexToken(LexTokenType.VARIABLA, new ElementVariable(varijabla));
						uTagu = false;
                    }else {
						token = new LexToken(LexTokenType.VARIABLA, new ElementVariable(varijabla));
						uTagu = true;
                    }
                    return token;
                }else if(data[currentIndex] == '@') {//ako je FUNKCIJA
					String funkcija = Character.toString(data[currentIndex]);
					if(Character.isLetter(data[currentIndex + 1])) {
						currentIndex++;
						funkcija += Character.toString(data[currentIndex]);
						currentIndex++;
						while(data[currentIndex] != ' ' && data[currentIndex] != '$') {
							if(Character.isLetter(data[currentIndex]) 
									|| Character.isDigit(data[currentIndex])
									|| data[currentIndex] == '_') {
								funkcija += Character.toString(data[currentIndex]);
							}else {
								throw new SmartScriptParserException("KRIVO DEFINIRANA FUNKCIJA!");
							}
							currentIndex++;
						}
						while(data[currentIndex] == ' ') {//otkloni razmake
							currentIndex++;
						}
						if(data[currentIndex] == '$' 
								&& currentIndex + 1 < data.length && data[currentIndex + 1] == '}'){
							currentIndex += 2;
							token = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction(funkcija));
							uTagu = false;
                        }else {
							token = new LexToken(LexTokenType.FUNKCIJA, new ElementFunction(funkcija));
							uTagu = true;
                        }
                        return token;
                    }else {
						throw new SmartScriptParserException("KRIVO DEFINIRANA FUNKCIJA!");
					}
				}else if(data[currentIndex] == '+' 
						|| data[currentIndex] == '-'
						|| data[currentIndex] == '*'
						|| data[currentIndex] == '/'
						|| data[currentIndex] == '^') {//ako je OPERATOR
					if((data[currentIndex] == '+' 
							|| data[currentIndex] == '-') 
							&& currentIndex + 1 < data.length
							&& Character.isDigit(data[currentIndex + 1])) {//parsiraj kao broj
						StringBuilder broj = new StringBuilder(Character.toString(data[currentIndex]));
						currentIndex++;
						while(Character.isDigit(data[currentIndex])) {
							broj.append(data[currentIndex]);
							currentIndex++;
						}
						if(data[currentIndex] == '.') {
							broj.append(data[currentIndex]);
							numberTokenGenerator(broj);
						}
						numberTokenGenerator(broj);
					}else {
						String operator = Character.toString(data[currentIndex]);
						currentIndex++;
						//parsiraj kao operator
						while(data[currentIndex] == ' ') {//otkloni razmake
							currentIndex++;
						}
						if(data[currentIndex] == '$' 
								&& currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {
							currentIndex += 2;
							token = new LexToken(LexTokenType.OPERATOR, new ElementOperator(operator));
							uTagu = false;
                        }else {
							token = new LexToken(LexTokenType.OPERATOR, new ElementOperator(operator));
							uTagu = true;
                        }
                        return token;
                    }
				}else if(data[currentIndex] == '"') {//ako je string
					currentIndex++;
					StringBuilder string;
					if(data[currentIndex] == '\\') {
						if(currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\'
								|| data[currentIndex + 1] == '"')) {
							string = new StringBuilder(Character.toString(data[currentIndex + 1]));
							currentIndex += 1;
						}else if(currentIndex + 1 < data.length && (data[currentIndex + 1] == 'n'
								|| data[currentIndex + 1] == 't'
								|| data[currentIndex + 1] == 'r')) {
							if(data[currentIndex + 1] == 'n') {
								string = new StringBuilder(Character.toString(10));
							}else if(data[currentIndex + 1] == 'r') {
								string = new StringBuilder(Character.toString(13));
							}else {
								string = new StringBuilder(Character.toString(9));
							}
							currentIndex++;
						}else{
							throw new SmartScriptParserException("Kriva uporaba znaka \\");
						}
					}else {
						string = new StringBuilder(Character.toString(data[currentIndex]));
						currentIndex++;
					}
					while(data[currentIndex] != '"') {
						if(data[currentIndex] == '\\') {
							if(currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\'
									|| data[currentIndex + 1] == '"')) {
								string.append(data[currentIndex + 1]);
								currentIndex += 1;
							}else if(currentIndex + 1 < data.length && (data[currentIndex + 1] == 'n'
									|| data[currentIndex + 1] == 't'
									|| data[currentIndex + 1] == 'r')) {
								if(data[currentIndex + 1] == 'n') {
									string.append(Character.toString(10));
								}else if(data[currentIndex + 1] == 'r') {
									string.append(Character.toString(13));
								}else {
									string.append(Character.toString(9));
								}
								currentIndex++;
							}else {
								throw new SmartScriptParserException("Kriva uporaba znaka \\");
							}
						}else {
							string.append(data[currentIndex]);
						}
						currentIndex++;
					}
					currentIndex++;
					while(data[currentIndex] == ' ') {//otkloni razmake
						currentIndex++;
					}
					if(data[currentIndex] == '$' 
							&& currentIndex + 1 < data.length && data[currentIndex + 1] == '}'){
						currentIndex += 2;
						token = new LexToken(LexTokenType.STRINGUTAGU, new ElementString(string.toString(), false));
						uTagu = false;
                    }else {
						token = new LexToken(LexTokenType.STRINGUTAGU, new ElementString(string.toString(), true));
						uTagu = true;
                    }
                    return token;
                }else if(Character.isDigit(data[currentIndex])) {//ako je samo broj
					StringBuilder broj = new StringBuilder(Character.toString(data[currentIndex]));
					currentIndex++;
					while(Character.isDigit(data[currentIndex])) {
						broj.append(data[currentIndex]);
						currentIndex++;
					}
					if(data[currentIndex] == '.') {
						broj.append(data[currentIndex]);
						currentIndex++;
						numberTokenGenerator(broj);
					}else {
						numberTokenGenerator(broj);
					}
				}else{
					throw new SmartScriptParserException("KRIVO DEFINIRAN TAG!");
				}
			}
		return token;
	}

	private void numberTokenGenerator(StringBuilder broj) {
		int brojI = Integer.parseInt(broj.toString());
		while(data[currentIndex] == ' ') {//otkloni razmake
			currentIndex++;
		}
		if(data[currentIndex] == '$'
				&& currentIndex + 1 < data.length
				&& data[currentIndex + 1] == '}') {
			currentIndex += 2;
			uTagu = false;
		}else {
			uTagu = true;
		}
		token = new LexToken(LexTokenType.NUMBER, new ElementConstantInteger(brojI));
	}

	public boolean isuTagu() {
		return !uTagu;
	}

	public LexToken getToken() {
		return token;
	}
}