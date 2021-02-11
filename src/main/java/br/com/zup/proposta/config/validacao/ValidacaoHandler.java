package br.com.zup.proposta.config.validacao;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FormResponse> handle(MethodArgumentNotValidException ex) {
        List<String> response = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            response.add(String.format("%s %s", e.getField(), mensagem));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FormResponse(response));
    }

    @ExceptionHandler(ApiErroException.class)
    public ResponseEntity<FormResponse> handle(ApiErroException ex) {
        List<String> response = new ArrayList<>();
        response.add(ex.getMensagem());

        return ResponseEntity.status(ex.getHttpStatus()).body(new FormResponse(response));
    }
}
