package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "stories")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer story_id;

    private String authorId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String image;

    @ManyToMany
    @JoinTable(
    name = "STORY_GENRES",
    joinColumns = @JoinColumn(name = "story_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column()
    private LocalDateTime last_updated;

    @Column()
    @Builder.Default
    private Boolean is_completed = false;

    @Column()
    @Builder.Default
    private int view_count = 0;

    @Column()
    @Builder.Default
    private Boolean kids_appropriate = true;

}
