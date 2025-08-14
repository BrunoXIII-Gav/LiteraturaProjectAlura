package com.alura.literatura.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "autores")
public class AutorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nombre_autor;

    @Column
    private Date fecha_nacimiento;

    @Column
    private String fecha_fallecimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_libro")
    private LibroModel libro;

    @Override
    public String toString() {
        return  ", AUTOR: '" + nombre_autor;
    }
}
