package com.iyashasgowda.yservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iyashasgowda.yservice.utilities.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;

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

    @Column(nullable = false)
    private long duration = 0;

    @Column(nullable = false)
    private MediaType type;

    @Column(length = 10)
    private String format;

    @Column(nullable = false)
    private Integer width = 0;

    @Column(nullable = false)
    private Integer height = 0;

    @Column(length = 512, nullable = false)
    private String url;

    @Column(length = 512)
    private String thumbnail;

    @Column(nullable = false)
    private long views = 0;

    @Column(nullable = false)
    private long likes = 0;

    @Column(nullable = false, updatable = false)
    private long created_on = System.currentTimeMillis();
}
