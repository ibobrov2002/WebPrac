package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "Courses")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Courses  implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "coverage")
    private String coverage;
    @Column(name = "intensity")
    private Short intensity;
    @Column(name = "year")
    private Short year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses other = (Courses) o;
        return Objects.equals(id, other.id)
                && name.equals(other.name)
                && coverage.equals(other.coverage)
                && Objects.equals(intensity, other.intensity)
                && Objects.equals(year, other.year);
    }
}
