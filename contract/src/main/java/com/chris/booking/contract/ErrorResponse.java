package com.chris.booking.contract;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {


    private static final long serialVersionUID = 4945012106754731592L;
    @ApiModelProperty(position = 1, value = "status code", required = true, example = "404")
    private int errorStatusCode;
    @ApiModelProperty(position = 2, value = "reason", required = true, example = "Something went wrong")
    private String reason;

}
