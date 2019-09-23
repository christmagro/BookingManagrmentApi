package com.chris.booking.api.data.service;

import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.api.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingDataService {

    Booking createBooking(Booking booking);

    Booking getBooking(int bookingId) throws BookingNotFoundException;

    Page<Booking> listBookings(Pageable pageable);

    List<Booking> listBookings();
}
