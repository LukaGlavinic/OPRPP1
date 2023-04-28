package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class EchoNode extends Node{

	private Element[] elements;

	public Element[] getElements() {
		return this.elements;
	}

	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public boolean equals(Object obj) {
		//TO DO
		if (this == obj) {
            return true;
        }
        if (obj instanceof EchoNode) {
        	EchoNode node = (EchoNode)obj;
        	Element[] elementi = node.elements;
        	
        	Element[] elementi2 = this.getElements();
        	
        	for(int index = 0; index < elementi.length; index++) {
        		if (!elementi[index].equals(elementi2[index]))return false;
        	}
        	return true;
        }
        return false;
	}
	
	@Override
    public String toString() {
       String document = "{$= ";
       for(Element e : this.elements) {
           document += e.asText() + " ";
       }
       document += "$}";
       return document;
    }
}