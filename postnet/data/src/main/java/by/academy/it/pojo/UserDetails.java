package by.academy.it.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
public class UserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Past(message = "Дата должна быть меньше текущей")
    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "about")
    private String about;

    @Column(name = "hobby")
    private String hobby;

    public UserDetails(Date birthday, String about, String hobby) {
        this.birthday = birthday;
        this.about = about;
        this.hobby = hobby;
    }
}
