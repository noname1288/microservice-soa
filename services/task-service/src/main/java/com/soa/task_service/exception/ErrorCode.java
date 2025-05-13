package com.soa.task_service.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
    TASK_EXISTED(1010, "Task's name has already existed"),
    TASK_NOT_EXISTED(1011, "Your task has not existed");

    int code;
    String message;

    ErrorCode(int code, String message){
        this.code = code;
        this.message = message;
    }
}
