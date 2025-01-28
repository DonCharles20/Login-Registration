package com.don.login_registration.appuser;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/** this is the AppUser Class that implements the UserDetails Interface
 * contains the following attributes
 *  Long Id to keep track of User accounts in the database
 *  String firstName and String lastName of User accounts
 *  String email to use as usernames
 *  String Passwords to be Encoded and used as keys for user accounts
 *  AppUserRole appUserRole to keep differentiate between normal accounts and admin accounts
 *  which is also an Enum with two roles USER,ADMIN
 *  two boolean variables locked and enabled to keep tracked of locked and enabled accounts.
 *  Several annotations are used such as @Entity and @Table to map onto the table
 * @Data is used to reduce boilerplate codes of setters,getters and constructors
 * Both @SequenceGenerator and @GeneratedValue to create a sequence of ID's
 * The UserDetails interface is springSecurity for when users are loging in.
 * used to Validate credentials and check account roles
 * */
@Entity
@Table(name = "app_user")
@Data
public class AppUser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName="user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="user_sequence"
    )
    private Long Id;
    
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked;
    private Boolean enabled;

    public AppUser(String firstName, String lastName, String email, String password, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
