package com.chris.booking.api.data.service.impl;

import com.chris.booking.api.data.service.BookingDataService;
import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.api.mapper.CustomObjectMapper;
import com.chris.booking.api.model.Booking;
import com.chris.booking.api.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingDataServiceImpl implements BookingDataService {

    private BookingRepository bookingRepository;

    public BookingDataServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public Booking getBooking(int bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> listBookings(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }
}
