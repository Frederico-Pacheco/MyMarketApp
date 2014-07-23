package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Calendar;

public class ItemCompra implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5624449590365259853L;

	private long id;
	
	private Produto produto;
	
	private Calendar dataCriacao;
	
	private Calendar dataAlteracao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Calendar getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Calendar dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Calendar getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Calendar dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
}
