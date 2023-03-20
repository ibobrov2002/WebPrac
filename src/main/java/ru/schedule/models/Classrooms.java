package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "Classrooms")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Classrooms implements CommonEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "roominess")
    private Integer roominess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classrooms other = (Classrooms) o;
        return Objects.equals(id, other.id)
                && Objects.equals(roominess, other.roominess);
    }

}
