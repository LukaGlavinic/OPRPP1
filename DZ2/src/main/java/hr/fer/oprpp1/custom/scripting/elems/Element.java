package hr.fer.oprpp1.custom.scripting.elems;

public class Element {
	
	public Element() {}

	public String asText() {
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof Element el) {

            return el.asText().equals(asText());
        }
        return false;
	}
}