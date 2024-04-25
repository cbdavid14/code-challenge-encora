package com.encora.challenged.http;

import com.encora.challenged.http.constants.ErrorMessage;
import com.encora.challenged.http.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialException.class)
    public @ResponseBody HttpErrorInfo handleBadCredentialsExceptions(
      ServerHttpRequest request, BadCredentialException ex) {

        return createHttpErrorInfo(UNAUTHORIZED, request, ex);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public @ResponseBody HttpErrorInfo handleSignatureJwtExceptions(
      ServerHttpRequest request, AccessDeniedException ex) {

        return createHttpErrorInfo(FORBIDDEN, request, ex);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(SignatureJwtException.class)
    public @ResponseBody HttpErrorInfo handleSignatureJwtExceptions(
      ServerHttpRequest request, SignatureJwtException ex) {

        return createHttpErrorInfo(FORBIDDEN, request, ex);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ExpiredTokenException.class)
    public @ResponseBody HttpErrorInfo handleExpireTokenExceptions(
      ServerHttpRequest request, ExpiredTokenException ex) {

        return createHttpErrorInfo(FORBIDDEN, request, ex);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpErrorInfo handleNotFoundExceptions(
            ServerHttpRequest request, NotFoundException ex) {

        return createHttpErrorInfo(NOT_FOUND, request, ex);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(
            ServerHttpRequest request, InvalidInputException ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody HttpErrorInfo handleBadRequestExceptions(
      ServerHttpRequest request, BadRequestException ex) {

        return createHttpErrorInfo(BAD_REQUEST, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(
      HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        logger.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(path, httpStatus, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorInfo> handleSecurityException(Exception ex) {
        String path = "./";
        HttpErrorInfo httpErrorInfo = null;

        if (ex instanceof AccessDeniedException) {
            httpErrorInfo = new HttpErrorInfo(path, FORBIDDEN, ErrorMessage.ACCESS_DENIED.getText()+ "[" + ex.getMessage() + "]");
        }

        if (ex instanceof SignatureException) {
            httpErrorInfo = new HttpErrorInfo(path, FORBIDDEN, ErrorMessage.SIGNATURE_INVALID.getText()+ "[" + ex.getMessage() + "]");
        }

        if (ex instanceof ExpiredJwtException) {
            httpErrorInfo = new HttpErrorInfo(path, UNAUTHORIZED, ErrorMessage.EXPIRED_TOKEN.getText() + "[" + ex.getMessage() + "]");
        }
        if (httpErrorInfo == null) {
            httpErrorInfo = new HttpErrorInfo(path, INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return ResponseEntity.status(httpErrorInfo.getHttpStatus()).body(httpErrorInfo);
    }
}
