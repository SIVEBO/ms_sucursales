package com.sivebo.ms_sucursales.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException ex) {
		log.error("Data integrity violation: {}", ex.getMessage());
		Map<String, String> error = new HashMap<>();
		error.put("error", "Error de integridad de datos: ya existe un registro con estos valores");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errores);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Map<String, String>> handleNumberFormat(NumberFormatException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("error", "El parámetro 'id' debe ser un número entero válido");
		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
		log.error("Unhandled runtime exception", ex);
		Map<String, String> error = new HashMap<>();
		error.put("error", "Ocurrió un error interno en el servidor");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
