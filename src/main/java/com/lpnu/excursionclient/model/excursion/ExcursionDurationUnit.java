package com.lpnu.excursionclient.model.excursion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "excursion_duration_unit")
@Getter
@ToString
public class ExcursionDurationUnit {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;

    protected ExcursionDurationUnit() {
    }
}
