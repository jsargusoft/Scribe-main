package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int multimedia_id;

    @ManyToOne()
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapters chapter;

    @Column(nullable = false)
    private String url;

}
