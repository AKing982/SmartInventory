package org.example.smartinventory.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name="users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserEntity
{
    @Id
    @Column(name="userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="username")
    private String username;

    @Column(name="password", unique=true)
    private String password;

    @Column(name="is_active")
    private boolean is_active;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="userid"),
            inverseJoinColumns = @JoinColumn(name="role_Id")
    )
    private Set<Integer> roles = new HashSet<>();


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private EmployeeEntity employee;


}
