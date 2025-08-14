package com.alura.literatura.external;

import com.alura.literatura.dtos.Libro;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsultaLibros {

    private static final String api = "https://gutendex.com/books/";  // api predeterminada
    private static final HttpClient httpclient = HttpClient.newHttpClient();
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static Libro obtenerDatosLibrosPorNombre(String nombreIngreso){

        String codificacion = URLEncoder.encode(nombreIngreso, StandardCharsets.UTF_8).replace("+","%20");
        String url_base = api+"?search="+ codificacion;

        try {

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url_base)).GET().build();

            HttpResponse<String> response = httpclient.send(request,HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){
                ExtractLibrosRecord data = gson.fromJson(response.body(), ExtractLibrosRecord.class);
                return data.results().get(0);
            } else {
                System.out.printf("Erro al obtener los datos del libro %d%n", response.statusCode());
            }

        }catch (Exception e){
            System.out.println("Error en la conexion"+ e.getMessage());
        }
        return null;
    }

}
