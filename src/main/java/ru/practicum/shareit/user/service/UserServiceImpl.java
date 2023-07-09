package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.exception.UserNotSaveException;
import ru.practicum.shareit.user.exception.UserNotUpdateException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return userRepository.getUserDtoById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        UserDto userDtoNew = validateUserDto(userDto);
        return userRepository.saveUser(userDtoNew).
                orElseThrow(() -> new UserNotSaveException("Пользователь не был создан: " + userDtoNew));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        return userRepository.updateUser(userId, userDto).orElseThrow(() ->
                new UserNotUpdateException("Пользователь с id = " + userId + " не был обновлён: " + userDto));
    }

    @Override
    public boolean deleteUserById(Long userId) {
        return userRepository.deleteUserById(userId);
    }

    private UserDto validateUserDto(UserDto userDto) {
        String message;
        if (userDto.getName() == null) {
            logAndError("Ошибка! Имя или логин пользователя не может быть пустым.", 10001);
        }
        if (userDto.getEmail() == null) {
            logAndError("Ошибка! Адрес электронной почты пользователя не может быть пустым.", 10002);
        }
        return userDto;
    }

    private static void logAndError(String exp, int errorCode) {
        log.warn(exp + " Код ошибки: " + errorCode);
        throw new ValidationException(exp, errorCode);
    }

}