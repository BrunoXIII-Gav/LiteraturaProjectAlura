package com.alura.literatura;

import com.alura.literatura.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	private final Menu menu;

	@Autowired
    public LiteraturaApplication(Menu menu) {
        this.menu = menu;
    }


    public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.MenuMain();
	}
}
