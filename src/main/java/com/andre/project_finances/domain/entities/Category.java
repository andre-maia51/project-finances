package com.andre.project_finances.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "category")
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String description;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;

    public Category(String name, String description, User user) {
        this.name = name;
        this.description = description;
        this.user = user;
    }
}
