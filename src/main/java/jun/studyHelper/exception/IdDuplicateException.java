package jun.studyHelper.exception;

import lombok.Getter;

@Getter
public class IdDuplicateException extends RuntimeException{
    private ErrorCode errorCode;

    public IdDuplicateException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
