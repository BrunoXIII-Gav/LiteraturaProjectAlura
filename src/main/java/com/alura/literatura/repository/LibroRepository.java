package com.alura.literatura.repository;

import com.alura.literatura.model.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<LibroModel,Integer> {

    @Query("SELECT DISTINCT l FROM LibroModel l LEFT JOIN FETCH l.autores")
    List<LibroModel> findAllWithAutores();

    @Query("SELECT l FROM LibroModel l LEFT JOIN FETCH l.autores a WHERE UPPER(l.titulo) = UPPER(:titulo)")
    Optional<LibroModel> findByTituloIgnoreCaseWithAutores(@Param("titulo") String titulo);

    @Query("SELECT a.nombre_autor, l.titulo FROM AutorModel a LEFT JOIN a.libro l ORDER BY a.nombre_autor")
    List<Object[]> findLibrosPorAutor();

    @Query("""
            SELECT a.nombre_autor, a.fecha_nacimiento, a.fecha_fallecimiento, l.titulo FROM AutorModel a LEFT JOIN a.libro l\s
            WHERE YEAR(a.fecha_nacimiento) <= :aniooIngresado\s\s
            AND (a.fecha_fallecimiento IS NULL OR CAST(a.fecha_fallecimiento AS int) >= :aniooIngresado)
            ORDER BY a.nombre_autor""")
    List<Object[]> findAutorVivoPorAnio(@Param("aniooIngresado") int aniooIngresado);

    @Query("""
            SELECT l FROM LibroModel l LEFT JOIN FETCH l.autores a WHERE l.idioma = :idioma ORDER BY l.titulo
            """)
    List<LibroModel> findLibroPorIdioma(@Param("idioma") String idioma);
}
