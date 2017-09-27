package de.asvaachen.workinghours.backend.project;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.UUID;

public class ProjectEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;

    private String name;

    private String description;
}
