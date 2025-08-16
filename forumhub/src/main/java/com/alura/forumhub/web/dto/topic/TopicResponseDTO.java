package com.alura.forumhub.web.dto.topic;

import com.alura.forumhub.domain.topic.Topic;
import com.alura.forumhub.domain.topic.TopicStatus;

import java.time.LocalDateTime;

public record TopicResponseDTO(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        TopicStatus status,
        String author,
        String course
) {
    public static TopicResponseDTO from(Topic t) {
        return new TopicResponseDTO(
                t.getId(),
                t.getTitle(),
                t.getMessage(),
                t.getCreatedAt(),
                t.getStatus(),
                t.getAuthor(),
                t.getCourse()
        );
    }
}
