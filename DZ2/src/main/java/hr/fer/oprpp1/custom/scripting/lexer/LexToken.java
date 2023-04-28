package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class LexToken {
	
	private LexTokenType type;
	private Element value;
	
	public LexToken(LexTokenType type, Element value) {
		this.type = type;
		this.value = value;
	}

	public Element getValue() {
		return this.value;
	}
	
	public LexTokenType getType() {
		return this.type;
	}
}