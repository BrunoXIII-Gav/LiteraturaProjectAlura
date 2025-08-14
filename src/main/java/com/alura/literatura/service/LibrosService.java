package com.alura.literatura.service;

import com.alura.literatura.dtos.Autor;
import com.alura.literatura.dtos.Libro;
import com.alura.literatura.external.ConsultaLibros;
import com.alura.literatura.model.AutorModel;
import com.alura.literatura.model.LibroModel;
import com.alura.literatura.repository.AutorRepository;
import com.alura.literatura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class LibrosService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    @Autowired
    public LibrosService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public void searchAndSaveLibroModel(String nombreLibro){

        if (findByTitulo(nombreLibro) != null){
            System.out.println("No se puede registrar el mismo libro mas de una vez");
            return;
        }

        Libro libroFromApi = ConsultaLibros.obtenerDatosLibrosPorNombre(nombreLibro);
        if (libroFromApi != null){

            LibroModel libroModel = new LibroModel();
            libroModel.setTitulo(libroFromApi.title());
            libroModel.setIdioma(libroFromApi.languages().get(0));
            libroModel.setDescargas(libroFromApi.downloadCount());

            if (libroFromApi.authors() !=null){

                List<AutorModel> autoresModels = new ArrayList<>();
                for (Autor autorApi : libroFromApi.authors()) {
                    final AutorModel autorModel = getAutorModel(autorApi, libroModel);
                    autoresModels.add(autorModel);
                }

                libroModel.setAutores(autoresModels);

                libroRepository.save(libroModel);
            }


        }else {
            System.out.println("Libro no encontrado");
        }

        LibroModel libroGuardado = findByTitulo(nombreLibro);

        if (libroGuardado !=null){
            System.out.println(libroGuardado);
        }
    }

    private static AutorModel getAutorModel(Autor autorApi, LibroModel libroModel) {
        AutorModel autorModel = new AutorModel();
        autorModel.setNombre_autor(autorApi.name());
        autorModel.setFecha_nacimiento(
                autorApi.birthYear() != null ? new Date(autorApi.birthYear() - 1900, Calendar.JANUARY, 1) : null
        );
        autorModel.setFecha_fallecimiento(
                autorApi.deathYear() != null ? String.valueOf(autorApi.deathYear()) : null
        );
        autorModel.setLibro(libroModel); // Relación ManyToOne
        return autorModel;
    }

    public LibroModel findByTitulo(String titulo){
        return libroRepository.findByTituloIgnoreCaseWithAutores(titulo).orElse(null);
    }

    @Transactional
    public void listAllLibros(){
        List<LibroModel> mostrarLibros=libroRepository.findAllWithAutores();

        if (!mostrarLibros.isEmpty()){
            mostrarLibros.forEach(System.out::println);
        }
    }

    @Transactional
    public void listarLibrosPorAutor(){
        List<Object[]> mostrarPorAutor =libroRepository.findLibrosPorAutor();

        String autorActual = "";
        for (Object[] autor : mostrarPorAutor){
            String nombreAutor = (String) autor[0];
            String tituloLibro = (String) autor[1];

            if (!nombreAutor.equals(autorActual)) {
                if (!autorActual.isEmpty()) {
                    System.out.println(); // Línea en blanco entre autores
                }
                System.out.println("📚 AUTOR: " + nombreAutor);
                System.out.println("   Libros:");
                autorActual = nombreAutor;
            }
            if (tituloLibro != null) {
                System.out.println("   - " + tituloLibro);
            }
        }
    }

    @Transactional
    public void listarAutoresVivosPorAnio(int anioIngresado){
        List<Object[]> mostrarAutorVivoPorAnio =libroRepository.findAutorVivoPorAnio(anioIngresado);

        for (Object[] result : mostrarAutorVivoPorAnio){
            String nombreAutor = (String) result[0];
            Date fechaNacimiento = (Date) result[1];
            String fechaFallecimiento = (String) result[2];
            String tituloLibro = (String) result[3];

            System.out.println("📖 Autor: " + nombreAutor);
            System.out.println("   Nacimiento: " + fechaNacimiento);
            System.out.println("   Fallecimiento: " + fechaFallecimiento);
            if (tituloLibro!=null){
                System.out.println("   Libro: " + tituloLibro);
            }
            System.out.println();
        }

        System.out.println();
    }

    @Transactional
    public void listarLibroPorIdioma(String idiomaIngresado){
        List<LibroModel> mostrarLibrosPorIdioma=libroRepository.findLibroPorIdioma(idiomaIngresado);

        System.out.println("Total de libros encontrados: " + mostrarLibrosPorIdioma.size());
        System.out.println();

        mostrarLibrosPorIdioma.forEach(System.out::println);
    }

}
