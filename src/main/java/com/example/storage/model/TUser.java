package com.example.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "t_user")
public class TUser implements Serializable {

    private static final long serialVersionUID = 2749493794886279420L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "password", nullable = false)
    @Length(min = 5, message = "*您的密码必须至少包含5个字符")
    @NotEmpty(message = "*请提供您的密码")
    @JsonIgnore
    private String password;

    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 5, message = "*您的用户名必须至少包含5个字符")
    @NotEmpty(message = "*请提供您的用户名")
    private String username;

    @Column(name = "first_name")
    @NotEmpty(message = "*请提供您的名字")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "*请提供您的姓氏")
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<TNote> noteList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<TFile> fileList;
}
