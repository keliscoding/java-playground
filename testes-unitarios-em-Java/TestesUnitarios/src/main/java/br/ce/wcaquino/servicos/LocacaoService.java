package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	private LocacaoDao dao;
	private SPCService spcService;
	private EmailService emailService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null) throw new LocadoraException("Usuario vazio");

		boolean negativado;
		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente.");
		}
		if(negativado) throw new LocadoraException("Usuário Negativado.");
		if(filmes == null || filmes.isEmpty()) throw new LocadoraException("Filme vazio");
		if(filmeSemEstoque(filmes)) throw new FilmeSemEstoqueException("Filme sem estoque");


		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		//apply discount

		Double discount = 0.0;
		switch (filmes.size()){
			case 3:
				discount = 0.25;
				break;
			case 4:
				discount = 0.5;
				break;
			case 5:
				discount = 0.75;
				break;
			case 6:
				discount = 1.0;
				break;
			default:
				System.out.println("No discount applied...");
		}

		locacao.setValor(getMoviesTotal(filmes) - movieDiscount(filmes, discount));

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar

		dao.salvar(locacao);
		
		return locacao;
	}

	public void notificarAtrasos() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		locacoes.stream()
				.forEach(locacao -> {
					if(locacao.getDataRetorno().before(new Date())) {
						emailService.notificarAtraso(locacao.getUsuario());
					}
				});
	}

	public void prorrogarLocacao(Locacao locacao, int dias) {
		Locacao novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(obterDataComDiferencaDias(dias));
		novaLocacao.setValor(locacao.getValor()*dias);
		dao.salvar(novaLocacao);
	}


	private Double movieDiscount(List<Filme> filmes, Double discount) {
		Double movieValue = filmes.get(filmes.size() - 1).getPrecoLocacao();
		return movieValue * discount;
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