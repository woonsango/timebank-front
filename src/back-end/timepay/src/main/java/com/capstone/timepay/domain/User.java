package com.capstone.timepay.domain;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class User {

    private Long user_id;
    private String name;
    private String sex;
    private LocalDateTime birthday;
    private String region;
    
}
