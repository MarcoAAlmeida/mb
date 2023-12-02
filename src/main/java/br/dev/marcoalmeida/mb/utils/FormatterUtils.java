package br.dev.marcoalmeida.mb.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class FormatterUtils {
    public static String DEFAULT_FORMAT =  "";

    public static DateTimeFormatter formatter(){
        return DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    }

    public static DateTimeFormatter formatterUI(){
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    }
}
