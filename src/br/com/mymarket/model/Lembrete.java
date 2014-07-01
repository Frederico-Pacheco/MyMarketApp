package br.com.mymarket.model;

import java.io.Serializable;
import java.util.Date;

import br.com.mymarket.utils.DateUtils;

public class Lembrete implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5946298131703457549L;

	private long id;
	
	private String nome;
	
	private Date data;
	
	private Pessoa pessoa;
	
	public Lembrete(String nome, Date data) {
		this.nome=nome;
		this.data=data;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getFormatDate() {
		return DateUtils.formatDate(this.getData());
	}

	public String getFormatHour() {
		return DateUtils.formatHour(this.getData());
	}
}
