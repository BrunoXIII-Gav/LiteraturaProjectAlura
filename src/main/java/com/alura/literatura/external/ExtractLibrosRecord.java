package com.alura.literatura.external;

import com.alura.literatura.dtos.Libro;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public record ExtractLibrosRecord(
        int count,
        String next,
        String previous,
        List<Libro> results
        ) {
}
