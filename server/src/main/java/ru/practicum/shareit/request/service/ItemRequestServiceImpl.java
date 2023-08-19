package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.exception.ItemRequestNotSaveException;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public List<ItemRequestDto> getAllItemRequestsByUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id = " + userId + " не найден."));

        return ItemRequestMapper.INSTANCE.convertItemRequestListToItemRequestDTOList(
                itemRequestRepository.findAllByRequestorId(userId));
    }

    @Override
    public List<ItemRequestDto> getAllItemRequestsByOtherUsers(Long userId, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id = " + userId + " не найден."));

        return ItemRequestMapper.INSTANCE.convertItemRequestListToItemRequestDTOList(
                itemRequestRepository.findAllByRequestorIdNot(userId, page));
    }

    @Override
    public ItemRequestDto getItemRequestById(Long userId, Long requestId) {
        userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id = " + userId + " не найден."));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId).orElseThrow(() ->
                new ItemRequestNotFoundException("Запрос с идентификатором " + requestId + " не найден."));

        return ItemRequestMapper.INSTANCE.toItemRequestDto(itemRequest);
    }

    @Transactional
    @Override
    public ItemRequestDto saveItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id = " + userId + " не найден."));

        ItemRequest itemRequest = ItemRequestMapper.INSTANCE.toItemRequest(itemRequestDto, user);
        try {
            return ItemRequestMapper.INSTANCE.toItemRequestDto(itemRequestRepository.save(itemRequest));
        } catch (DataIntegrityViolationException e) {
            throw new ItemRequestNotSaveException("Запрос вещи не был создан: " + itemRequestDto);
        }
    }

}