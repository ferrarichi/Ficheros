

import java.io.Serializable;
import java.util.ArrayList;

public class Libro implements Serializable {

	
	private String titulo;
	ArrayList<Autor> autores = null;
	private int ano_publicacion;
	private int paginas;

	public Libro(String tit, int año, ArrayList<Autor> autors, int num_pag){
		
		titulo = tit;
		autores = autors;
		ano_publicacion = año;
		paginas = num_pag;
	}
	

	

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ArrayList<Autor> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<Autor> autores) {
		this.autores = autores;
	}




	public int getAno_publicacion() {
		return ano_publicacion;
	}
	public void setAno_publicacion(int ano_publicacion) {
		this.ano_publicacion = ano_publicacion;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

		
		public void imprimir() {
			String datos = "";
			datos += ("Titulo: " + this.titulo + "\n");
			datos += ("Año: " + this.ano_publicacion + "\n");
			for (int i=0; i<autores.size(); i++) {
				datos += ("Autor nº" + (i+1) + ": " + this.autores.get(i).getNombre() + "\n");
			}
			datos += ("Paginas: " + this.paginas + "\n");
			datos += ("----------------------------------------");
			
			System.out.println(datos);
		}
		


	
}
