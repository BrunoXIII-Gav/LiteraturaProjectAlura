package com.alura.literatura.menu;

import com.alura.literatura.external.ConsultaLibros;
import com.alura.literatura.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class Menu {

    private LibrosService librosService;

    Scanner scanner = new Scanner(System.in);

    @Autowired
    public Menu(LibrosService librosService) {
        this.librosService = librosService;
    }

    public void MenuMain(){


        boolean loop = true;

        while (loop) {

            var menu = """
                    1 - Buscar libro por titulo
                    
                    2 - Listar libros registrados
                    
                    3 - Listar autores registrados
                    
                    4 - Listar autores vivos en unu determinado año
                    
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;

            System.out.println(menu);

            System.out.println("***************************");
            System.out.println("que desea hacer");
            String opcionTexto = scanner.nextLine().trim();

            int opcion;
            try {
                opcion = Integer.parseInt(opcionTexto);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n Ingresa el libro que deseas buscar ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    librosService.searchAndSaveLibroModel(respuesta);
                    break;
                case 2:
                    librosService.listAllLibros();
                    break;
                case 3:
                    librosService.listarLibrosPorAutor();
                    break;
                case 4:
                    System.out.println("\n Ingrese el año vivo del autor(es) que desea buscar ");
                    int anio = Integer.parseInt(scanner.nextLine());
                    librosService.listarAutoresVivosPorAnio(anio);
                    break;
                case 5:
                    System.out.println("""
                        Ingrese el prefijo del idioma para buscar los libros:
                        
                        es - Español
                        en - Inglés
                        fr - Francés
                        fi - Finlandés
                        """);
                    String respuestaIdioma = scanner.nextLine().trim().toLowerCase();
                    librosService.listarLibroPorIdioma(respuestaIdioma);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    loop = false;
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

}
