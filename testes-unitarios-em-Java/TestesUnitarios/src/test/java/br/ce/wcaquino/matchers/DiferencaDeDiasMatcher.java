package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

public class DiferencaDeDiasMatcher extends TypeSafeMatcher<Date>{

    private Integer dias;
    private Date dataComDiferencaDeDias;

    public DiferencaDeDiasMatcher(Integer dias) {
        this.dias = dias;
        this.dataComDiferencaDeDias = DataUtils.obterDataComDiferencaDias(dias);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(dataComDiferencaDeDias.toString());
    }

    @Override
    protected boolean matchesSafely(Date dataRetorno) {
        return isMesmaData(dataRetorno, dataComDiferencaDeDias);
    }
}
