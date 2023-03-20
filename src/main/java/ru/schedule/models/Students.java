package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "Students")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Students implements CommonEntity<Long>{

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

    @Column(name = "year")
    private Short year;

    @Column(name = "stream")
    private Short stream;

    @Column(name = "group_st")
    private Integer group_st;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students other = (Students) o;
        return Objects.equals(id, other.id)
                && first_name.equals(other.first_name)
                && last_name.equals(other.last_name)
                && patronymic.equals(other.patronymic)
                && Objects.equals(stream, other.stream)
                && Objects.equals(year, other.year)
                && Objects.equals(group_st, other.group_st);
    }
}
