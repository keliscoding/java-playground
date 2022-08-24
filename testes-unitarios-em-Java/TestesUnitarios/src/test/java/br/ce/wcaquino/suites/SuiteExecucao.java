package br.ce.wcaquino.suites;

import br.ce.wcaquino.servicos.CalculoValorLocacaoTeste;
import br.ce.wcaquino.servicos.LocacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//em ferramentas de integração continua isso aqui
//não é muito interessante pq duplica a execução de testes

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculoValorLocacaoTeste.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {
    //Remova se puder!

    @BeforeClass
    public static void before(){
        System.out.println("before class na suite de testes!");
    }

    @AfterClass
    public static void after(){
        System.out.println("after class na suite de testes!");
    }
}
