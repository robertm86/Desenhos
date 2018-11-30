package edu.udc.psw.desenhos.DB.data;

import java.sql.Timestamp;

public class Desenhos {
	private long id;
	private String nome;
	private Timestamp criacao;
	private Timestamp modificacao;
	private int formato;

	public Desenhos(long id) {
		this.id = id;
		nome = "";
		long time = System.currentTimeMillis();
		criacao = new Timestamp( time );
		modificacao = new Timestamp( time );
		this.formato = formato;
	}

	public Desenhos(long id, String nome, Timestamp criacao, Timestamp modificacao, int formato) {
		this.id = id;
		this.nome = nome;
		this.criacao = criacao;
		this.modificacao = modificacao;
		this.formato = formato;
	}

	@Override
	public String toString() {
		return "Desenhos [id=" + id + ", nome=" + nome + ", criacao=" + criacao + ", modificacao=" + modificacao + ", formato=" + formato +  "]";
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

	public Timestamp getCriacao() {
		return criacao;
	}

	public void setCriacao(Timestamp criacao) {
		this.criacao = criacao;
	}

	public Timestamp getModificacao() {
		return modificacao;
	}

	public void setModificacao(Timestamp modificacao) {
		this.modificacao = modificacao;
	}

	public int getFormato() {
		return formato;
	}

	public void setFormato(int formato) {
		this.formato = formato;
	}
	
}
