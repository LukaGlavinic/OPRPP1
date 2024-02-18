package hr.fer.oprpp1.custom.scripting.nodes;

public class DocumentNode extends Node{

	public DocumentNode() {
		super();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    
	    if (obj instanceof DocumentNode dn) {
            if(dn.numberOfChildren() != numberOfChildren()) return false;
	        
	        for(int i = 0; i < numberOfChildren(); i++) {
	            if(!dn.getChild(i).equals(getChild(i))) return false;
	        }
	        return true;
	    }
	    return false;
	}
} 