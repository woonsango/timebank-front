package com.capstone.timepay.domain.donateBoard;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DonateBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    private String type;
    private Integer targetTimePay; // 목표 타임페이
    private Integer donateTimePay; // 기부받은 타임페이
    @Column(nullable = false)
    private String category;
}
