import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	private Document dom = null;
	private ArrayList<Libro> libros = null;

	public Parser() {
		libros = new ArrayList<Libro>();
	}

	public void parseFicheroXML(String fichero) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(fichero);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void parseDocument() {

		Element docEle = dom.getDocumentElement();

		NodeList nl = docEle.getElementsByTagName("libro");

		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				Libro l = getLibro(el);
				libros.add(l);
			}
		}
	}

	private Libro getLibro(Element libroEle) {
		String titulo = getTextValue(libroEle, "titulo");
		int año_publicacion = getIntValue(libroEle, "anoPublicacion");
		ArrayList<Autor> autores = getAutores(libroEle);
		int num_paginas = getIntValue(libroEle, "paginas");

		Libro lb = new Libro(titulo, año_publicacion, autores, num_paginas);

		return lb;

	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();

		}
		return textVal;

	}

	private int getIntValue(Element ele, String tagName) {
		return Integer.parseInt(getTextValue(ele, tagName));
	}

	//Obtener autores
		private ArrayList<Autor> getAutores(Element ele){
			ArrayList <Autor> autores = new ArrayList<Autor>();
			
			NodeList nl = ele.getElementsByTagName("autor");
			if (nl != null && nl.getLength() > 0) {
					//Obtenemos elemento Autores
					Element elA = (Element) nl.item(0);
					
					//Inspecciona cada nombre que hay en autores
					NodeList nlA = elA.getElementsByTagName("nombre");
					for (int i=0; i<nlA.getLength(); i++) {
						Element el = (Element) nlA.item(i);
						String au = el.getFirstChild().getNodeValue();
						
						autores.add(new Autor(au));
					}				
			}

			return autores;
		}
	public void print() {

		Iterator it = libros.iterator();

		while (it.hasNext()) {

			Libro l = (Libro) it.next();
			l.imprimir();
		}

	}
	
	public ArrayList<Libro> getLibros(){
		return this.libros;
	}
}