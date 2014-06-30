package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Date;

public class ListaCompra implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7684865247808123507L;

	private Long id;
	
	private Date data;
	
	private String nome;
	
	public ListaCompra() {
		super();
	}
	
	
	public ListaCompra(Long id, Date data, String nome) {
		super();
		this.id = id;
		this.data = data;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
