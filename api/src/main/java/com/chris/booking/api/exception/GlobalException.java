package com.chris.booking.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -8198521567662490434L;
    private String message;
    private String code;
}
