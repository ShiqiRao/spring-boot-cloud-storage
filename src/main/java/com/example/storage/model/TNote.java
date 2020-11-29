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
@Table(name = "t_note")
public class TNote implements Serializable {

    private static final long serialVersionUID = 3706652240480176782L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @Column(name = "note_title", nullable = false)
    @NotEmpty(message = "*请提供笔记标题")
    @JsonIgnore
    private String noteTitle;

    @Column(name = "note_description")
    @NotEmpty(message = "*请提供笔记描述")
    @JsonIgnore
    private String noteDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private TUser user;
}
