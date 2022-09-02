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

    @Column(unique = true, nullable = false)
    private String filename;

    @Column(nullable = false)
    @Nationalized
    private String title;

    @Nationalized
    private String description;

    @Nationalized
    private String keywords;

    private long size = 0;

    private long duration = 0;

    @Column(nullable = false)
    private MediaType type;

    private String format;

    private Integer width = 0;

    private Integer height = 0;

    @Column(nullable = false)
    private String url;

    private String thumbnail;

    private long views = 0;

    private long likes = 0;

    private long comments = 0;

    private long reports = 0;

    @Column(updatable = false)
    private long created_on = System.currentTimeMillis();
}