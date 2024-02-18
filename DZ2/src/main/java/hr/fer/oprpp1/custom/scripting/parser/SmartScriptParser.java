package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.LexToken;
import hr.fer.oprpp1.custom.scripting.lexer.LexTokenType;
import hr.fer.oprpp1.custom.scripting.lexer.LexerForParser;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParser {

	private ObjectStack stog;
	
	private LexerForParser lexer;
	
	private int brojVarijabliUForu, brojVarijabliUEcho;

	public SmartScriptParser(String docBody) {
		this.stog = new ObjectStack();
		this.brojVarijabliUForu = 0;
		this.brojVarijabliUEcho = 0;

		try {
			this.lexer = new LexerForParser(docBody);
			LexToken token = lexer.nextToken();
			this.stog.push(new DocumentNode());
			
			if(token.getType() != LexTokenType.EOF) {
				do {
					if(token.getType() == LexTokenType.EOF) {
						break;
					}else {
						try {
							parse(token);
						}catch(Exception e) {
							System.out.println(e.getMessage());
						}
					}
					token = lexer.nextToken();
				}while(token.getType()!= LexTokenType.EOF);
			}
		}catch(SmartScriptParserException sspe) {
			System.out.println("Lexer ili parser je bacio svoju iznimku: " + sspe.getMessage());
			throw new SmartScriptParserException("Nešto je pošlo po krivu pri parsiranju!");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			throw new SmartScriptParserException("Nešto je pošlo po krivu pri parsiranju!");
		}
	}
	
	public void parse(LexToken token) {
		if(token.getType() == LexTokenType.TAGFOR) {//ako je u foru
		    brojVarijabliUForu = 0;
		    LexToken toke = lexer.nextToken();
            if(toke.getType() != LexTokenType.VARIABLA) {
                throw new SmartScriptParserException("Prvi clan FOR nije varijabla!");
            }else {
                ElementVariable var = (ElementVariable) toke.getValue();
                Element[] elementiForPetljeNakonVarijable = new Element[4];
                elementiForPetljeNakonVarijable[0] = var;
                int i = 1;
                this.brojVarijabliUForu++;
                toke = lexer.nextToken();
                do {
                    if((toke.getType() == LexTokenType.VARIABLA 
                            || toke.getType() == LexTokenType.STRINGUTAGU
                            || toke.getType() == LexTokenType.NUMBER) && this.brojVarijabliUForu < 5) {
                        this.brojVarijabliUForu++;
                        elementiForPetljeNakonVarijable[i] = toke.getValue();

                    }else {
                        throw new SmartScriptParserException("Nepravilan broj varijabli u FOR petlji!");
                    }
                    if(lexer.isuTagu()) {
                        break;
                    }
                    i++;
                    toke = lexer.nextToken();
                }while(true);   
                if(this.brojVarijabliUForu > 4 || this.brojVarijabliUForu < 3) {
                    throw new SmartScriptParserException("Nepravilan broj varijabli u FOR petlji!");
                }else {
                    ForLoopNode cvorForPetlje = new ForLoopNode((ElementVariable)elementiForPetljeNakonVarijable[0], elementiForPetljeNakonVarijable[1], elementiForPetljeNakonVarijable[2], elementiForPetljeNakonVarijable[3]);
                    Node zadnjiNaStogu = (Node) this.stog.pop();
                    zadnjiNaStogu.addChildNode(cvorForPetlje);
                    this.stog.push(zadnjiNaStogu);
                    this.stog.push(cvorForPetlje);
                }
            }
		}else if(token.getType() == LexTokenType.TAGECHO) {//ako je u echo
			this.brojVarijabliUEcho = 0;
			Element[] elements = new Element[10];
            LexToken toke = lexer.nextToken();
            do {
                if(this.brojVarijabliUEcho >= elements.length) {
                    Element[] pom = elements;
                    elements = new Element[this.brojVarijabliUEcho * 2];
                    for(int i = 0; i < this.brojVarijabliUEcho; i++) {
                        elements[i] = pom[i];
                    }
                    elements[this.brojVarijabliUEcho] = toke.getValue();
                    this.brojVarijabliUEcho++;
                    
                }else {
                    elements[this.brojVarijabliUEcho] = toke.getValue();
                    this.brojVarijabliUEcho++;
 
                }
                if(lexer.isuTagu()) {
                    break; 
                }
                toke = lexer.nextToken();
            } while(true);
            Element[] elementsFinal = Arrays.copyOf(elements, brojVarijabliUEcho);
            EchoNode cvorEcho = new EchoNode(elementsFinal);
            Node zadnjiNaStogu = (Node) this.stog.pop();
            zadnjiNaStogu.addChildNode(cvorEcho);
            this.stog.push(zadnjiNaStogu);
		}else if(token.getType() == LexTokenType.TEKST) {//ako je tekst
			TextNode cvorZaTekst = new TextNode(token.getValue().asText());
			Node zadnjiNaStogu = (Node) this.stog.pop();
			zadnjiNaStogu.addChildNode(cvorZaTekst);
			this.stog.push(zadnjiNaStogu);
		}else if(token.getType() == LexTokenType.TAGEND) {//ako je end
			this.stog.pop();
			if(this.stog.size() == 0) {
				throw new SmartScriptParserException("Stog je ostao prazan!");
			}
		}
	}
	
	public DocumentNode getDocumentNode() {
		if(this.stog.size() != 1) {
			throw new SmartScriptParserException("Stog je ostao prazan!");
		}
		return (DocumentNode) this.stog.pop();
	}
}