package com.chris.booking.api.util;


import com.chris.booking.contract.ApiResponseEnvelope;
import com.chris.booking.contract.BookingApiResponse;
import com.chris.booking.contract.ErrorResponse;
import com.chris.booking.contract.type.Status;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseUtil {

    public <T extends BookingApiResponse> ApiResponseEnvelope<T> getResponse(T t) {
        ApiResponseEnvelope<T> apiResponseEnvelope = new ApiResponseEnvelope<>();
        apiResponseEnvelope.setPayload(t);
        apiResponseEnvelope.setInstant(Instant.now());
        apiResponseEnvelope.setStatus(Status.OK.name());
        return apiResponseEnvelope;
    }

    public ApiResponseEnvelope getResponseWithError(ErrorResponse errorResponse) {
        ApiResponseEnvelope apiResponseEnvelope = new ApiResponseEnvelope<>();
        apiResponseEnvelope.setInstant(Instant.now());
        apiResponseEnvelope.setError(errorResponse);
        apiResponseEnvelope.setStatus(Status.ERROR.name());
        return apiResponseEnvelope;
    }


}
