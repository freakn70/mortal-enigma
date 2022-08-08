package com.iyashasgowda.yservice.entities;

import com.iyashasgowda.yservice.utilities.MediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 18, nullable = false)
    private String identifier;

    @Column(nullable = false)
    @Nationalized
    private String filename;

    private long size;

    @Column(length = 512, nullable = false)
    private String url;

    @Column(nullable = false)
    private MediaType type;
}
