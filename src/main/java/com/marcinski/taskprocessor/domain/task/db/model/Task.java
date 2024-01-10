package com.marcinski.taskprocessor.domain.task.db.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode(of = "uuid")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid = UUID.randomUUID();
    private String input;
    private String pattern;
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;
    private int progress = 0;
    private TaskResult result;

    public static Task createInitialTask(String input, String pattern) {
        return new Task()
                .setInput(input)
                .setPattern(pattern);
    }
}
