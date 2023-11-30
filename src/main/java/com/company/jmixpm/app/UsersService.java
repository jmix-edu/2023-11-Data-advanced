package com.company.jmixpm.app;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UsersService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<User> getUsersNotInProject(Project project, int firstResult, int maxResult) {
        return em.createQuery("SELECT u FROM User u " +
                        "WHERE u.id NOT IN " +
                        "(SELECT u1.id FROM User u1 " +
                        " INNER JOIN u1.projects pul " +
                        " WHERE pul.id = ?1)", User.class)
                .setParameter(1, project.getId())
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }
}