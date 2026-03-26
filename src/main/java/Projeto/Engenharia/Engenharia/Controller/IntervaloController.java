package Projeto.Engenharia.Engenharia.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Projeto.Engenharia.Engenharia.Entity.Intervalo;
import Projeto.Engenharia.Engenharia.Repository.IntervaloRepository;
import Projeto.Engenharia.Engenharia.Service.IntervaloService;

@RestController
@RequestMapping("/intervalos")
public class IntervaloController {
	
	private final IntervaloService service;
	
	public IntervaloController(IntervaloService service) {
		this.service = service;
		
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<String> salvarIntervalo(@RequestBody Intervalo i) {
		try {
			String mensagem = this.service.save(i);
			return ResponseEntity.ok(mensagem);
		}catch(Exception e) {
			   return ResponseEntity.badRequest().body("Erro ao salvar intervalo: " + e.getMessage());
		}
		
	}
	@GetMapping("/trazerTodos")
	public ResponseEntity<List<Intervalo>> trazerTodos(){
		List<Intervalo> lista = this.service.getAll();
		return ResponseEntity.ok(lista);
	}
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<String> deletarIntervalo(@PathVariable Long id) {
		try {
			String mensagem = this.service.deletar(id);
			return ResponseEntity.ok(mensagem);
		}catch(Exception e) {
			return new ResponseEntity<String>("Erro ao deletar", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Intervalo> buscarIntervalo(@PathVariable Long id) {	
		try {
			Intervalo intervalo = this.service.get(id);
			return ResponseEntity.ok(intervalo);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	@PutMapping("/{id}")
	public ResponseEntity<String> atualizarIntervalo(@PathVariable Long id, @RequestBody Intervalo intervalo) {
		try {
			String mensagem = this.service.atualizarIntervalo(id, intervalo);
			return ResponseEntity.ok(mensagem);
			
		}catch(Exception e) {
			   return ResponseEntity.badRequest().body("Erro ao atualizar intervalo: " + e.getMessage());

		}
				
	}
	
	
	
	
	

}
