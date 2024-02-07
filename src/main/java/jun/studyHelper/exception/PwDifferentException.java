package jun.studyHelper.exception;

public class PwDifferentException extends RuntimeException{
    private ErrorCode errorCode;
    public PwDifferentException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
