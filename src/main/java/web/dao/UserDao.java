package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        if (user.getId() != null) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    public void deleteById(long id) {
        User user = findById(id).orElseThrow(() ->
                new IllegalArgumentException("Item id: " + id + " not found for deleting"));
        entityManager.remove(user);
    }

    public Optional<User> findById(long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user;
    }

    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e", User.class);
        return (List<User>) query.getResultList();
    }
}
