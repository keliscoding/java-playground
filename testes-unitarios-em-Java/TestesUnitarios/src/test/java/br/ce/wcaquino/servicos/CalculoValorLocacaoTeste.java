package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//DATA DRIVEN TEST
@RunWith(Parameterized.class)
public class CalculoValorLocacaoTeste {

    @InjectMocks
    private LocacaoService service;

    @Mock
    private LocacaoDao dao;
    @Mock
    private SPCService spc;


    @Parameterized.Parameter(value = 0)
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String description;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private static Filme filme1 = umFilme().agora();
    private static Filme filme2 = umFilme().agora();
    private static Filme filme3 = umFilme().agora();
    private static Filme filme4 = umFilme().agora();
    private static Filme filme5 = umFilme().agora();
    private static Filme filme6 = umFilme().agora();
    private static Filme filme7 = umFilme().agora();

    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
                { Arrays.asList(filme1, filme2), 10.0, "2 Filmes: Sem Desconto" },
                { Arrays.asList(filme1, filme2, filme3), 13.75, "3 Filmes: 25%" },
                { Arrays.asList(filme1, filme2, filme3, filme4), 17.50, "4 Filmes: 50%" },
                { Arrays.asList(filme1, filme2, filme3, filme4, filme5), 21.25, "5 Filmes: 70%" },
                { Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 25.0, "6 Filmes: 100%" },
                { Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 35.0, "7 Filmes: Sem Desconto" }
        });
    }

    @Test
    public void shouldCalculateDiscountValue() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        Usuario usuario = umUsuario().agora();

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(locacao.getValor(), is(valorLocacao));
    }

//    @Test
//    public void print(){
//        System.out.println(valorLocacao);
//    }
}
