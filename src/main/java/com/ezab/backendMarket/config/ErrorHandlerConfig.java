package com.ezab.backendMarket.config;

import com.ezab.backendMarket.exceptions.GeneralServiceException;
import com.ezab.backendMarket.exceptions.NoDataFoundException;
import com.ezab.backendMarket.exceptions.ValidateServiceException;
import com.ezab.backendMarket.utils.WrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Wrapper;

@Slf4j
@ControllerAdvice
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> all(Exception e, WebRequest request) {
        log.error(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(
                false,
                "Internal server error",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidateServiceException.class)
    public ResponseEntity<?> validateService(ValidateServiceException e, WebRequest request) {
        log.info(e.getMessage());
        WrapperResponse<?> response = new WrapperResponse<>(
                false,
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> dataNotFound(NoDataFoundException e, WebRequest request) {
        log.info(e.getMessage());
        WrapperResponse<?> response = new WrapperResponse<>(
                false,
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GeneralServiceException.class)
    public ResponseEntity<?> generalServiceException(GeneralServiceException e, WebRequest request) {
        log.error(e.getMessage(), e);
        WrapperResponse<?> response = new WrapperResponse<>(
                false,
                e.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
