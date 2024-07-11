package uz.ccrew.matchmaking.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import uz.ccrew.matchmaking.dto.GoodResponse;
import uz.ccrew.matchmaking.dto.Response;

import jakarta.validation.ConstraintViolationException;
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
import uz.ccrew.matchmaking.exp.AuthHeaderNotFound;

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

    @ExceptionHandler({ConstraintViolationException.class})
    private ResponseEntity<Response<?>> handle(ConstraintViolationException e) {
        String message = e.getMessage();
        int index = message.indexOf(": ");
        message = message.substring(index + 1);
        return GoodResponse.badRequest(message);
    }

    @ExceptionHandler({JWTDecodeException.class, SignatureException.class})
    private ResponseEntity<Response<?>> forbiddenHandler(RuntimeException e) {
        return GoodResponse.error(HttpStatus.FORBIDDEN, 0, e.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class, TokenExpiredException.class, AuthHeaderNotFound.class})
    private ResponseEntity<Response<?>> exceptionHandler(RuntimeException e) {
        return GoodResponse.error(HttpStatus.BAD_REQUEST, 0, e.getMessage());
    }

}