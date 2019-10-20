package br.pag.message;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * @author brunner.klueger
 */
@Component
public class Translator {

    private static ResourceBundleMessageSource messageSource;

    private static HttpServletRequest request;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource, HttpServletRequest request) {
        Translator.messageSource = messageSource;
        Translator.request = request;
    }

    public static String toLocale(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

    public static String toLocale(String msgCode, Object args) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(msgCode, null, locale);
        return String.format(message, args);
    }
}
