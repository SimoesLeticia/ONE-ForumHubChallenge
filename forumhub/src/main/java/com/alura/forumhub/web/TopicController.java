package com.alura.forumhub.web;

import com.alura.forumhub.domain.topic.Topic;
import com.alura.forumhub.domain.topic.TopicStatus;
import com.alura.forumhub.repository.TopicRepository;
import com.alura.forumhub.web.dto.topic.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicController {

    private final TopicRepository repository;

    @GetMapping
    public Page<TopicResponseDTO> list(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) Integer year
    ) {
        if (course != null && year != null) {
            LocalDateTime from = LocalDate.of(year, 1, 1).atStartOfDay();
            LocalDateTime to = LocalDate.of(year, 12, 31).atTime(23, 59, 59);
            return repository.findByCourseIgnoreCaseAndCreatedAtBetween(course, from, to, pageable).map(TopicResponseDTO::from);
        }
        return repository.findAll(pageable).map(TopicResponseDTO::from);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> detail(@PathVariable Long id) {
        return repository.findById(id)
                .map(t -> ResponseEntity.ok(TopicResponseDTO.from(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid TopicCreateDTO body) {
        if (repository.existsByTitleIgnoreCaseAndMessageIgnoreCase(body.title(), body.message())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Tópico duplicado (título+mensagem).");
        }
        Topic saved = repository.save(Topic.builder()
                .title(body.title())
                .message(body.message())
                .author(body.author())
                .course(body.course())
                .status(TopicStatus.OPEN)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(TopicResponseDTO.from(saved));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateDTO body) {
        return repository.findById(id)
                .map(t -> {
                    t.setTitle(body.title());
                    t.setMessage(body.message());
                    if (body.status() != null) t.setStatus(body.status());
                    return ResponseEntity.ok(TopicResponseDTO.from(t));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repository.findById(id).map(t -> {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
