package br.dev.marcoalmeida.mb.exception;

import br.dev.marcoalmeida.mb.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class MbExceptionHandler {

    @ExceptionHandler(MbException.class)
    public final ResponseEntity<ErrorDTO> handleMbException(MbException mbException){
        return ResponseEntity.unprocessableEntity().body(ErrorDTO.of(List.of(mbException.getMessage())));

    }
}
