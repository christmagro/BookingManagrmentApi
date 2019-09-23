package com.chris.booking.contract;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingsListApi extends BookingApiResponse {

    private static final long serialVersionUID = 3616998442820661582L;
    private List<BookingApi> bookings;
}
