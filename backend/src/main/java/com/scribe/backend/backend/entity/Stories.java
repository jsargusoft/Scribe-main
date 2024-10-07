package com.scribe.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data //getter,setter,toString
@NoArgsConstructor
@AllArgsConstructor
public class Stories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private int story_id;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "user_id", nullable = false)
    private User author_id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(
        name = "STORY_GENRES",
        joinColumns = @JoinColumn(name = "story_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(nullable = false)
    private Date created_at;

    @Column(name = "last_updated")
    private Date last_updated;

    @Column()
    private Boolean is_completed = false;

    @Column()
    private int view_count = 0;

    @Column()
    private Boolean kids_appropriate = true;

}


