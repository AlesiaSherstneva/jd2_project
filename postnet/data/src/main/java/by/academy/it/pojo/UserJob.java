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
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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
