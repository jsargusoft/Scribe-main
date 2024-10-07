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
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "story_id", nullable = false)
    private Stories story;

    @ManyToOne()
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapters chapter;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date uploaded_at;
    
}

