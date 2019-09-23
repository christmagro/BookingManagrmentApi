package com.chris.booking.contract;

import lombok.*;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingsApi extends BookingApiResponse {

    private static final long serialVersionUID = 3211549148523129694L;
    private Page<BookingApi> bookings;
}
