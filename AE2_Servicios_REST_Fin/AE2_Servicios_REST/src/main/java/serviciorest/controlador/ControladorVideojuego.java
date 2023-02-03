package serviciorest.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import serviciorest.modelo.entidad.Videojuego;
import serviciorest.modelo.persistencia.ListaVideojuegos;
@RestController
public class ControladorVideojuego {
	
	@Autowired
	ListaVideojuegos listaVideojuegos;
	
	//Dar de alta un videojuego

	@PostMapping (path="videojuegos",consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> altaVideojuego(@RequestBody Videojuego j){
		System.out.println("altaVideojuego: objeto videojuego: " + j);
		String mensaje = listaVideojuegos.altaVideojuego(j);
		//Comprobamos el mensaje que nos devuelve al intentar añadir un juego
		if (mensaje.equals("Videojuego añadido")){
			System.out.println("El videojuego se ha añadido");
			return new ResponseEntity<Videojuego>(j, HttpStatus.CREATED);
		}
		//En caso de que el id o el nombre del juego exista (Req.2) no se añade
		else if(mensaje.equals("El id introducido ya existe")){
			System.out.println("El videojuego no se ha podido añadir, el id ya existe");
			return new ResponseEntity<Videojuego>(j,HttpStatus.FORBIDDEN);			
		}
		else {
			System.out.println("El videojuego no se ha podido añadir, el nombre ya existe");
			return new ResponseEntity<Videojuego>(j,HttpStatus.FORBIDDEN);	
		}
		
	}
	//Dar de baja un videojuego:
	@DeleteMapping (path="videojuegos/{id}")
	public ResponseEntity<Videojuego> bajaVideojuego(@PathVariable("id") int id){
		System.out.println("ID a borrar: " + id);
		Videojuego j = listaVideojuegos.bajaVideojuego(id);
		if (j !=null) { //Se ha podido borrar el videojuego porque existe el id:
			return new ResponseEntity<Videojuego>(j, HttpStatus.OK);
		}
		else
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
	}
	//Modificar un videojuego:
	@PutMapping(path="videojuegos/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> modificarVideojuego(@PathVariable("id") int id,
			@RequestBody Videojuego j){
		System.out.println("ID a modificar: " + id);
		System.out.println("Datos a modificar: " + j);
		Videojuego jUpdate = listaVideojuegos.modificarVideojuego(j);
		if (jUpdate!=null) { //Si se ha podido modificar el juego porque su id existe:
			return new ResponseEntity<Videojuego> (HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
		}
	}
	//Mostrar un videojuego introduciendo el ID
	@GetMapping(path="videojuegos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Videojuego> mostrarVideojuego(@PathVariable("id") int id){
		System.out.println("Buscando videojuego con id: " + id);
		Videojuego j = listaVideojuegos.mostrarVideojuego(id);
		if (j!=null) { //Existe el videojuego:
			return new ResponseEntity<Videojuego>(j,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Videojuego>(HttpStatus.NOT_FOUND);
		}
		
	}
	//Listar videojuegos:
	@GetMapping(path="videojuegos", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Videojuego>> listarVideojuegos(){
		List<Videojuego> listado = null;
		System.out.println("Listando todos los videojuegos:");
		listado=listaVideojuegos.listarVideojuegos();
		return new ResponseEntity<List<Videojuego>>(listado,HttpStatus.OK);
	}

}
