package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void deleteById(long id){
        userDao.deleteById(id);
    }

    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }
}
