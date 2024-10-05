package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer history_id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "story_id", nullable = false)
    private Stories story;

    @ManyToOne()
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapters chapter;

    @Column(name = "last_read", nullable = false)
    private Date last_read;

    @Column(name = "progress", nullable = false)
    private Integer progress;
}
