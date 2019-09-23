package com.chris.booking.api.service.impl;

import com.chris.booking.api.data.service.BookingDataService;
import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.api.mapper.CustomObjectMapper;
import com.chris.booking.api.model.Booking;
import com.chris.booking.api.service.BookingService;
import com.chris.booking.contract.BookingApi;
import com.chris.booking.contract.BookingsApi;
import com.chris.booking.contract.BookingsListApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private CustomObjectMapper mapper;

    private BookingDataService bookingDataService;

    public BookingServiceImpl(CustomObjectMapper mapper, BookingDataService bookingDataService) {
        this.mapper = mapper;
        this.bookingDataService = bookingDataService;
    }

    @Override
    public BookingApi createBooking(BookingApi bookingApi) {
        return mapper.map(bookingDataService.createBooking(mapper.map(bookingApi, Booking.class)), BookingApi.class);
    }

    @Override
    public BookingApi getBooking(int bookingId) throws BookingNotFoundException {
        return mapper.map(bookingDataService.getBooking(bookingId), BookingApi.class);
    }

    @Override
    public BookingsApi getAllBookings(Pageable pageable) {
        return BookingsApi.builder().bookings(bookingDataService.listBookings(pageable).map(booking -> mapper.map(booking, BookingApi.class))).build();
    }

    @Override
    public BookingsListApi getAllBookings() {
        return BookingsListApi.builder().bookings(mapper.mapAsList(bookingDataService.listBookings(), BookingApi.class)).build();
    }
}
