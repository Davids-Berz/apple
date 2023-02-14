package com.apple.apple.models.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})

public class Role implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
