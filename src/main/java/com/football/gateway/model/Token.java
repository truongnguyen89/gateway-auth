package com.football.gateway.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: truongnq
 * Date: 2018-12-16
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "token")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "n_id")
    private Long id;
    @Column(name = "n_user_id")
    private Long userId;
    @Column(name = "s_token")
    private String token;
    @Column(name = "n_type")
    private Integer type;
    @Column(name = "n_status")
    private Integer status;
    @Column(name = "d_expiration_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date expirationAt;
    @Column(name = "d_created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    @Column(name = "d_updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Date updatedAt;

    public Token() {
    }

    public Token(Long userId, String token, Date expirationAt, Date createdAt) {
        this.userId = userId;
        this.token = token;
        this.status = 1;
        this.expirationAt = expirationAt;
        this.createdAt = createdAt;
    }

    public Token(Long userId, String token, long expiration) {
        this.userId = userId;
        this.token = token;
        this.status = 1;
        Date date = new Date();
        this.createdAt = date;
        this.expirationAt = new Date(date.getTime() + expiration);
    }

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = new Date();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getExpirationAt() {
        return expirationAt;
    }

    public void setExpirationAt(Date expirationAt) {
        this.expirationAt = expirationAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
