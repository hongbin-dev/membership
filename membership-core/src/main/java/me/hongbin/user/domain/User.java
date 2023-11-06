package me.hongbin.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import me.hongbin.generic.type.BaseTimeEntity;

@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long id() {
        return id;
    }

    public String getName() {
        return name;
    }
}
