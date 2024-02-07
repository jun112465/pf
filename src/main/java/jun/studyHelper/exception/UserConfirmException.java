package jun.studyHelper.exception;

public class UserConfirmException extends RuntimeException{

    private ErrorCode errorCode;
    public UserConfirmException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
