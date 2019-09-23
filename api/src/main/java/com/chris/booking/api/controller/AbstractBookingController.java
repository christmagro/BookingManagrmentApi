package com.chris.booking.api.controller;

import com.chris.booking.api.util.ResponseUtil;
import lombok.Data;

@Data
public abstract class AbstractBookingController {

    private ResponseUtil responseUtil;

    public AbstractBookingController(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }
}
