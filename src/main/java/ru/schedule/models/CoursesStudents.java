package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "CoursesStudents")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoursesStudents implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_id")
    private Long course_id;

    @JoinColumn(name = "student_id")
    private Long student_id;

}
