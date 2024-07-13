package uz.ccrew.matchmaking.controller;

import uz.ccrew.matchmaking.dto.ResponseMaker;
import uz.ccrew.matchmaking.dto.Response;
import uz.ccrew.matchmaking.exp.*;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;


@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Response<?> r = new Response<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fieldError -> r.addError(fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(r);
    }

    @ExceptionHandler({BasicException.class})
    private ResponseEntity<Response<?>> basicHandler(BasicException e) {
        return ResponseMaker.error(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler({BadCredentialsException.class})
    private ResponseEntity<Response<?>> forbiddenHandler(RuntimeException e) {
        return ResponseMaker.error(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler({JWTDecodeException.class, SignatureException.class})
    private ResponseEntity<Response<?>> unauthorizedHandler(RuntimeException e) {
        return ResponseMaker.error(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Response<?>> handle(Exception e) {
        return ResponseMaker.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}