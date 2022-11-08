package by.academy.it.pojo;

import by.academy.it.validators.Password;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // публичный профиль, первая страница

    @NotNull(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 15, message = "Введи от 2 до 15 символов")
    @Pattern(regexp = "^[A-ZА-ЯЁ][a-zA-Zа-яА-ЯёЁ\\-]+$",
            message = "Имя должно начинаться с прописной буквы и состоять из строчных букв")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Это поле не может быть пустым")
    @Size(min = 2, max = 20, message = "Введи от 2 до 20 символов")
    @Pattern(regexp = "^[A-ZА-ЯЁ][a-zA-Zа-яА-ЯёЁ\\-]+$",
            message = "Фамилия должна начинаться с прописной буквы и состоять из строчных букв")
    @Column(name = "surname")
    private String surname;

    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "gender")
    private String gender;

    @NotNull(message = "Это поле не может быть пустым")
    @Email(message = "Введи корректный e-mail")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Это поле не может быть пустым")
    @Password
    @Column(name = "password")
    private String password;

    // публичный профиль, вторая страница

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_job_id")
    private UserJob userJob;

    // приватный профиль

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_details_id")
    private UserDetails userDetails;

    // для настройки security и администрирования

    @Column(name = "authority")
    private String authority = "REGISTERED";

    @Column(name = "enabled")
    private Byte enabled = 1;

    public User(String name, String surname, String gender, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }
}
