package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null) throw new LocadoraException("Usuario vazio");
		if(filmes == null || filmes.isEmpty()) throw new LocadoraException("Filme vazio");
		if(filmeSemEstoque(filmes)) throw new FilmeSemEstoqueException("Filme sem estoque");


		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(getMoviesTotal(filmes));

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	private double getMoviesTotal(List<Filme> filmes) {
		return filmes.stream().mapToDouble(filme -> filme.getPrecoLocacao()).sum();
	}

	private boolean filmeSemEstoque(List<Filme> filmes) {
		return filmes.stream().anyMatch(filme -> filme.getEstoque() == 0);
	}

	public static void main(String[] args) {
		
	}
}