package com.example.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "t_file")
public class TFile implements Serializable {

    private static final long serialVersionUID = -6499207403641470328L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "filename", nullable = false)
    @NotEmpty(message = "*请提供文件名")
    @JsonIgnore
    private String filename;

    @Column(name = "object_name", nullable = false)
    private String objectName;

    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @Column(name = "file_size")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private TUser user;
}
