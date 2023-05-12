package io.github.zam0k.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import io.github.zam0k.model.Atividade;
import io.github.zam0k.model.TipoAtividade;

@Named("atividadeMB")
@SessionScoped
public class AtividadeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -18152090962227525L;
	
	@Inject
	private Atividade atividade;
	
	public TipoAtividade[] getAtividades() {
		return TipoAtividade.values();
	}
	
	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
		
}
