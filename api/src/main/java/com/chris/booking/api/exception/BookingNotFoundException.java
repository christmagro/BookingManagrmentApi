package com.chris.booking.api.exception;

public class BookingNotFoundException extends GlobalException {
    private static final long serialVersionUID = 4049413404849370022L;

    public BookingNotFoundException(int bookingId) {
        super("BookingId " + bookingId + " not found", "100");
    }
}
