package com.hp.tvbook.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.nio.file.AccessDeniedException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.hp.tvbook.model.RestResponse;
import com.hp.tvbook.service.LocaleMessageSourceService;

/**
 * Created by xinch on 2016/11/18.
 */
@ControllerAdvice
class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LocaleMessageSourceService messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException base) {
        log.error("BaseException:{}", base);
        if (base.getData() != null) {
            return new ResponseEntity<>(RestResponse.error(base.getStatus().value(), base.getErrorMessage(), base.getData()), base.getStatus());
        }
        return new ResponseEntity<>(RestResponse.error(base.getStatus().value(), base.getErrorMessage()), base.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException:{}", e.getMessage());
        return new ResponseEntity<>(RestResponse.error(FORBIDDEN.value(), messageSource.getMessage("access.is.denied")), FORBIDDEN);
    }

    @ExceptionHandler(value = {NullPointerException.class, ArrayIndexOutOfBoundsException.class})
    public ResponseEntity<?> handleCommonException(Exception ex) {
        log.error("Common Exception:{}", ex);
        return new ResponseEntity<>(RestResponse.error(INTERNAL_SERVER_ERROR.value(), ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpServerErrorException.class})
    public ResponseEntity<?> handleHttpServerErrorException(HttpServerErrorException ex) {
        log.error("HttpServerErrorException:{}", ex);
        return new ResponseEntity<>(RestResponse.error(INTERNAL_SERVER_ERROR.value(), ex.getStatusText()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("HttpClientErrorException:{}", ex);
        return new ResponseEntity<>(RestResponse.error(ex.getStatusText()), BAD_REQUEST);
    }

    @ExceptionHandler(value = {RestClientException.class})
    public ResponseEntity<?> handleRestClientException(RestClientException ex) {
        log.error("RestClientException:{}", ex);
        return new ResponseEntity<>(RestResponse.error(ex.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public final ResponseEntity<?> handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest request) throws JsonProcessingException {
        log.error("ConstraintViolationException:{}", ex);
        Map<String, String> errorMessages = Maps.newHashMap();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                errorMessages.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errorMessages.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(RestResponse.error(objectMapper.writeValueAsString(errorMessages), errorMessages), BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public final ResponseEntity<?> handleBindException(BindException ex) throws JsonProcessingException {
        log.error("BindException:{}", ex);
        Map<String, String> errorMessages = Maps.newHashMap();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                errorMessages.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errorMessages.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(RestResponse.error(objectMapper.writeValueAsString(errorMessages), errorMessages), BAD_REQUEST);
    }
}