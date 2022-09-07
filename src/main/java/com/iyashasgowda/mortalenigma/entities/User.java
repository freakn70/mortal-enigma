package com.iyashasgowda.mortalenigma.entities;

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

    private String full_name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 10)
    private String mobile;

    private String password;

    private String image_url;

    private long uploads = 0;

    private long likes = 0;

    @Column(updatable = false)
    private long created_on = System.currentTimeMillis();

    private boolean verified = false;

    private boolean active = true;
}