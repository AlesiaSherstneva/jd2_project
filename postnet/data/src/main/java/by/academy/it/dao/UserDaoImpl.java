package by.academy.it.dao;

import by.academy.it.pojo.User;
import by.academy.it.pojo.UserDetails;
import by.academy.it.pojo.UserJob;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void setUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> lookingForUser = criteria.from(User.class);
        criteria.select(lookingForUser);
        criteria.where(builder.equal(lookingForUser.get("email"), email));

        return session.createQuery(criteria).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);

        return session.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchUsers(String searchString) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaQuery<User> criteria = getUserCriteriaQuery(searchString, session);

        return session.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> pageUsers(String searchString, int page) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaQuery<User> criteria = getUserCriteriaQuery(searchString, session);

        return session.createQuery(criteria)
                .setFirstResult(page*3)
                .setMaxResults(3)
                .getResultList();
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
    @Transactional
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    @Transactional
    public void updateUserJob(UserJob userJob) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userJob);
    }

    @Override
    @Transactional
    public void updateUserDetails(UserDetails userDetails) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userDetails);
    }

    @Override
    @Transactional
    public void updateUserStatus(Byte enabled, Integer id) {
        Session session = sessionFactory.getCurrentSession();

        User updatingUser = session.get(User.class, id);
        updatingUser.setEnabled(enabled);
        session.update(updatingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.find(User.class, id));
    }
}
