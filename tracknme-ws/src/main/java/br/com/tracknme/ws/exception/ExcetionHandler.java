package br.com.tracknme.ws.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tracknme.ws.exception.model.ErrorMessage;
import br.com.tracknme.ws.exception.service.LocationServiceException;

@ControllerAdvice
public class ExcetionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(value = {Exception.class, NullPointerException.class, LocationServiceException.class})
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){
				
		String errorMessageDetails = ex.getLocalizedMessage();
		
		if(errorMessageDetails == null) errorMessageDetails = ex.toString();
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDetails);
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
}
