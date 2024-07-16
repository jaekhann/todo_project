package uz.pdp.g42.jaecoder.dto;

import uz.pdp.g42.jaecoder.todo.Priority;

public record TodoUpdateDto(Long id, String title, String description, Priority priority, Boolean completed) {
}
