package br.ce.wcaquino.servicos;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.LocacaoBuilder.umaLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.CustomMatchers.ehHoje;
import static br.ce.wcaquino.matchers.CustomMatchers.ehHojeComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class LocacaoServiceTest {

    private LocacaoService service;
    private SPCService spcService;
    private LocacaoDao dao;
    private EmailService emailService;
    //fazer a variavel statica faz ela sair do escopo do teste e o junit para de reinicializar ela
//    private static Integer i = 0;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        service = new LocacaoService();
        // passar flag --add-opens java.base/java.lang=ALL-UNNAMED
        dao = mock(LocacaoDao.class);
        spcService = mock(SPCService.class);
        emailService = mock(EmailService.class);
        service.setDao(dao);
        service.setSpcService(spcService);
        service.setEmailService(emailService);
//        i++;
//        System.out.println(i);
    }

    @After
    public void tearDown() {
        //System.out.println("After");
    }

//    @BeforeClass
//    public static void setupClass() {
//        System.out.println("Before Class");
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//        System.out.println("After Class");
//    }

    @Test
    public void teste() throws Exception {
        //cenario
        List<Filme> filmes = Arrays.asList(umFilme().agora());
        Usuario usuario = umUsuario().agora();

        //acao

        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        //      assertEquals(5.0, locacao.getValor(), 0.01);
        //ler sobre fluent interface:
        //      assertThat(locacao.getValor(), is(equalTo(5.0)));
        //usando rule:
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

        //custom matcher =)
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
    }

    //formas de tratar um teste que espera receber uma exceção

    //1) elegante
    // para usar essa, devemos ter a garantia de que a exceção é bem específica
    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacao_filmeSemEstoque_elegante() throws Exception {
        //cenario
        List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());
        Usuario usuario = umUsuario().agora();


        //acao

        service.alugarFilme(usuario, filmes);
    }

    //2) robusta
    // é a forma em que se há mais poder sobre a execução
    @Test
    public void testLocacao_usuarioVazio_robusta() throws FilmeSemEstoqueException {
        //cenario
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail("Deveria ter lançado uma exceção.");
        } catch(LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    //3) nova
    @Test
    public void testLocacao_filmeVazio_nova() throws Exception {
        //cenario
        Usuario usuario = umUsuario().agora();
        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao

        service.alugarFilme(usuario, null);

    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException {
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        when(spcService.possuiNegativacao(usuario)).thenReturn(true);

        // isso aqui é o mesmo que o de cima
        //when(spcService.possuiNegativacao(any(Usuario.class))).thenReturn(true);

        //acao
        try {
            service.alugarFilme(usuario, filmes);
        //verificacao
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuário Negativado."));
        }

        verify(spcService).possuiNegativacao(usuario);
    }

    @Test
    public void deveEnviarEmailParaLocacoesAtrasadas() {
        //cenario
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Locacao em dia").agora();
        Usuario usuario3 = umUsuario().comNome("Outro usuario atrasado").agora();
        List<Locacao> locacaos = Arrays.asList(
                umaLocacao()
                        .atrasada()
                        .comUsuario(usuario)
                        .agora(),
                umaLocacao().comUsuario(usuario2).agora(),
                umaLocacao().atrasada().comUsuario(usuario3).agora()
        );

        when(dao.obterLocacoesPendentes()).thenReturn(locacaos);

        //acao
        service.notificarAtrasos();

        //verificacao

        //isso aqui é o mesmo que verificar um usuario de cada vez
        verify(emailService, times(2)).notificarAtraso(any(Usuario.class));

        //verify(emailService).notificarAtraso(usuario);
        //verify(emailService).notificarAtraso(usuario3);
        verify(emailService, never()).notificarAtraso(usuario2);
        verifyNoMoreInteractions(emailService);
        //verifyZeroInteractions(spcService);
    }
}
