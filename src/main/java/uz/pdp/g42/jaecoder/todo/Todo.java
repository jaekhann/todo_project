package uz.pdp.g42.jaecoder.todo;

import lombok.*;
import uz.pdp.g42.jaecoder.annotations.Domain;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Domain
public class Todo {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private Boolean done;
    private Priority priority;
    private Date createdAt;
}
