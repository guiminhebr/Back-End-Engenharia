package Projeto.Engenharia.Engenharia.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Intervalo {

    @Id @GeneratedValue
    private Long id;
    private String tipoIntervalo;

    public String getTipoIntervalo() {
		return tipoIntervalo;
	}

	public void setTipoIntervalo(String tipoIntervalo) {
		this.tipoIntervalo = tipoIntervalo;
	}

	private String status;
    private String numeroOs;

    private String trecho;
    private Double kmInicial;
    private Double kmFinal;

    private LocalDate data;
    private LocalTime inicio;
    private LocalTime fim;

    private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNumeroOs() {
		return numeroOs;
	}

	public void setNumeroOs(String numeroOs) {
		this.numeroOs = numeroOs;
	}

	public String getTrecho() {
		return trecho;
	}

	public void setTrecho(String trecho) {
		this.trecho = trecho;
	}

	public Double getKmInicial() {
		return kmInicial;
	}

	public void setKmInicial(Double kmInicial) {
		this.kmInicial = kmInicial;
	}

	public Double getKmFinal() {
		return kmFinal;
	}

	public void setKmFinal(Double kmFinal) {
		this.kmFinal = kmFinal;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalTime inicio) {
		this.inicio = inicio;
	}

	public LocalTime getFim() {
		return fim;
	}

	public void setFim(LocalTime fim) {
		this.fim = fim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	  

}
