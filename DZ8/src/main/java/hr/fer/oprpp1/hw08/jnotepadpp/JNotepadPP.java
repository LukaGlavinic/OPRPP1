package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JNotepadPP extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private MultipleDocumentModel tabPane;
	private String ccpText;

	public JNotepadPP() {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		setLocation(0, 0);
		setSize(600, 600);
		
		initGUI();
	}

	private void initGUI() {
		tabPane = new DefaultMultipleDocumentModel();
		ccpText = "";
		
		this.getContentPane().add(tabPane.getVisualComponent());
		this.getContentPane().add((Component) tabPane);
		
		createActions();
		createMenus();
		createToolbars();
	}
	
	private void createActions() {
		newDocumentAction.putValue(Action.NAME, 
				"New");
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N"));
		newDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_N);
		newDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to open new file.");
		
		openDocumentAction.putValue(
				Action.NAME, 
				"Open");
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O); 
		openDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to open existing file from disk.");
		
		saveDocumentAction.putValue(
				Action.NAME, 
				"Save");
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S); 
		saveDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to save current file to disk.");
		
		saveAsDocumentAction.putValue(
				Action.NAME, 
				"SaveAs");
		saveAsDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control A")); 
		saveAsDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_A); 
		saveAsDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to set name and save current file to disk.");
		
		closeDocumentAction.putValue(
				Action.NAME, 
				"Close");
		closeDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control E")); 
		closeDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_E); 
		closeDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to close the current tab.");
		
		infoDocumentAction.putValue(
				Action.NAME, 
				"Info");
		infoDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I")); 
		infoDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_I); 
		infoDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to view statistical info of file.");
		
		copyDocumentAction.putValue(
				Action.NAME, 
				"Copy");
		copyDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")); 
		copyDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_C); 
		copyDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to copy copied text.");
		
		cutDocumentAction.putValue(
				Action.NAME, 
				"Cut");
		cutDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control T")); 
		cutDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_T); 
		cutDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to cut copied text.");
		
		pasteDocumentAction.putValue(
				Action.NAME, 
				"Paste");
		pasteDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control P")); 
		pasteDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_P); 
		pasteDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to paste copied text.");
		
		invertDocumentAction.putValue(
				Action.NAME, 
				"InvertCase");
		invertDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control R")); 
		invertDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_R); 
		invertDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to invert case of selected text.");
		
		uppercaseDocumentAction.putValue(
				Action.NAME, 
				"ToUpperCase");
		uppercaseDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control U")); 
		uppercaseDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_U);
		uppercaseDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to set selected text to upper case.");
		
		lowercaseDocumentAction.putValue(
				Action.NAME, 
				"ToLowerCase");
		lowercaseDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control L"));
		lowercaseDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_L);
		lowercaseDocumentAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Used to set selected text to lower case.");
		
		exitAction.putValue(
				Action.NAME, 
				"Exit");
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X); 
		exitAction.putValue(
				Action.SHORT_DESCRIPTION, 
				"Exit application."); 
	}
	
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		JButton open = new JButton(openDocumentAction);
		JButton save = new JButton(saveDocumentAction);
		JButton close = new JButton(closeDocumentAction);
		
		toolBar.add(open);
		toolBar.add(save);
		toolBar.add(close);
		toolBar.addSeparator();
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");//primjeni za lokaliziranost
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu("Edit");//primjeni za lokaliziranost
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(copyDocumentAction));
		editMenu.add(new JMenuItem(cutDocumentAction));
		editMenu.add(new JMenuItem(pasteDocumentAction));
		editMenu.add(new JMenuItem(infoDocumentAction));
		
		JMenu languageMenu = new JMenu("Languages");
		menuBar.add(languageMenu);
		
		JMenu toolsMenu = new JMenu("Tools");
		
		JMenu changeCaseMenu = new JMenu("Change case");
		
		changeCaseMenu.add(new JMenuItem(invertDocumentAction));
		changeCaseMenu.add(new JMenuItem(uppercaseDocumentAction));
		changeCaseMenu.add(new JMenuItem(lowercaseDocumentAction));
		
		toolsMenu.add(changeCaseMenu);
		menuBar.add(toolsMenu);
		
		setJMenuBar(menuBar);
	}
	
	private Action newDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			tabPane.createNewDocument();
			setTitle("(unnamed) - JNotepad++");
		}
	};

	private Action openDocumentAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Datoteka " + fileName.getAbsolutePath() + " ne postoji!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				byte[] okteti = Files.readAllBytes(filePath);
				tabPane.loadDocument(filePath);
				setTitle(filePath.toString() + " - JNotepad++");
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Pogreška prilikom èitanja datoteke " + fileName.getAbsolutePath() + ".", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	
	private Action saveDocumentAction = new AbstractAction() {//POPRAVI
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			Path openedFilePath = tabPane.getCurrentDocument().getFilePath();
			if(openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepadPP.this, 
							"Ništa nije snimljeno.", 
							"Upozorenje", 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			tabPane.saveDocument(tabPane.getCurrentDocument(), openedFilePath);
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action saveAsDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						"Ništa nije snimljeno.", 
						"Upozorenje", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path openedFilePath = jfc.getSelectedFile().toPath();
			tabPane.saveDocument(tabPane.getCurrentDocument(), openedFilePath);
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Datoteka je snimljena.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action closeDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tabPane.getCurrentDocument().isModified()) {
				int result = JOptionPane.showConfirmDialog(null,"Do you want to save?", "Save option",
			               JOptionPane.YES_NO_OPTION,
			               JOptionPane.QUESTION_MESSAGE);
			            if(result == JOptionPane.YES_OPTION){
			            	saveDocumentAction.actionPerformed(e);
			            }
			            tabPane.closeDocument(tabPane.getCurrentDocument());
			}else {
				tabPane.closeDocument(tabPane.getCurrentDocument());
			}
		}
	};
	
	private Action infoDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel trenutni = tabPane.getCurrentDocument();
			JTextArea text = trenutni.getTextComponent();
			String sveText = text.getText();
			int brojZnakova = sveText.length();
			String[] lines = sveText.split("\r\n|\r|\n");
			int brojLinija = lines.length;
			int brojNepraznih = sveText.replaceAll("\\s+", "").length();
			String informacija = "Your document has " 
					+ brojZnakova + " characters, " 
					+ brojNepraznih + " non-blank characters and " + brojLinija + " lines."; 
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					informacija, 
					"Statistical info", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action copyDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(len != 0) {
				offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					ccpText = doc.getText(offset, len);
				}catch(BadLocationException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}else {
				//TODO
				//onemoguæi tipku
			}
		}
	};
	
	private Action cutDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(len != 0) {
				offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					ccpText = doc.getText(offset, len);
					doc.remove(offset, len);
					tabPane.getCurrentDocument().setModified(true);
				}catch(BadLocationException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}else {
				//TODO
				//onemoguæi tipku
			}
		}
	};
	
	private Action pasteDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			try {
				if(len != 0) {
					offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
					doc.remove(offset, len);
					doc.insertString(offset, ccpText, null);
				}else {
					doc.insertString(textArea.getCaret().getDot(), ccpText, null);
				}
			}catch(BadLocationException ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
			tabPane.getCurrentDocument().setModified(true);
		}
	};
	
	private Action invertDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(len != 0) {
				offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					String text = doc.getText(offset, len);
					text = changeCase(text);
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
					tabPane.getCurrentDocument().setModified(true);
				}catch(BadLocationException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}else {
				//TODO
				//onemoguæi tipku
			}
		}
		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				}else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	private Action uppercaseDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(len != 0) {
				offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					String text = doc.getText(offset, len);
					text = text.toUpperCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
					tabPane.getCurrentDocument().setModified(true);
				}catch(BadLocationException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}else {
				//TODO
				//onemoguæi tipku
			}
		}
	};
	
	private Action lowercaseDocumentAction = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea textArea = tabPane.getCurrentDocument().getTextComponent();
			Document doc = textArea.getDocument();
			int offset = 0, len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
			if(len != 0) {
				offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
				try {
					String text = doc.getText(offset, len);
					text = text.toLowerCase();
					doc.remove(offset, len);
					doc.insertString(offset, text, null);
					tabPane.getCurrentDocument().setModified(true);
				}catch(BadLocationException ex) {
					System.out.println(ex.getMessage());
					ex.printStackTrace();
				}
			}else {
				//TODO
				//onemoguæi tipku
			}
		}
	};
	
	private Action exitAction = new AbstractAction() {
		
		private static final long serialVersionUID = 1L;
		
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JNotepadPP().setVisible(true);
			}
		});
	}
}