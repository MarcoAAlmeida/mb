package br.dev.marcoalmeida.mb.utils;

public class RandomUtils {
    public static int randomNaturalNumberUpTo(Long max){
        assert(max>0);
        return Double.valueOf(Math.random() * max).intValue();
    }


}
