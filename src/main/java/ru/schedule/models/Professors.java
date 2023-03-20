package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "Professors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Professors implements CommonEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "patronymic")
    private String patronymic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professors other = (Professors) o;
        return Objects.equals(id, other.id)
                && first_name.equals(other.first_name)
                && last_name.equals(other.last_name)
                && patronymic.equals(other.patronymic);
    }

}
