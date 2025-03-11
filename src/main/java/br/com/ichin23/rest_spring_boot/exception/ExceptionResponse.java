package br.com.ichin23.rest_spring_boot.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
