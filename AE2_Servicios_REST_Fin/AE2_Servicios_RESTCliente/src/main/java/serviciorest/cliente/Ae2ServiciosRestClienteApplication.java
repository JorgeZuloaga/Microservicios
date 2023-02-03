package serviciorest.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import serviciorest.cliente.entidad.Videojuego;
import serviciorest.cliente.servicio.ServicioProxyVideojuego;

@SpringBootApplication
public class Ae2ServiciosRestClienteApplication implements CommandLineRunner {
	
	//Primero inyectaremos todos los objetos que necesitamos para
	//acceder a nuestro ServicioRest, el ServicioProxyPersona y el
	//ServicioProxyMensaje
	@Autowired
	private ServicioProxyVideojuego spv;
	
	//También necesitaremos acceder al contexto de Spring para parar
	//la aplicación, ya que esta app al ser una aplicación web se
	//lanzará en un Tomcat. De esta manera le decimos a Spring que
	//nos inyecte su propio contexto.
	@Autowired
	private ApplicationContext context;
	
	//En este método daremos de alta un objeto de tipo RestTemplate que será
	//el objeto más importante de esta aplicación. Será usado por los 
	//objetos ServicioProxy para hacer las peticiones HTTP a nuestro
	//servicio REST. 
	//Como no podemos anotar la clase RestTemplate para dar un objeto
	//de este tipo, porque no la hemos creado nosotros, usaremos la anotación 
	//@Bean para decirle a Spring que cuando arranque la app ejecute este 
	//método y meta el objeto devuelto dentro del contexto de Spring con ID 
	//"restTemplate" (el nombre del método)
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		System.out.println("Cliente -> Cargando el contexto de Spring");
		SpringApplication.run(Ae2ServiciosRestClienteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Mientras el cliente esté activo, se mostrará el menú por pantalla:
		boolean continuar=true;
		Scanner sc = new Scanner(System.in);
		String opcionMenu="";
		int id;
		String nombre, compania;
		double nota;
		while(continuar) { //Mientras que no se seleccione salir:
			//Mostrar menú
			System.out.println("     M E N Ú     ");
			System.out.println("   Selecciona una opción:");
			System.out.println(" 1. Dar de alta un videojuego");
			System.out.println(" 2. Dar de baja un videojuego por ID");
			System.out.println(" 3. Modificar un videojuego por ID");
			System.out.println(" 4. Obtener un videojuego por ID");
			System.out.println(" 5. Listar videojuegos");
			System.out.println(" 6. Salir");
			
			//Almacenamos en opcionMenu la opción seleccionada por el usuario
			opcionMenu = sc.nextLine();
			
			//Se crea un switch-case con los distintas opciones del menú
			//En la variable contenido, se almacenan los datos introducidos
			//por el cliente
			switch(opcionMenu) {
			case "1":
				//Dar de alta, se piden todos los datos
				System.out.println(" Introduzca los datos del nuevo videojuego:");
				System.out.println(" Introduzca el ID:");
				id = Integer.parseInt(sc.nextLine());
				System.out.println(" Introduzca el nombre:");
				nombre = sc.nextLine();
				System.out.println(" Introduzca la compañía:");
				compania = sc.nextLine();
				System.out.println(" Introduzca la nota:");
				nota = Double.parseDouble(sc.nextLine());
				//se crea un nuevo objeto de tipo Videojuego con todos los atributos
				Videojuego nuevo = new Videojuego(id, nombre, compania, nota);
				//Se da de alta el nuevo videojuego en el listado
				spv.alta(nuevo);
				break;			
			case "2":
				//Dar de baja, se pide el id a borrar
				System.out.println(" Introduzca el ID del videojuego a borrar:");
				id = Integer.parseInt(sc.nextLine());
				spv.baja(id);
				break;
			case "3":
				//Modificar pidiendo el id:
				System.out.println(" Introduzca el ID:");
				id = Integer.parseInt(sc.nextLine());
				//Se piden los demás datos:
				System.out.println(" Introduzca los datos del videojuego a modificar:");
				System.out.println(" Introduzca el nombre:");
				nombre = sc.nextLine();
				System.out.println(" Introduzca la compañía:");
				compania = sc.nextLine();
				System.out.println(" Introduzca la nota:");
				nota = Double.parseDouble(sc.nextLine());
				//Se crea un objeto de tipo Videojuego con todos los datos nuevos
				Videojuego modificado = new Videojuego(id, nombre, compania, nota);
				//Se modifica dicho videojuego
				spv.modificar(modificado);
				break;
			case "4":
				//Obtener videojuego por id:
				System.out.println(" Introduzca el ID del videojuego a mostrar:");
				id = Integer.parseInt(sc.nextLine());
				Videojuego j = spv.obtener(id);
				//Se muestra el videojuego obtenido
				System.out.println("El videojuego es: "+ j.toString());
				break;
			case "5":
				//Listar todos los videojuegos
				List<Videojuego> listado= new ArrayList<Videojuego>();
				listado=spv.listar();
				System.out.println(" El listado de los videojuegos es:");
				//Se muestran todos los videojuegos recorriendo el listado
				//por un for, invocando al método toString en cada uno de ellos
				for (Videojuego v: listado) {
					System.out.println(v.toString());
				}
				break;
			case "6":
				//Salir
				continuar=false; //Se sale del bucle
				break;
			default:
				//Cualquier opción distinta a las anteriores, enviará
				//un mensaje de opción no contemplada
				System.out.println("Opción no contemplada, inténtalo de nuevo");
				
			}
		}
		pararAplicacion(); //Si se sale del bucle, se para la aplicación
		
	}
	
	public void pararAplicacion() {
		//Esta aplicacion levanta un servidor web, por lo que tenemos que dar 
		//la orden de pararlo cuando acabemos. Para ello usamos el método exit, 
		//de la clase SpringApplication, que necesita tanto el contexto de 
		//Spring como un objeto que implemente la interfaz ExitCodeGenerator. 
		//Podemos usar la función lambda "() -> 0" para simplificar 
		
		SpringApplication.exit(context, () -> 0);
		
		//Podemos hacerlo también de una manera clásica, es decir, creando
		//la clase anónima a partir de la interfaz. 
		//El código de abajo sería equivalente al de arriba
		//(pero mucho más largo)
		/*
		SpringApplication.exit(context, new ExitCodeGenerator() {
			
			@Override
			public int getExitCode() {
				return 0;
			}
		});*/
	}

}
