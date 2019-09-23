package com.chris.booking.api;

import com.chris.booking.api.model.Booking;
import com.chris.booking.api.repository.BookingRepository;
import com.chris.booking.contract.BookingApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookingManagementApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingRepository bookingRepository;

    private List<Booking> bookings = new ArrayList<>();
    private Page<Booking> pageBookings;

    @Before
    public void setup() {
        Booking booking1 = Booking.builder()
                .customerName("Chris")
                .dropOffLocality("San Gwann")
                .pickupLocality("Bahrija")
                .pickupDateTime(LocalDateTime.now())
                .build();
        Booking booking2 = Booking.builder()
                .customerName("Chris")
                .dropOffLocality("San Gwann")
                .pickupLocality("Bahrija")
                .pickupDateTime(LocalDateTime.now())
                .build();
        bookings.addAll(Arrays.asList(booking1, booking2));
        pageBookings = new PageImpl(bookings);

    }


    @Test
    public void getListOfBookings() throws Exception {

        when(bookingRepository.findAll()).thenReturn(bookings);
        mvc.perform(get("/booking/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.bookings", hasSize(2)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getListOfNoBookings() throws Exception {

        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());
        mvc.perform(get("/booking/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.bookings", hasSize(0)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPageOfBookings() throws Exception {

        when(bookingRepository.findAll((Pageable) Mockito.any())).thenReturn(pageBookings);
        mvc.perform(get("/booking/page?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.bookings.content", hasSize(2)))
                .andExpect(jsonPath("$.payload.bookings.totalPages").value(1))
                .andExpect(jsonPath("$.payload.bookings.totalElements").value(2))
                .andExpect(jsonPath("$.payload.bookings.first").value("true"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPageOfNoBookings() throws Exception {
        when(bookingRepository.findAll((Pageable) Mockito.any())).thenReturn(new PageImpl(Collections.EMPTY_LIST));
        mvc.perform(get("/booking/page?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.bookings.content", hasSize(0)))
                .andExpect(jsonPath("$.payload.bookings.totalPages").value(1))
                .andExpect(jsonPath("$.payload.bookings.totalElements").value(0))
                .andExpect(jsonPath("$.payload.bookings.first").value("true"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getBooking() throws Exception {

        LocalDateTime dateTime = LocalDateTime.now();

        when(bookingRepository.findById(1)).thenReturn(Optional.of(Booking.builder()
                .id(1)
                .customerName("Chris")
                .dropOffLocality("San Gwann")
                .pickupLocality("Bahrija")
                .pickupDateTime(dateTime)
                .build()));
        mvc.perform(get("/booking/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload.id").value(1))
                .andExpect(jsonPath("$.payload.pickupDateTime").value(dateTime.toString()))
                .andExpect(jsonPath("$.payload.pickupLocality").value("Bahrija"))
                .andExpect(jsonPath("$.payload.dropOffLocality").value("San Gwann"))
                .andExpect(jsonPath("$.status").value("OK"))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getNonExistentBooking() throws Exception {

        when(bookingRepository.findById(1)).thenReturn(Optional.empty());
        mvc.perform(get("/booking/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("BookingId 1 not found"))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBooking() throws Exception {


        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .customerName("Chris")
                        .dropOffLocality("San Gwann")
                        .pickupLocality("Bahrija")
                        .pickupDateTime(LocalDateTime.now().plusHours(1))
                        .build()))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))

                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBookingMissingName() throws Exception {


        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .dropOffLocality("San Gwann")
                        .pickupLocality("Bahrija")
                        .pickupDateTime(LocalDateTime.now().plusHours(1))
                        .build()))
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("[CustomFieldError(field=customerName, code=NotBlank, rejectedValue=null)]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBookingMissingDate() throws Exception {


        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .customerName("Chris")
                        .dropOffLocality("San Gwann")
                        .pickupLocality("Bahrija")
                        .build()))
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("[CustomFieldError(field=pickupDateTime, code=NotNull, rejectedValue=null)]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBookingDateInThePast() throws Exception {

        LocalDateTime dateTime = LocalDateTime.now().minusHours(1);
        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .customerName("Chris")
                        .dropOffLocality("San Gwann")
                        .pickupLocality("Bahrija")
                        .pickupDateTime(dateTime)
                        .build()))
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("[CustomFieldError(field=pickupDateTime, code=Future, rejectedValue=" + dateTime + ")]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBookingMissingPickupLocality() throws Exception {

        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .customerName("Chris")
                        .dropOffLocality("San Gwann")
                        .pickupDateTime(LocalDateTime.now().plusHours(1))
                        .build()))
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("[CustomFieldError(field=pickupLocality, code=NotBlank, rejectedValue=null)]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createBookingMissingDropOffLocality() throws Exception {

        mvc.perform(post("/booking/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(BookingApi.builder()
                        .customerName("Chris")
                        .pickupLocality("Bahrija")
                        .pickupDateTime(LocalDateTime.now().plusHours(1))
                        .build()))
        )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.error.errorStatusCode").value(1000))
                .andExpect(jsonPath("$.error.reason").value("[CustomFieldError(field=dropOffLocality, code=NotBlank, rejectedValue=null)]"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
