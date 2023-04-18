package com.example.autogradertyp.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represent the user table in the database.
 *
 * @author Fahd Alsahali
 * @version 1.0
 * @date 25/01/2023
 */


@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {


    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;


    private String username;

    @Column(name = "password", columnDefinition = "LONGTEXT")
    private String password;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    private String email;

    private String role;

    private String studentNumber;

    @OneToMany(mappedBy = "user")
    private List<Submission> Submissions;

    @OneToMany(mappedBy = "author")
    private List<Assignment> assignments;

    /**
     * A method to register a new user
     *
     * @param username         The username of the user
     * @param password         The encrypted password of the user
     * @param accountNonLocked To indicates if the account is locked
     * @param email            The email of the user
     * @param role             The role of the user
     * @param studentNumber    The university id of the user
     */
    public User(String username, String password, boolean accountNonLocked,
                String email, String role, String studentNumber) {
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

    /**
     * A method that gets the user encrypted password
     *
     * @return The encrypted password of the user
     */
    @Override
    public String getPassword() {

        return password;
    }

    /**
     * A method that sets the password of the user
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * A method to get the username of the user
     *
     * @return The username of the user
     */
    @Override
    public String getUsername() {

        return username;
    }

    /**
     * A method that sets the username of the user
     *
     * @param username The username of the user
     */
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

    public void setAccountNonLocked(Boolean accountNonLocked) {

        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    /**
     * A method that gets the email of the user
     *
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * A method that sets the email of the user
     *
     * @param email The email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * A method that gets the role of the user
     *
     * @return The role of the user
     */
    public String getRole() {
        return role;
    }

    /**
     * A method that sets the role of the user
     *
     * @param role The role of the user
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * A method that gets the university id
     *
     * @return The university id of the user
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * A method that sets the university id of the user
     *
     * @param studentNumber The university id of the user
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
