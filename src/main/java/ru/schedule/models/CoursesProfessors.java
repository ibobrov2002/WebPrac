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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    private Courses course_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor_id")
    @ToString.Exclude
    private Professors professor_id;

}
