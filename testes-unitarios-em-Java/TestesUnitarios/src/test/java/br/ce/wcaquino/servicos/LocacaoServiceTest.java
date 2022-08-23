package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void teste() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        //acao

        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificacao

        //      assertEquals(5.0, locacao.getValor(), 0.01);
        //ler sobre fluent interface:
        //      assertThat(locacao.getValor(), is(equalTo(5.0)));
        //usando rule:
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
    }

    //formas de tratar um teste que espera receber uma exceção

    //1) elegante
    @Test(expected = Exception.class)
    public void testLocacao_filmeSemEstoque_elegante() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //acao

        service.alugarFilme(usuario, filme);
    }

    //2) robusta

    @Test
    public void testLocacao_filmeSemEstoque_robusta() {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        //acao
        try {
            service.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lançado uma exceção.");
        } catch(Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    //3) nova
    @Test
    public void testLocacao_filmeSemEstoque_nova() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);
        exception.expect(Exception.class);
        exception.expectMessage("Filme sem estoque");

        //acao

        service.alugarFilme(usuario, filme);

    }


}
