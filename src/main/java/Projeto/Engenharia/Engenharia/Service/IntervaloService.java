package Projeto.Engenharia.Engenharia.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import Projeto.Engenharia.Engenharia.Entity.Intervalo;
import Projeto.Engenharia.Engenharia.Repository.IntervaloRepository;

@Service
public class IntervaloService {
	
	//gravar
	private final IntervaloRepository repo;
	
	public IntervaloService(IntervaloRepository repo) {
		this.repo = repo;
	}
	
	
	public String save(Intervalo i) {
		repo.save(i);
		return "Intervalo cadastrado com sucesso!";
	}
	public String deletar(Long id) {
		repo.deleteById(id);;
		return "Intervalo deletado com sucesso!";
			
	}
	public List<Intervalo> getAll(){
		return repo.findAll();
	}
	
	public Intervalo get(Long id) {
		return repo.findById(id).orElseThrow(() -> new RuntimeException());
	}
	
	
	public String atualizarIntervalo(Long id, Intervalo intervalo) {
		Intervalo i = get(id);
		i.setStatus(intervalo.getStatus());
		i.setNumeroOs(intervalo.getNumeroOs());
		i.setTipoIntervalo(intervalo.getTipoIntervalo());
		i.setTrecho(intervalo.getTrecho());
		i.setKmInicial(intervalo.getKmInicial());
		i.setKmFinal(intervalo.getKmFinal());
		
		this.repo.save(i);
		return "Intervalo Atualizado!";
		
		
	}
	



	
	
	
	


}
