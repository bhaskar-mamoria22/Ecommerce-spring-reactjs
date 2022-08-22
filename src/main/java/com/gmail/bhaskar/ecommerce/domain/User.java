package com.gmail.bhaskar.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "username", "activationCode"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    /**
     * The unique code of the object.
     * The @Id annotation says that the field is the key for the current object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User name.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Username cannot be empty")
    private String username;

    /**
     * User password for logging into account on the site.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;

    /**
     * User email.
     * The @Email annotation says the string has to be a well-formed email address.
     * The @NotBlank annotation says the field should not be empty.
     */
    @Email(message = "Incorrect email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    /**
     * Provides access to the site if the user has confirmed the activation code on his email.
     */
    private boolean active;

    /**
     * Activation code that is sent to the user's email.
     */
    private String activationCode;

    /**
     * Password reset code that is sent to the user's email.
     */
    private String passwordResetCode;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfume> perfumeList;

    /**
     * Method for verifying a user with administrator rights.
     */
    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return isActive();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
}