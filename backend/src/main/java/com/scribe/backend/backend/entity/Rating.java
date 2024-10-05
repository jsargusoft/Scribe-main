package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RATINGS")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rating_id;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne()
    @JoinColumn(name = "story_id", nullable = false)
    private Stories story;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;  

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date published_at = new Date();

}

