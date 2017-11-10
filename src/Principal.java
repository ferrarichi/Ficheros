import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

	public static ArrayList<Libro> lbs = new ArrayList<>();

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		int opcion = 0;

		while (opcion != 5) {

			System.out.println("1. Cargar libros");
			System.out.println("2. Mostrar libros obtenidos");
			System.out.println("3. Crear libro");
			System.out.println("4. Guardar libros");
			System.out.println("5. Salir");
			
			System.out.println("");

			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				cargarLibros();
				break;
			case 2:
				mostrarLibros();
				break;
			case 3:
				crearLibro();
				break;
			case 4:
				guardarLibros();
				break;

			default:
				System.out.println("-----------------------------------------");
				System.out.println("Has introducido un valor no válido");
			}
		}
		System.out.println("El programa ha finalizado");
	}

	// Obtener libros XML
	public static void cargarLibros() {
		Parser pl = new Parser();
		pl.parseFicheroXML(pedirFichero());
		pl.parseDocument();

		// Añadimos al array que conservamos los nuevos libros cargados
		ArrayList<Libro> lbsTEMP = pl.getLibros();
		for (int i = 0; i < lbsTEMP.size(); i++) {
			lbs.add(lbsTEMP.get(i));
		}

		System.out.println("-----------------------------------------");
		System.out.println("Se han obtenido " + lbsTEMP.size() + " nuevos libros");
		System.out.println("Atualmente hay un total de " + lbs.size() + " libros");
	}


	public static String pedirFichero() {
		Scanner entrada = new Scanner(System.in);
		String fichero = "";
		boolean ok = false;
		while (!ok) {
			System.out.println("Introduce el nombre del fichero XML");
			fichero = entrada.nextLine();

			File archivo = new File(fichero);
			if (!archivo.exists()) {
				System.out.println("El fichero no existe");
			} else {
				ok = true;
			}
		}

		return fichero;
	}

	public static void mostrarLibros() {
		if (lbs != null) {
			System.out.println("-----------------------------------------");
			System.out.println("Mostrando " + lbs.size() + " libros...");
			System.out.println("-----------------------------------------");

			// Imprimimos libros
			System.out.println("-----------------------------------------");
			for (int i = 0; i < lbs.size(); i++) {
				System.out.println("Libro número " + (i + 1));
				System.out.println("-----------------------------------------");
				lbs.get(i).imprimir();
				System.out.println("-----------------------------------------");
			}
		} else {
			System.out.println("-----------------------------------------");
			System.out.println("No se encuentran libros actualmente");
		}

	}

	// Crear un nuevo libro
	public static void crearLibro() {
		Scanner entrada = new Scanner(System.in);
		System.out.println("-----------------------------------------");
		System.out.println("Generando nuevo libro");

		// Variables a ultilizar
		String titulo = "";

		int numAutores, año, paginas = 0;
		ArrayList<Autor> autores = new ArrayList<Autor>();

		// Pedimos datos
		System.out.println("> Introduce el Titulo");
		titulo = entrada.nextLine();
		System.out.println("> Introduce el Año");
		año = entrada.nextInt();
		numAutores = pedirInt("autores");
		System.out.println("> Introduce el número de páginas");
		paginas = entrada.nextInt();

		// Generamos autores del libro
		for (int i = 0; i < numAutores; i++) {
			autores.add(new Autor(pedirAutor(i)));
		}

		// Se crea libro
		Libro nuevo = new Libro(titulo, año, autores, paginas);

		// Se imprime datos
		System.out.println("-----------------------------------------");
		System.out.println("Nuevo libro generado:");
		nuevo.imprimir();
		System.out.println("-----------------------------------------");

		// Se añade al arrayList
		lbs.add(nuevo);
	}
	
	public static String pedirAutor(int i){
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("> Introduce el autor nº " + (i + 1));
		String name = entrada.nextLine();
		
		return name;

	}

	// Guardar libros
	public static void guardarLibros() {
		Scanner entrada = new Scanner(System.in);

		System.out.println("-----------------------------------------");
		System.out.println("Guardando valores");

		Marshaller marshaller = new Marshaller(lbs);

		System.out.println("Entro a crear Documento");
		marshaller.crearDocumento();
		;
		System.out.println("Entro a crear arbol");
		marshaller.crearArbolDOM();

		System.out.println("Introduce el nombre del nuevo fichero");
		String fileTXT = entrada.nextLine();

		File archivo = new File(fileTXT + ".xml");

		try {
			marshaller.escribirDocumentAXML(archivo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al intentar generar el fichero xml");
		}

		System.out.println("XML generado correctamente");
	}

	// Devuelve Int seguro
	public static int pedirInt(String motivo) {
		Scanner entrada = new Scanner(System.in);
		boolean valido = false;
		int valor = 0;

		while (!valido) {
			System.out.println("> Introduce el Número de " + motivo);
			try {
				valor = entrada.nextInt();
			} catch (Exception e) {
				// TODO: handle exception
				valor = 0;
			}

			if (valor > 0) {
				valido = true;
			} else {
				System.out.println("***** No puede tener 0 autores, introduce mas de 0  autores");
			}
		}

		return valor;
	}
}
