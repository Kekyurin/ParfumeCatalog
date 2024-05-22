package org.example.perfumecatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "perfumes_name_key",
                columnNames = "name"
        )
)
public class Perfume {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String perfumePictureName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerfumePictureName() {
        return perfumePictureName;
    }

    public void setPerfumePictureName(String perfumePictureName) {
        this.perfumePictureName = perfumePictureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfume perfume = (Perfume) o;
        return Objects.equals(id, perfume.id) && Objects.equals(name, perfume.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
