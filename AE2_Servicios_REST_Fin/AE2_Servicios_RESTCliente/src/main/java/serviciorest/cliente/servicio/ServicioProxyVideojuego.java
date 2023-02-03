package serviciorest.cliente.servicio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;

//Con esta anotación damos de alta un objeto de tipo
//ServicioProxyVideojuego dentro del contexto de Spring
@Service
public class ServicioProxyVideojuego {
	
	//La URL base del servicio REST de videojuegos
	public static final String URL = "http://localhost:8080/videojuegos/";
	
	//Se inyecta el objeto de tipo RestTemplate
	//que nos ayuda a hacer las peticiones HTTP al servicio REST
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Método para dar de alta un videojuego
	 * @param j es el videojuego que recibe
	 * @return retorna el videojuego dado de alta o null en caso de que no se pueda añadir
	 */
	public Videojuego alta(Videojuego j) {
		try {
			//El servidor trabaja con objetos de tipo ResponseEntity
			//El cliente también lo hará
			ResponseEntity<Videojuego> re = restTemplate.postForEntity(URL,j,Videojuego.class);
			System.out.println("Alta -> Código de respuesta " + re.getStatusCode());
			//Al ser el objeto ResponseEntity de tipo Videojuego, al obtener el 
			//body me lo convierte automaticamente a tipo Videojuego
			//(Spring utiliza librerías por debajo para pasar de JSON a objeto)
			return re.getBody();
		}
		catch (HttpClientErrorException e) {
			System.out.println("alta -> El videojuego NO se ha dado de alta, id: " + j.getId());
		    System.out.println("alta -> Código de respuesta: " + e.getStatusCode());
		    return null;
		}
	}

	/**
	 * Método para dar de baja un videojuego
	 * @param id del videojuego
	 * @return true o false en función de si lo puede borrar o no
	 */
	public boolean baja(int id) {
		try {
			//El método delete no devuelve nada, en caso de que no pueda
			//borrarlo saltará la excepción
			restTemplate.delete(URL + id);
			System.out.println("Baja -> Videojuego borrado");
			return true;
		}
		catch(HttpClientErrorException e) {
			System.out.println("Baja -> El videojuego NO se ha borrado, id: " + id);
		    System.out.println("Baja -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
		
	}
	
	
	/**
	 * Método para modificar un videojuego existente por ID
	 * @param j videojuego con todos los datos
	 * @return true o false en función de si lo puede modificar
	 * porque el videojuego existe por su id o no
	 */
	public boolean modificar(Videojuego j) {
		try {
			restTemplate.put(URL + j.getId(), j, Videojuego.class);
			System.out.println("Modificar -> Videojuego modificado");
			return true;
		}
		catch(HttpClientErrorException e){
			System.out.println("Modificar -> El videojuego NO se ha modificado, id: " + j.getId());
		    System.out.println("Modificar -> Codigo de respuesta: " + e.getStatusCode());
		    return false;
		}
	}
	
	
	/**
	 * Método que muestra un videojuego indicando su id
	 * @param id del videojuego a mostrar
	 * @return el videojuego si el id está en la lista, o null si no existe
	 */
	public Videojuego obtener(int id) {
		try {
			//Como el servicio trabaja con objetos ResponseEntity, nosotros 
			//tambien podemos hacerlo en el cliente
			//Ej http://localhost:8080/videojuegos/1 GET
			ResponseEntity<Videojuego> re = restTemplate.getForEntity(URL + id, Videojuego.class);
			System.out.println("Obtener -> Código de respuesta " + re.getStatusCode());
			return re.getBody();
		}
		catch (HttpClientErrorException e) {
			System.out.println("Obtener -> El videojuego NO se ha encontrado, id: " + id);
		    System.out.println("Obtener -> Código de respuesta: " + e.getStatusCode());
		    return null;
		}
		
	}
	
	
	/**
	 * Método que retorna un listado con todos los videojuegos
	 * @return el listado completo o null en caso de que haya algún error
	 * que haga que el servidor no funcione.
	 */
	public List<Videojuego> listar(){
		try {
			String queryParams = "";
			ResponseEntity<Videojuego[]> response =
					  restTemplate.getForEntity(URL + queryParams,Videojuego[].class);
			Videojuego[] arrayPersonas = response.getBody();
			return Arrays.asList(arrayPersonas);//convertimos el array en un ArrayList
		} catch (HttpClientErrorException e) {
			System.out.println("listar -> Error al obtener la lista");
		    System.out.println("listar -> Codigo de respuesta: " + e.getStatusCode());
		    return null;
		}
		
	}
	

}
