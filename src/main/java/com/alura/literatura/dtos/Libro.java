package com.alura.literatura.dtos;

import java.util.List;

public record Libro(
        String title,
        List<Autor> authors,
        List<String> languages,
        int downloadCount
) {
}
