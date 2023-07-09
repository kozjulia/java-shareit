package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.exception.ItemNotUpdateException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.ItemNotSaveException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getAllItemsByUser(Long userId) {
        userRepository.getUserById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));
        return itemRepository.getAllItemsByUser(userId);
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return itemRepository.getItemDtoById(itemId).
                orElseThrow(() -> new ItemNotFoundException("Вещь с идентификатором " + itemId + " не найдена."));
    }

    @Override
    public ItemDto saveItem(ItemDto itemDto, Long userId) {
        ItemDto itemDtoNew = validateItemDto(itemDto);
        return itemRepository.saveItem(itemDto, userRepository.getUserById(userId).orElseThrow(() ->
                        new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."))).
                orElseThrow(() -> new ItemNotSaveException("Вещь не была создана: " + itemDtoNew));
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        User user = userRepository.getUserById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с идентификатором " + userId + " не найден."));
        return itemRepository.updateItem(itemId, itemDto, user).
                orElseThrow(() -> new ItemNotUpdateException("Вещь с id = " + itemId +
                        " не была обновлена: " + itemDto));
    }

    @Override
    public List<ItemDto> findItems(String text, Long userId) {
        if (text.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return itemRepository.findItems(text, userId);
    }

    private ItemDto validateItemDto(ItemDto itemDto) {
        String message;
        if (itemDto.getName() == null) {
            logAndError("Ошибка! Краткое название вещи не может быть пустым.", 20001);
        }
        if (itemDto.getDescription() == null) {
            logAndError("Ошибка! Развёрнутое описание вещи не может быть пустым. ", 20002);
        }
        if (itemDto.getAvailable() == null) {
            logAndError("Ошибка! Статус доступности вещи для аренды не может быть пустым.", 20003);
        }
        return itemDto;
    }

    private static void logAndError(String exp, int errorCode) {
        log.warn(exp + " Код ошибки: " + errorCode);
        throw new ValidationException(exp, errorCode);
    }

}