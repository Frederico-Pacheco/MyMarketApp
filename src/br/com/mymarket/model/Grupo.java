package br.com.mymarket.model;

import java.io.Serializable;
import java.util.List;

public class Grupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3100975780005996795L;

	private String nome;
	
	private List<Pessoa> integrantes;

	public Grupo() {
		super();
	}
	
	public Grupo(String nome) {
		this.nome=nome;
	}


	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setIntegrantes(List<Pessoa> integrantes) {
		this.integrantes = integrantes;
	}
	
	public List<Pessoa> getIntegrantes() {
		return integrantes;
	}
	
}
