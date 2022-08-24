package br.ce.wcaquino.matchers;

public class CustomMatchers {
    public static DiferencaDeDiasMatcher ehHojeComDiferencaDias(Integer dias) {
        return new DiferencaDeDiasMatcher(dias);
    }
}
