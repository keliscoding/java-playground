package io.github.zam0k.model;

import java.io.Serializable;

public class Atividade implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349165660940421443L;

	private Long id;
	private String nome;
	private TipoAtividade tipoAtividade;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoAtividade getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(TipoAtividade tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
		
}
