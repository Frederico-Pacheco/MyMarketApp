package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Lembrete implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6613141933822395382L;

	private long id;
	
	private String titulo;
	
	private String mensagem;
	
	private Pessoa pessoa;
	
	private Calendar dataCriacao;
	
	private Calendar dataAlteracao;

	public Lembrete(String mensagem, Date dataCriacao) {
		this.mensagem = mensagem;
		Calendar data = Calendar.getInstance();
		data.setTime(dataCriacao);
		this.dataCriacao = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
