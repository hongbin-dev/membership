package me.hongbin.generic;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.hongbin.generic.exception.BusinessException;
import me.hongbin.generic.exception.LockConflictException;
import me.hongbin.generic.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessException(BusinessException exception) {

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        return new ErrorResponse(
            "잘못된 입력입니다.",
            exception.getBindingResult().getFieldErrors().stream()
                .map(it ->
                    new ErrorResponse.ErrorDto(
                        it.getField(),
                        it.getRejectedValue(),
                        it.getDefaultMessage())
                )
                .toList()
        );
    }

    @ExceptionHandler(LockConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleLockConflictException(LockConflictException exception) {
        log.info(exception.getMessage());

        return new ErrorResponse("잠시 후 다시 시도해 주세요.");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(RuntimeException exception) {
        log.info("### handleRuntimeException {}", exception.getMessage() + Arrays.toString(exception.getStackTrace()));

        return new ErrorResponse("잘못된 요청입니다.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        log.error("### handleException {}", exception.getMessage() + Arrays.toString(exception.getStackTrace()));

        return new ErrorResponse("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요");
    }
}
