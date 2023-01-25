package com.example.autogradertyp.data.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {


    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Column(name="password",columnDefinition="LONGTEXT")
    private String password;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    private String email;

    private String role;

    private String studentNumber;

    public User() {
    }
    public User(String username, String password, boolean accountNonLocked,
                String email,String role, String studentNumber) {
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return list;
    }

    @Override
    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getUsername() {

        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {

        return accountNonLocked;
    }
    @Override public boolean isCredentialsNonExpired() {

        return true;
    }
    @Override public boolean isEnabled() {

        return true;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {

        this.accountNonLocked = accountNonLocked;
    }
    public boolean getAccountNonLocked() {

        return accountNonLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
