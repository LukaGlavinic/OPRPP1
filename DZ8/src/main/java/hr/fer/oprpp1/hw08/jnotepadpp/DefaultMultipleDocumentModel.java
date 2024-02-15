package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel{

	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> listaDokumenata;
	private ImageIcon nespremljeno, spremljeno;
	private int current;
	private List<MultipleDocumentListener> listaSlusaca;
	
	private ImageIcon ucitajCrvenu() {
		InputStream is = this.getClass().getResourceAsStream("icons/redD.png");
		if(is == null) {
			throw new NullPointerException("Ne mogu �itati iz mape za crvenu ikonu, tok je null!");
		}
		byte[] bytes;
		try {
			bytes = is.readAllBytes();
			is.close();
			ImageIcon iI = new ImageIcon(bytes);
			Image image = iI.getImage();
			Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} catch (IOException e) {
			System.out.println("Gre�ka u �itanju crvene ikone u polje bajtova!");
		}
		return null;
	}
	
	private ImageIcon ucitajZelenu() {
		InputStream is = this.getClass().getResourceAsStream("icons/greenD.png");
		if(is == null) {
			throw new NullPointerException("Ne mogu �itati iz mape za zelenu ikonu, tok je null!");
		}
		byte[] bytes;
		try {
			bytes = is.readAllBytes();
			is.close();
			ImageIcon iI = new ImageIcon(bytes);
			Image image = iI.getImage();
			Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
			return new ImageIcon(newimg);
		} catch (IOException e) {
			System.out.println("Gre�ka u �itanju zelenu ikone u polje bajtova!");
		}
		return null;
	}

	public DefaultMultipleDocumentModel() {
		current = 0;
		listaDokumenata = new ArrayList<>();
		listaSlusaca = new ArrayList<>();
		spremljeno = ucitajZelenu();
		nespremljeno = ucitajCrvenu();
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				current = getSelectedIndex();
			}
		});
	}

	public Iterator<SingleDocumentModel> iterator() {
		return this.iterator();
	}

	public JComponent getVisualComponent() {
		return this;
	}
	
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel noviDok = new DefaultSingleDocumentModel(null, "");
		listaDokumenata.add(noviDok);
		current = listaDokumenata.size() - 1;
		JScrollPane sP = new JScrollPane(noviDok.getTextComponent());
		addTab("(undefined)", nespremljeno, sP);
		setSelectedIndex(current);
		return noviDok;
	}

	public SingleDocumentModel getCurrentDocument() {
		return listaDokumenata.get(current);
	}

	public SingleDocumentModel loadDocument(Path path) {
		if(path == null) {
			throw new NullPointerException("Zadana putanja je null!");
		}
		byte[] okteti;
		SingleDocumentModel ucitaniDok = null;
		try {
			int indexUEditoru = getIndexOfDocument(findForPath(path));
			if(indexUEditoru != -1) {
				ucitaniDok = listaDokumenata.get(indexUEditoru);
				current = indexUEditoru;
			}else {
				okteti = Files.readAllBytes(path);
				String ime = path.toFile().getName();
				String tekst = new String(okteti, StandardCharsets.UTF_8);
				ucitaniDok = new DefaultSingleDocumentModel(path, tekst);
				ucitaniDok.setModified(false);
				listaDokumenata.add(ucitaniDok);
				current = getIndexOfDocument(ucitaniDok);
				JTextArea tA = new JTextArea(tekst);
				JScrollPane sP = new JScrollPane(tA);
				addTab(ime, spremljeno, sP);
			}
			setSelectedIndex(current);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Greska pri u�itavanju dokumenta!");
		}
		return ucitaniDok;
	}

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		model.setFilePath(newPath);
		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, podatci);
			model.setModified(false);
		}catch (IOException e1) {
			JOptionPane.showMessageDialog(
					this, 
					"Pogre�ka prilikom zapisivanja datoteke " + newPath.toFile().getAbsolutePath() + ".\nPa�nja: nije jasno u kojem je stanju datoteka na disku!", 
					"Pogre�ka", 
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void closeDocument(SingleDocumentModel model) {
		if(current != 0) {
			remove(current);
			listaDokumenata.remove(model);
			setSelectedIndex(current);
		}else {
			remove(0);
			listaDokumenata.remove(model);
			if(listaDokumenata.size() > 0) {
				setSelectedIndex(0);
			}
		}
	}

	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listaSlusaca.add(l);
	}

	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listaSlusaca.remove(l);
	}

	public int getNumberOfDocuments() {
		return listaDokumenata.size();
	}

	public SingleDocumentModel getDocument(int index) {
		return listaDokumenata.get(index);
	}

	public SingleDocumentModel findForPath(Path path) {
		if(path == null) {
			throw new NullPointerException("Zadana putanja je null!");
		}
		for(SingleDocumentModel sdm : listaDokumenata) {
			if(path.equals(sdm.getFilePath())) {
				return sdm;
			}
		}
		return null;
	}

	public int getIndexOfDocument(SingleDocumentModel doc) {
		for(int i = 0; i < listaDokumenata.size(); i++) {
			if(listaDokumenata.get(i).equals(doc)) {
				return i;
			}
		}
		return -1;
	}
}