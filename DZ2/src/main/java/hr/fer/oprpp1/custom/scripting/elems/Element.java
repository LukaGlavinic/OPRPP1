package hr.fer.oprpp1.custom.scripting.elems;

import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;

public class Element {
	
	public Element() {}

	public String asText() {
		return new String();
	}
	
	@Override
	public boolean equals(Object obj) {
		//TO DO
		if (this == obj) {
            return true;
        }
        if (obj instanceof Element) {
        	Element el = (Element)obj;
        	
            return el.asText().equals(asText());
        }
        return false;
	}
}