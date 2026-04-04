package Projeto.Engenharia.Engenharia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Projeto.Engenharia.Engenharia.Service.FilesStorageService;
import jakarta.annotation.Resource;

@SpringBootApplication
public class EngenhariaApplication implements CommandLineRunner {
	  @Resource
	  FilesStorageService storageService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(EngenhariaApplication.class, args);
	}

	 @Override
	  public void run(String... arg) throws Exception {
//	    storageService.deleteAll();
	    storageService.init();
	  }
}
