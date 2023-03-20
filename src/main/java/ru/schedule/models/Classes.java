package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "Classes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Classes implements CommonEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_id")
    private Long course_id;

    @Column(name = "classroom_num")
    private Long classroom_num;

    @Column(name = "time")
    private String time;

    @Column(name = "day_of_week")
    private Short day_of_week;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes other = (Classes) o;
        return Objects.equals(id, other.id)
                && Objects.equals(course_id, other.course_id)
                && Objects.equals(classroom_num, other.classroom_num)
                && Objects.equals(time, other.time)
                && Objects.equals(day_of_week, other.day_of_week);
    }

}
