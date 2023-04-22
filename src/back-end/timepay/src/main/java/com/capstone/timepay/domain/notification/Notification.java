package com.capstone.timepay.domain.notification;

import com.capstone.timepay.domain.BaseTimeEntity;
import com.capstone.timepay.domain.admin.Admin;
import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column
    private String title;
    private String imageUrl;
    private boolean isNotice;
    private String content;


    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;
}
