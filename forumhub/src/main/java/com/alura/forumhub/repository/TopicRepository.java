package com.alura.forumhub.repository;

import com.alura.forumhub.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleIgnoreCaseAndMessageIgnoreCase(String title, String message);
    Page<Topic> findByCourseIgnoreCaseAndCreatedAtBetween(String course, java.time.LocalDateTime from, java.time.LocalDateTime to, Pageable pageable);
}
