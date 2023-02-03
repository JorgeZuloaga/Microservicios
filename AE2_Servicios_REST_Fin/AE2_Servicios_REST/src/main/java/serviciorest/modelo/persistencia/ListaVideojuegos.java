package serviciorest.modelo.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import serviciorest.modelo.entidad.Videojuego;

/**
 * Patron DAO (Data Access Object), objeto que se encarga de hacer las consultas
 * a algun motor de persistencia (BBDD, Ficheros, etc). En este caso vamos a 
 * simular que los datos estan guardados en una BBDD trabajando con una lista
 * de objetos cargada en memoria para simplificar el ejemplo.
 * 
 * Hay que tener en cuenta que para simplificar el ejemplo tambien se ha hecho
 * que el ID con el que se dan de alta las personas en la lista coincide exactamente
 * con la posicion del array que ocupan.
 * 
 * Mediante la anotacion @Component, damos de alta un unico objeto de esta clase
 * dentro del contexto de Spring, su ID sera el nombre de la case en notacion
 * lowerCamelCase
 * 
 */

@Component
public class ListaVideojuegos {
	
	static List<Videojuego> listaVideojuegos; //Lista para almacenar los videojuegos
	
	static {
		listaVideojuegos = new ArrayList<Videojuego>();
		
		//Se crean 5 juegos iniciales:		
		Videojuego j1= new Videojuego(1, "WoW", "Blizzard", 8);
		Videojuego j2= new Videojuego(2, "Diablo3", "Blizzard", 8);
		Videojuego j3= new Videojuego(3, "God of War", "SCE Santa Monica Studio", 9);
		Videojuego j4= new Videojuego(4, "Cyberpunk", "CD Projekt", 7);
		Videojuego j5= new Videojuego(5, "Assasin Creed Valhalla", "Ubisoft", 8);
		
		//Se añaden los videojuegos al listado
		listaVideojuegos.add(j1);
		listaVideojuegos.add(j2);
		listaVideojuegos.add(j3);
		listaVideojuegos.add(j4);
		listaVideojuegos.add(j5);
		
	}
	
	//Requerimiento 1: crear métodos para:
		
	/**
	 * Método para dar de alta un videojuego.
	 * @param j - el videojuego que recibe para dar de alta
	 * @return String que indica si se ha añadido el juego o no.
	 * Para cumplir el requisito 2, antes de añadir el juego se comprueba
	 * si el id o el nombre están repetidos. En caso de estar repetidos, no se añade
	 * al listado y se envía un mensaje indicándolo
	 */
	public String altaVideojuego(Videojuego j) {
		//Requerimiento 2: comprobar que no se repita ni el id ni el nombre
		for (Videojuego v: listaVideojuegos) {
			//Recorro el listado de juegos
			if (v.getId()==j.getId()) { //Compruebo si existe el id
				return "El id introducido ya existe"; //Como existe finalizo el programa devolviendo
													  // un string que lo indica
			}
			if (v.getNombre().equals(j.getNombre())) { //compruebo si existe el nombre
				return "El nombre introducido ya existe";//Como existe finalizo el programa devolviendo
				  										 // un string que lo indica
			}
		}
		//En caso de que no se repita, lo añado al listado
		listaVideojuegos.add(j);
		return "Videojuego añadido";
		
	}
	
	
	/**
	 * Método para dar de baja un videojuego del listado
	 * indicando su id
	 * @param id del videojuego que se desea eliminar
	 * @return el videojuego eliminado. En caso de que el id no exista,
	 * se retorna null y no se elimina ningún videojuego
	 */
	public Videojuego bajaVideojuego(int id) {
		//Compruebo que el id se encuentra en mi listado
		for (Videojuego v : listaVideojuegos) {
			if (v.getId() == id) {
				//El id existe, elimino el videojuego
				//Como el id no tiene por qué coincidir con la posición
				//que tiene el objeto en el arraylist, se busca el id
				//y una vez encontrado el videojuego, se borra mediante
				//su index empleando indexOf(v):
				listaVideojuegos.remove(listaVideojuegos.indexOf(v));
				return v;
			}
		}
		
		return null;
		
	}
	
	/** Método para modificar un videojuego del listado
	 * mediante búsqueda por ID
	 * @param j se introducen los datos del videojuego a modificar
	 * @return los nuevos datos del videojuego modificado o null
	 * en caso de que no se encuentre el ID en la lista
	 */
	public Videojuego modificarVideojuego(Videojuego j) {
		for(Videojuego v: listaVideojuegos) {
			if(v.getId()==j.getId()) { //Si el id existe:
				//actualizo los datos
				v.setNombre(j.getNombre());
				v.setCompania(j.getCompania());
				v.setNota(j.getNota());
				return v; //Devuelve el videojuego modificado
			}
		}
		return null; //No ha encontrado el videojuego a cambiar
	}
	
	
	/**
	 * Método para mostrar un videojuego indicando su ID
	 * @param id del juego a mostrar
	 * @return el videojuego en caso de que exista el ID en la lista
	 * o null en caso de que no exista
	 */
	public Videojuego mostrarVideojuego(int id) {
		for(Videojuego v: listaVideojuegos) {
			//Se busca el ID
			if(v.getId()==id) { //Si el id está:
				return v; //Devuelve el videojuego
			}	
		}
		return null; //El videojuego no se ha encontrado
		
	}
	
	
	/**
	 * Método que devuelve un listado con todos los videojuegos
	 * @return
	 */
	public List<Videojuego> listarVideojuegos(){
		return listaVideojuegos;
	}
	
	


}
