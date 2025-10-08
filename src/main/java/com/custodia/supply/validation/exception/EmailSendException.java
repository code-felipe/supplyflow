package com.custodia.supply.validation.exception;

@SuppressWarnings("serial")
public class EmailSendException extends RuntimeException{
	 public EmailSendException(String msg) { super(msg); }
     public EmailSendException(String msg, Throwable cause) { super(msg, cause); }
}
