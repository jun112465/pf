package jun.studyHelper.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    ID_DUPLICATION(400,"MEMBER-ERR-400","USER ID DUPLICATED"),
    ;


    private int status;
    private String errorCode;
    private String message;
}