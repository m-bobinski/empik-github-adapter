package com.empik.githubadapter.dao;


import com.empik.githubadapter.constants.SqlConstants;
import com.empik.githubadapter.entity.RequestCountEntity;
import com.empik.githubadapter.model.exception.CounterAlreadyExistsException;
import com.empik.githubadapter.repository.RequestCountEntityRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Component
@RequiredArgsConstructor
public class RequestCounterDao {

    @PersistenceContext
    private final EntityManager entityManager;
    private final RequestCountEntityRepository repository;

    /**
     * Returns information if given login already has a counter in the database
     *
     * @param login Requested login
     * @return login already exists
     */
    public boolean counterExists(String login) {
        return repository.existsById(login);
    }

    /**
     * Updates requested login's counter
     *
     * @param login Requested login
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void updateCounter(String login) {
        Query query = entityManager.createNativeQuery(SqlConstants.UPDATE_QUERY);
        query.setParameter("login", login);
        query.executeUpdate();
    }

    /**
     * Inserts counter for a new login
     *
     * @param login Requested login
     * @throws CounterAlreadyExistsException if counter was already created after checking if it exists
     */
    @Transactional(rollbackFor = CounterAlreadyExistsException.class)
    public void insertNewCounter(String login) throws CounterAlreadyExistsException {
        try {
            RequestCountEntity entity = new RequestCountEntity(login, 1);
            entityManager.persist(entity);
            entityManager.flush();
        } catch (PersistenceException e) {
            //in case that an entity for this login was created in a different thread
            if (e.getCause() != null && e.getCause().getClass().equals(ConstraintViolationException.class)) {
                throw new CounterAlreadyExistsException("Counter for this login already exists");
            }
        }
    }
}
