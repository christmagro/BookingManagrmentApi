package com.chris.booking.api.service;

import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.contract.BookingApi;
import com.chris.booking.contract.BookingsApi;
import com.chris.booking.contract.BookingsListApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    BookingApi createBooking(BookingApi bookingApi);

    BookingApi getBooking(int bookingId) throws BookingNotFoundException;

    BookingsApi getAllBookings(Pageable pageable);

    BookingsListApi getAllBookings();
}
