package br.com.mymarket.model;

import java.io.Serializable;

public class Produto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491497910276359249L;

	private String nome;
	
	private Double qtde;
	
	private Double preco;
	
	private boolean comprado;
	
	public Produto(){
		super();
	}
	
	public Produto(String nome, Double qtde, Double preco, boolean comprado) {
		super();
		this.nome = nome;
		this.qtde = qtde;
		this.preco = preco;
		this.comprado = comprado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public boolean isComprado() {
		return comprado;
	}

	public void setComprado(boolean comprado) {
		this.comprado = comprado;
	}
	
}
