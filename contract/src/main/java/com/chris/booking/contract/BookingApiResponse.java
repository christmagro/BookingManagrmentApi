package com.chris.booking.contract;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(subTypes = {BookingsApi.class, BookingApi.class}, discriminator = "type",
        description = "Supertype of all Booking methods.")
public class BookingApiResponse implements Serializable {
    private static final long serialVersionUID = 1605376587894305561L;
}
