package ru.schedule.models;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name = "CoursesProfessors")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoursesProfessors implements CommonEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "course_id")
    private Long course_id;

    @Column(name = "professor_id")
    private Long professor_id;

}
