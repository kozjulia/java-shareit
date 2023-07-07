package ru.practicum.shareit.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    private Long id; // уникальный идентификатор пользователя
    private String name; // имя или логин пользователя
    @NonNull
    private final String email; // адрес электронной почты, уникальный

}