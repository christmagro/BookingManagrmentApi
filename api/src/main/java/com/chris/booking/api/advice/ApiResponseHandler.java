package com.chris.booking.api.advice;

import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.api.exception.GlobalException;
import com.chris.booking.api.util.ResponseUtil;
import com.chris.booking.contract.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice(basePackages = "com.chris.booking.api.controller")
class ApiResponseHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiResponseHandler.class);

    private ResponseUtil responseUtil;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<CustomFieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new CustomFieldError(
                        fieldError.getField(),
                        fieldError.getCode(),
                        fieldError.getRejectedValue())
                )
                .collect(toList());


        return new ResponseEntity(responseUtil.getResponseWithError(ErrorResponse.builder().reason(fieldErrors.toString()).errorStatusCode(1000).build()), HttpStatus.BAD_REQUEST);
    }

    public ApiResponseHandler(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;

    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleNoBookingFound(Exception ex, NativeWebRequest request) {
        return new ResponseEntity(responseUtil.getResponseWithError(ErrorResponse.builder().reason(ex.getMessage()).errorStatusCode(1000).build()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNoSuchElementException(Exception ex, NativeWebRequest request) {
        return new ResponseEntity(responseUtil.getResponseWithError(ErrorResponse.builder().reason(ex.getMessage()).errorStatusCode(1000).build()), HttpStatus.BAD_REQUEST);
    }

}

@Data
@AllArgsConstructor
class CustomFieldError {
    private static final long serialVersionUID = 115431264722216393L;
    private String field;
    private String code;
    private Object rejectedValue;
}


