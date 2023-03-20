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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Courses course_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Students student_id;

}
