package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DefaultSingleDocumentModel implements SingleDocumentModel{
	
	private Path p;
	private JTextArea textArea;
	private boolean modified;
	private List<SingleDocumentListener> listaSlusaca;

	public DefaultSingleDocumentModel(Path p, String textContent) {
		this.p = p;
		this.textArea = new JTextArea(textContent);
		modified = true;
		listaSlusaca = new ArrayList<>();
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				DefaultSingleDocumentModel.this.setModified(true);
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				DefaultSingleDocumentModel.this.setModified(true);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				DefaultSingleDocumentModel.this.setModified(true);
			}
		});
	}

	public JTextArea getTextComponent() {
		return textArea;
	}

	public Path getFilePath() {
		return p;
	}

	public void setFilePath(Path path) {
		if(path == null) {
			throw new NullPointerException("Zadana putanja je null!");
		}
		p = path;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public void addSingleDocumentListener(SingleDocumentListener l) {
		listaSlusaca.add(l);
	}

	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listaSlusaca.remove(l);
	}
	
	//public void fire() {};
}