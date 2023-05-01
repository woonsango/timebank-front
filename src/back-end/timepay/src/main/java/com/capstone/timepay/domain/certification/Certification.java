package com.capstone.timepay.domain.certification;

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
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationId;

    @Column
    private String certificationUrl;
    private int downloadCount;
    private boolean isPublished;

    @OneToMany(mappedBy = "certification", orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
