package by.academy.it.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
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
}
