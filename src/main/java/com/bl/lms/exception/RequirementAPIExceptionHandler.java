package com.bl.lms.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.bl.lms.dto.ResponseDTO;

@ControllerAdvice
@Slf4j
public class RequirementAPIExceptionHandler {
	private static final String message = "Exception While Processing REST Request";

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDTO> handelHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		ResponseDTO respDTO = new ResponseDTO(message, "Should have date in dd MMM yyyy format");
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errMesg = errorList.stream().map(objErr -> objErr.getDefaultMessage())
				.collect(Collectors.toList());
		ResponseDTO respDTO = new ResponseDTO("Exception While Processing REST Request", errMesg);
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RequirementAPIException.class)
	public ResponseEntity<ResponseDTO> handleEmployeePayrollException(RequirementAPIException exception) {
		ResponseDTO respDTO = new ResponseDTO("Exception while REST Request", exception.getMessage());
		return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.BAD_REQUEST);
	}
}