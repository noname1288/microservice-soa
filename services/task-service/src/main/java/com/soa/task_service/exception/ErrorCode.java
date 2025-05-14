package com.soa.task_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
    TASK_EXISTED(1010, "Task's name has already existed"),
    TASK_NOT_EXISTED(1011, "Your task has not existed"),
    NOT_PERMISSION(1012,"Only team leader can create tasks" );

    int code;
    String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
