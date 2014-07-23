package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Compra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5149535001513535922L;

	private long id;
	
	private Pessoa pessoa;
	
	private List<ItemCompra> itens;

	private Calendar dataCriacao;
	
	private Calendar dataAlteracao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<ItemCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
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
