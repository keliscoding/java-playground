package br.ce.wcaquino.matchers;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class EhHojeMatcher extends TypeSafeMatcher<Date> {

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.isMesmaData(date, new Date());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(new Date().toString());
    }
}
