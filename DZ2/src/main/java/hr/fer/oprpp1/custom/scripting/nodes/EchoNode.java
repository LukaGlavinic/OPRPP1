package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node{

	private final Element[] elements;

	public Element[] getElements() {
		return elements;
	}

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj instanceof EchoNode node) {
            Element[] elementi = node.elements;
        	
        	Element[] elementi2 = getElements();
        	
        	for(int index = 0; index < elementi.length; index++) {
        		if (!elementi[index].equals(elementi2[index]))return false;
        	}
        	return true;
        }
        return false;
	}
	
	@Override
    public String toString() {
       StringBuilder document = new StringBuilder("{$= ");
       for(Element e : elements) {
           document.append(e.asText()).append(" ");
       }
       document.append("$}");
       return document.toString();
    }
}