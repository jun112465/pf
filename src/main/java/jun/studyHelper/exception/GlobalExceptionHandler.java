package jun.studyHelper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdDuplicateException.class)
    public ResponseEntity<ErrorResponse> handleIdDuplicateException(IdDuplicateException ex){
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PwDifferentException.class)
    public String handlePwDifferException(PwDifferentException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "information";
    }

    @ExceptionHandler(UserConfirmException.class)
    public String handleUserConfirmException(UserConfirmException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "information";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse response = new ErrorResponse(ErrorCode.INTER_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
