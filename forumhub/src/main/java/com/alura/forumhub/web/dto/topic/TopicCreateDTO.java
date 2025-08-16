package com.alura.forumhub.web.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicCreateDTO(
        @NotBlank @Size(max = 200) String title,
        @NotBlank String message,
        @NotBlank @Size(max = 100) String author,
        @NotBlank @Size(max = 100) String course
) { }
