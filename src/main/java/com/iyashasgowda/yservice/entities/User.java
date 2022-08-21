package com.iyashasgowda.yservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 16, nullable = false)
    private String username;

    @Column(length = 50)
    private String full_name;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 10)
    private String mobile;

    @Column(length = Integer.MAX_VALUE)
    private String password;

    @Column(length = Integer.MAX_VALUE)
    private String image_url;

    @Column(nullable = false)
    private long uploads = 0;

    @Column(nullable = false)
    private long likes = 0;

    @Column(nullable = false, updatable = false)
    private long created_on = System.currentTimeMillis();

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private boolean active = true;
}
