import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class Marshaller {

	//Variables
		private Document dom = null;
		private ArrayList<Libro> libros = null;
		
		public Marshaller(ArrayList<Libro> l) {
			libros = l;
		}
		
		public void crearDocumento() {
			//Creamos fabrica
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					try {
						//Creamos DocumentBuilder
						DocumentBuilder db = dbf.newDocumentBuilder();
						
						//Creamos instancia DOM
						dom = db.newDocument();
					}catch (ParserConfigurationException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
		}
		
		public void crearArbolDOM() {
			//Creamos elemento Raiz "Biblioteca"
			Element docEle = dom.createElement("biblioteca");
			dom.appendChild(docEle);
			
			//Recorremos elementos
			Iterator it = libros.iterator();
			while (it.hasNext()) {
				Libro l = (Libro) it.next();
				//Para cada objeto libro, creamos el <libro> y lo añadimos al DOM
				Element libroEle = setLibro(l);
				docEle.appendChild(libroEle);
			}
		}
		
		private Element setLibro(Libro l) {
			//Creamos elemento libro
			Element libroEle = dom.createElement("libro");
			
			//Creamos elemento Titulo 
			Element tituloEle = dom.createElement("titulo");
			Text tituloTexto = dom.createTextNode(l.getTitulo());
			tituloEle.appendChild(tituloTexto);
			tituloEle.setAttribute("anoPublicacion", Integer.toString(l.getAno_publicacion()));
			libroEle.appendChild(tituloEle);
					
			//Creamos elemento Autores
			ArrayList <Autor> autores = l.getAutores();
			Iterator it = autores.iterator();
			Element eleAutores = dom.createElement("autor");
			for(int i=0; i<autores.size(); i++) {
				Element eleNomAutor = dom.createElement("nombre");
				eleAutores.appendChild(eleNomAutor);
			}
			libroEle.appendChild(eleAutores);
			
			
			//Creamos elemento Paginas
			Element paginasEle = dom.createElement("editor");
			Text paginasTexto = dom.createTextNode(Integer.toString(l.getPaginas()));
			paginasEle.appendChild(paginasTexto);
			libroEle.appendChild(paginasEle);
					
			return libroEle;
		}
		
		public void escribirDocumentAXML(File archivo) throws TransformerException{
			//Creamos instancia para escribir el resultado
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			
			//Especificamos donde escribimos y la fuente de datos
			StreamResult result = new StreamResult(archivo);
			DOMSource source = new DOMSource(dom);
			trans.transform(source, result);
		}
}
