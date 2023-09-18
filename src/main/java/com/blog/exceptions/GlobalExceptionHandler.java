package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

// ye annotation controller me koi bhi exception ko handle karega globally

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// ye function agar 'ResourceNotFoundException' aata h kisi bhi controller me tb usko handle karega globally.
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();  // jo apiResponse ka exception ke bad message hoga isme aa jayega 
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.NOT_FOUND);
	}

	// for handling exception that we get while validating the fields
	// i.e 'MethodArgumentNotValidException'.
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex)
	{
		// phle wo sb field nikalna hoga jispe exception aa rha h with messages.
		// niklane ke bad usko map me store karenge
		
		Map<String, String> resp = new HashMap<>();
		
		// phle erros sb niklange(list dega), then errors se field nikalenge(using lambda fn) isliye hmko phle typecast karna hoga.
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();  // getting filed name
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex)
	{
		String message = ex.getMessage();  // jo apiResponse ka exception ke bad message hoga isme aa jayega 
		ApiResponse apiResponse = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiResponse , HttpStatus.BAD_REQUEST);
	}
	
}
   