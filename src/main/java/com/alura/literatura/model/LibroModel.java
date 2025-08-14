package com.alura.literatura.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "libros")
public class LibroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String titulo;

    @Column
    private String idioma;

    @Column
    private Integer descargas;

    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AutorModel> autores;

    @Override
    public String toString() {

        String autorUnique = "";

        if (autores.size() == 1){
            autorUnique = autores.get(0).getNombre_autor();
        } else {
            autorUnique = autores.stream().map(AutorModel::getNombre_autor).collect(Collectors.joining(", "));
        }


        return "\n========================================\n" +
                "TITULO: '" + titulo + "'\n" +
                "AUTOR: '" + autorUnique + "'\n" +
                "IDIOMA: '" + idioma + "'\n" +
                "NUMERO DE DESCARGAS: " + descargas + "\n" +
                "========================================\n";
    }
}
