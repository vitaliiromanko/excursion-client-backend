package com.lpnu.excursionclient.model.category;

import com.lpnu.excursionclient.model.excursion.Excursion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@ToString
public class Category {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @OneToMany(targetEntity = Excursion.class, mappedBy = "category")
    @ToString.Exclude
    private Set<Excursion> excursions = new HashSet<>();
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    protected Category() {
    }
}
