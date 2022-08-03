package by.academy.it.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_job")
public class UserJob implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_job_id")
    private Integer id;

    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "postoffice")
    private String postoffice;

    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "role")
    private String role;

    public UserJob(String postoffice, String role) {
        this.postoffice = postoffice;
        this.role = role;
    }
}
