package com.lpnu.excursionclient.model.excursion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "excursion_topic_type")
@Getter
@ToString
public class ExcursionTopicType {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;

    protected ExcursionTopicType() {
    }
}
