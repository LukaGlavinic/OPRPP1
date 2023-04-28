package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	
	private ArrayIndexedCollection poljeDjece;
	
	//private boolean inicijaliziranoPoljeDjece;
	
	public Node() {
		super();
		//this.inicijaliziranoPoljeDjece = false;
	}

	public void addChildNode(Node child) {
		if(this.poljeDjece == null) {
			this.poljeDjece = new ArrayIndexedCollection();
		}
		this.poljeDjece.add(child);
	}
	
	public int numberOfChildren() {
		return this.poljeDjece.size();
	}
	
	public Node getChild(int index) {
		return (Node) this.poljeDjece.get(index);
	}
	
	@Override
    public String toString() {
       String document = "";
       for(int i = 0; i < this.numberOfChildren(); i++) {
           document += this.getChild(i).toString();
       }
       return document;
    }
    
    /*public String ispisiDjete() {
        
    }*/
}