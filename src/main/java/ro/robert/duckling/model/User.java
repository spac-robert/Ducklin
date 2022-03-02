package ro.robert.duckling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "schema")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email can't be blank")
    @Email
    private String email;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Username can't be blank")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password can't be blank")
    private String password;

    @Column(name = "instant", nullable = false)
    private Instant created;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

}
