package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.mapper.BookingMapperImpl;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

class BookingMapperImplTest {

    private final BookingMapperImpl bookingMapper = new BookingMapperImpl();

    @Test
    @DisplayName("получен маппер в ДТО бронирования, когда вызван нуль, то получен нуль")
    void toBookingOutDto() {
        BookingOutDto bookingOutDto = bookingMapper.toBookingOutDto(null);

        assertThat(bookingOutDto, nullValue());
    }

    @Test
    @DisplayName("получен маппер в бронирование, когда вызван нуль, то получен нуль")
    void toBooking() {
        Booking booking = bookingMapper.toBooking(null, null, null);

        assertThat(booking, nullValue());
    }

    @Test
    @DisplayName("получен маппер в список ДТО бронирований, когда вызван нуль, то получен нуль")
    void convertBookingListToBookingOutDTOList() {
        List<BookingOutDto> bookings = bookingMapper.convertBookingListToBookingOutDTOList(null);

        assertThat(bookings, nullValue());
    }

}