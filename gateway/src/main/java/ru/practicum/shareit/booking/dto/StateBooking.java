package ru.practicum.shareit.booking.dto;

//import java.util.Optional;

public enum StateBooking {

    ALL, // все бронирования
    CURRENT, // текущие бронирования
    PAST, // завершённые бронирования
    FUTURE, // будущие бронирования
    WAITING, // ожидающие подтверждения бронирования
    REJECTED // отклонённые бронирования

    /*public static Optional<StateBooking> from(String stringState) {
        for (StateBooking state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }*/

}