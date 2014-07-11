package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Grupo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1640000875868104722L;

	private long id;
	
	private String nome;
	
	private List<Pessoa> integrantes;
	
	private Calendar dataCobranca;
	
	private Calendar dataCriacao;
	
	private Calendar dataAlteracao;	

	public Grupo(String nome) {
		this.nome = nome;
	}

	public Grupo() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pessoa> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<Pessoa> integrantes) {
		this.integrantes = integrantes;
	}

	public Calendar getDataCobranca() {
		return dataCobranca;
	}

	public void setDataCobranca(Calendar dataCobranca) {
		this.dataCobranca = dataCobranca;
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
