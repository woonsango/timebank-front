package com.capstone.timepay.domain.notification;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.admin.Admin;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column
    private String title;
    private String imageUrl;
    private boolean isNotice;
    private String content;
    private boolean isViewed;


    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;
}
