package uz.pdp.g42.jaecoder.dto;

import uz.pdp.g42.jaecoder.todo.Priority;

public record TodoCreateDto(String title, String description, Long userId, Priority priority) {
}
