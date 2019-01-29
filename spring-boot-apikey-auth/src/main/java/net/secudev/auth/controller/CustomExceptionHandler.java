package net.secudev.auth.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
  
	 @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Exception> handleError404(HttpServletRequest request, Exception e)   {
        
		 System.out.println("404");
        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }
}