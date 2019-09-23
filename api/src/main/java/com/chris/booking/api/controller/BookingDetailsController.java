package com.chris.booking.api.controller;

import com.chris.booking.api.exception.BookingNotFoundException;
import com.chris.booking.api.service.BookingService;
import com.chris.booking.api.util.ResponseUtil;
import com.chris.booking.contract.ApiResponseEnvelope;
import com.chris.booking.contract.BookingApi;
import com.chris.booking.contract.BookingsApi;
import com.chris.booking.contract.BookingsListApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/booking")
public class BookingDetailsController extends AbstractBookingController {

    private BookingService bookingService;


    public BookingDetailsController(ResponseUtil responseUtil, BookingService bookingService) {
        super(responseUtil);
        this.bookingService = bookingService;
    }

    @PostMapping(value = "/")
    public ApiResponseEnvelope<BookingApi> createBooking(@RequestBody @Valid BookingApi bookingApi) {
        return getResponseUtil().getResponse(bookingService.createBooking(bookingApi));
    }
    @GetMapping(value = "/{bookingId}")
    public ApiResponseEnvelope<BookingApi> getBooking(@PathVariable int bookingId) throws BookingNotFoundException {
        return getResponseUtil().getResponse(bookingService.getBooking(bookingId));
    }
    @GetMapping(value = "/page")
    public ApiResponseEnvelope<BookingsApi> getBookingsPage(@PageableDefault(size = 100, sort = "id") Pageable pageable) {
        return getResponseUtil().getResponse(bookingService.getAllBookings(pageable));
    }

    @GetMapping(value = "/list")
    public ApiResponseEnvelope<BookingsListApi> getBookings() {
        return getResponseUtil().getResponse(bookingService.getAllBookings());
    }


}

