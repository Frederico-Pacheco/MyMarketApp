package br.com.mymarket.model;

import java.io.Serializable;

public class Pessoa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534579135061476596L;

	private String nome;
	
	private String urlImage;
	
	private String number;

	public Pessoa() {
		super();
	}	
	
	public Pessoa(String nome, String number) {
		this.nome = nome;
		this.number = number;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return  nome + " - " + number;
	}
}
