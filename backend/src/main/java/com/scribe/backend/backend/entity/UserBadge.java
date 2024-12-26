// package com.scribe.backend.backend.entity;

// import java.util.Date;

// import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class UserBadge {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer userBadgeId; 

//     @ManyToOne
//     @JoinColumn(name = "user_id", nullable = false)
//     private User user;

//     @ManyToOne
//     @JoinColumn(name = "badge_id", nullable = false)
//     private Badges badge;

//     @Column(nullable = false)
//     private Date earned_at; 
// }
