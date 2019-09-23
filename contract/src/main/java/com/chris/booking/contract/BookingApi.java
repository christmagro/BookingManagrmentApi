package com.chris.booking.contract;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingApi extends BookingApiResponse {

    private static final long serialVersionUID = -3073094005642252952L;

    private int id;

    @NotBlank
    @Size(max = 255)
    private String customerName;

    @NotNull
    @Future
    private LocalDateTime pickupDateTime;

    @NotBlank
    @Size(max = 100)
    private String pickupLocality;

    @NotBlank
    @Size(max = 100)
    private String dropOffLocality;

    private String notes;

}
