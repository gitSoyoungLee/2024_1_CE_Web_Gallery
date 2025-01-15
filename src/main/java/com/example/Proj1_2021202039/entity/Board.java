package com.example.Proj1_2021202039.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
public class Board {
    @Id
    @Column(name="id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 데이터별 아이디
    private long id;

    //제목
    @Column
    private String title;

    //내용
    @Column
    private String contents;

    //이미지 이름(경로)
    @Column
    private String image;

}
