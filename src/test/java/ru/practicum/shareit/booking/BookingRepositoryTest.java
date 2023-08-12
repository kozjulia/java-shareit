package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.StatusBooking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private final User user = new User();
    private final Item item2 = new Item();

    @BeforeEach
    public void addComments() {
        user.setName("name");
        user.setEmail("mail@mail.ru");

        Item item1 = new Item();
        item1.setName("1");
        item1.setDescription("1");
        item1.setAvailable(true);
        item1.setOwner(user);
        item2.setName("2");
        item2.setDescription("2");
        item2.setAvailable(false);
        item2.setOwner(user);

        Booking booking1 = new Booking();
        booking1.setStart(LocalDateTime.now());
        booking1.setEnd(LocalDateTime.now());
        booking1.setItem(item1);
        booking1.setBooker(user);
        booking1.setStatus(StatusBooking.WAITING);
        Booking booking2 = new Booking();
        booking2.setStart(LocalDateTime.now());
        booking2.setEnd(LocalDateTime.now());
        booking2.setItem(item2);
        booking2.setBooker(user);
        booking2.setStatus(StatusBooking.WAITING);
        Booking booking3 = new Booking();
        booking3.setStart(LocalDateTime.now());
        booking3.setEnd(LocalDateTime.now().plusHours(3));
        booking3.setItem(item2);
        booking3.setBooker(user);
        booking3.setStatus(StatusBooking.WAITING);

        userRepository.save(user);
        itemRepository.save(item1);
        itemRepository.save(item2);
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);
    }

    @AfterEach
    public void deleteComments() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
        bookingRepository.deleteAll();
    }

    @Test
    @DisplayName("существует ли бронирование, когда вызвано, то получено количество бронирований")
    void isFindBooking() {
        Long countBookings = bookingRepository.isFindBooking(item2.getId(), user.getId(),
                LocalDateTime.now().plusMinutes(30));

        assertThat(1L, equalTo(countBookings));
    }

}