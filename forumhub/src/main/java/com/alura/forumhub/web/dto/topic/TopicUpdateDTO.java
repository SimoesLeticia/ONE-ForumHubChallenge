package com.alura.forumhub.web.dto.topic;

import com.alura.forumhub.domain.topic.TopicStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicUpdateDTO(
        @NotBlank @Size(max = 200) String title,
        @NotBlank String message,
        TopicStatus status
) { }
