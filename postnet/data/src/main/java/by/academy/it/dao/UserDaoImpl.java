package by.academy.it.dao;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import by.academy.it.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session;


    @Override
    public void setUser(User user) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User getUser(Integer id) {
        session = sessionFactory.openSession();

        User user = session.find(User.class, id);

        session.close();
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> lookingForUser = criteria.from(User.class);
        criteria.select(lookingForUser);
        criteria.where(builder.equal(lookingForUser.get("email"), email));
        User user = session.createQuery(criteria).getSingleResult();

        session.close();
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        List<User> allUsers = session.createQuery(criteria).getResultList();

        session.close();
        return allUsers;
    }

    @Override
    public List<User> searchUsers(String searchString) {
        Session session = sessionFactory.openSession();

        CriteriaQuery<User> criteria = getUserCriteriaQuery(searchString, session);

        List<User> likeUsers = session.createQuery(criteria).getResultList();
        session.close();
        return likeUsers;
    }

    @Override
    public List<User> pageUsers(String searchString, int page) {
        Session session = sessionFactory.openSession();

        CriteriaQuery<User> criteria = getUserCriteriaQuery(searchString, session);

        List<User> pageUsers = session.createQuery(criteria)
                .setFirstResult(page*3)
                .setMaxResults(3)
                .getResultList();
        session.close();
        return pageUsers;
    }

    //вспомогательный метод, чтобы не повторять один и тот же код в предыдущих двух методах
    
    private CriteriaQuery<User> getUserCriteriaQuery(String searchString, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> lookingForUsers = criteria.from(User.class);
        criteria.select(lookingForUsers);
        Predicate predicate = builder.or(
                builder.like(
                        builder.lower(lookingForUsers.get("name")),
                        builder.lower(builder.literal("%" + searchString.trim() + "%"))),
                builder.like(
                        builder.lower(lookingForUsers.get("surname")),
                        builder.lower(builder.literal("%" + searchString.trim() + "%"))),
                builder.like(
                        builder.lower(lookingForUsers.get("userJob").get("postoffice")),
                        builder.lower(builder.literal("%" + searchString.trim() + "%"))),
                builder.like(
                        builder.lower(lookingForUsers.get("userJob").get("role")),
                        builder.lower(builder.literal("%" + searchString.trim() + "%")))
        );

        criteria.where(predicate);
        criteria.orderBy(builder.asc(lookingForUsers.get("surname")), builder.asc(lookingForUsers.get("surname")));
        return criteria;
    }

    @Override
    public void updateUser(User user) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUserJob(UserJob userJob) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(userJob);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUserDetails(UserDetails userDetails) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(userDetails);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateUserStatus(Byte enabled, Integer id) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        User updatingUser = session.get(User.class, id);
        updatingUser.setEnabled(enabled);
        session.update(updatingUser);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteUser(Integer id) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        User deletingUser = session.find(User.class, id);
        session.delete(deletingUser);

        session.getTransaction().commit();
        session.close();
    }
}
