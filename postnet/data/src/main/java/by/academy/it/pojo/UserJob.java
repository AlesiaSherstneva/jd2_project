package by.academy.it.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.*;
import java.util.LinkedHashMap;

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

    @Transient
    private final LinkedHashMap<String, Integer> postoffices;

    @NotNull(message = "Это поле не может быть пустым")
    @Column(name = "role")
    private String role;

    public UserJob() {

        //создаётся список почтовых отделений для формы регистрации

        postoffices = new LinkedHashMap<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/postnet",
                    "root", "root");
            String query = "SELECT name FROM postoffice";
            Statement statement = connection.createStatement();
            statement.executeQuery(query);
            ResultSet firstResultSet = statement.getResultSet();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("SELECT number FROM postoffice WHERE name = ?");
            while (firstResultSet.next()) {
                String name = firstResultSet.getString("name");
                preparedStatement.setString(1, name);
                ResultSet secondResultSet = preparedStatement.executeQuery();
                while (secondResultSet.next()) {
                    Integer number = secondResultSet.getInt("number");
                    postoffices.put(name, number);
                }
            }
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
