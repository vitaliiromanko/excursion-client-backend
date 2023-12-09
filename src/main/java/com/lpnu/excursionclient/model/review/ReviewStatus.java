package com.lpnu.excursionclient.model.review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "review_status")
@Getter
@ToString
public class ReviewStatus {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;

    protected ReviewStatus() {
    }
}
