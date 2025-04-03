package ru.kata.spring.boot_security.demo.repositories;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void add(User user) {
        entityManager.persist(user);

    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user, User existingUser) {
        //entityManager.merge(user);
        existingUser = entityManager.find(User.class, user.getId());
        if(user.getName() !=null) {
            existingUser.setName(user.getName());
        }
        if(user.getLastName() !=null) {
            existingUser.setLastName(user.getLastName());
        }
        if(user.getAge() > 0) {
            existingUser.setAge(user.getAge());
        }
        if(user.getAddress() !=null) {
            existingUser.setAddress(user.getAddress());
        }
        if(user.getUsername() !=null) {
            existingUser.setUsername(user.getUsername());
        }
            existingUser.setEnabled(user.isEnabled());


    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

//    @Override
//    public User findByUsername(String username) {
//        try {
//            return entityManager.createQuery(
//                            "SELECT u FROM User u WHERE u.username = :username", User.class)
//                    .setParameter("username", username)
//                    .getSingleResult();
//        } catch (NoResultException e) {
//            return null;
//        }
//    }
}


