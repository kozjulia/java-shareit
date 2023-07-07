package ru.practicum.shareit.user.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDto {

    @NonNull
    private String name;
    @NonNull
    private final String email;

}