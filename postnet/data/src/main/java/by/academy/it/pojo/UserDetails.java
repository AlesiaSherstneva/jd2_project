package by.academy.it.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_details_id")
    private Integer id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Past(message = "Дата должна быть меньше текущей")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "about")
    private String about;

    @Column(name = "hobby")
    private String hobby;

}
