package com.example.book.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_USERS")
public class User {
    // 对于主键，还需要用@Id标识，自增主键再追加一个@GeneratedValue，以便Hibernate能读取到自增主键的值。
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    // nullable指示列是否允许为NULL，updatable指示该列是否允许被用在UPDATE语句，length指示String类型的列的长度（如果没有指定，默认是255）。
    //@Column(nullable = false, updatable = false)
    @Column(name = "USER_NAME")
    @Size(max = 20, min = 2, message = "{用户名长度请输入2到20}")
    @NotEmpty(message = "Please Enter your name")
    private String name;
    @Column(name = "USER_EMAIL", unique = true)
    @Email(message = "{user email is invalid}")
    @NotEmpty(message = "Please Enter your email")
    private String email;
    // 饿汉式，当我们需要为大多数用例获取关联的集合时，这是一种针对特定用途的妥协解决方案。
    // 还可以在JPQL中使用JOIN FETCH指令来按需获取关联的集合： SELECT u FROM User u JOIN FETCH u.roles
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "DETAILS_ID")
    private UserDetails details;
    // 自关联
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "editor_id")
    private User editor;
    @Column(name = "editor_id", insertable = false, updatable = false)
    private Long editorId;
    // 双自我依赖测试
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id")
    private User creator;
    @Column(name = "creator_id", insertable = false, updatable = false)
    private Long creatorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long editorId) {
        this.editorId = editorId;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}