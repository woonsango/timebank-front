package com.capstone.timepay.domain.category;

import com.capstone.timepay.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String boardType;
    private String categoryName;
    private String useYn;

    public void updateUseYn(String query){
        this.useYn = query;
    }

}
