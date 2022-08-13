package com.iyashasgowda.yservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iyashasgowda.yservice.utilities.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Media implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, length = 18, nullable = false)
    private String filename;

    @Column(length = Integer.MAX_VALUE, nullable = false)
    @Nationalized
    private String title;

    @Column(nullable = false)
    private long size = 0;

    @Column(length = 512, nullable = false)
    private String url;

    @Column(nullable = false)
    private MediaType type;

    @Column(nullable = false)
    private long views = 0;

    @Column(nullable = false)
    private long likes = 0;

    @Column(nullable = false)
    @CreationTimestamp
    private Date created_on;

    public Media(String filename, User user_id, String title, long size, String url, MediaType type) {
        this.filename = filename;
        this.user = user_id;
        this.title = title;
        this.size = size;
        this.url = url;
        this.type = type;
    }
}
