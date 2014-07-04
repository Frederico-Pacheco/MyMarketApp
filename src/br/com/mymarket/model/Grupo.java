package br.com.mymarket.model;

import java.io.Serializable;

public class Grupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3100975780005996795L;

	
	private String nome;
	
	public Grupo(String nome) {
		this.nome=nome;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
