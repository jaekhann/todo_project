package uz.pdp.g42.jaecoder.dto;

public record TodoUpdateDto(Long id, String title, String description, Boolean done) {
}
