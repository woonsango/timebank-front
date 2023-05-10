package com.capstone.timepay.domain.certification;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.inquiry.Inquiry;
import com.capstone.timepay.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Certification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    @Column
    private String certificationUrl;
    private int downloadCount;
    private boolean isPublished;
    private Long dealBoardId;
    private int time;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public void updateCertificationUrl(String url){
        this.certificationUrl = url;
    }
    public void updateIsPublished(boolean state){
        this.isPublished = state;
    }
}
