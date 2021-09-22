package com.empik.githubadapter.dao;

import com.empik.githubadapter.entity.RequestCountEntity;
import com.empik.githubadapter.model.exception.CounterAlreadyExistsException;
import com.empik.githubadapter.repository.RequestCountEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestCounterDaoIntegrationTest {

    @Autowired
    private RequestCounterDao dao;

    @Autowired
    private RequestCountEntityRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void shouldThrowExceptionWhenTryingToInsertExistingCounter() {
        //given
        String login = "test";
        repository.save(new RequestCountEntity(login, 3));

        //when / then
        assertThrows(CounterAlreadyExistsException.class, () -> dao.insertNewCounter(login));
    }

    @Test
    void shouldInsertNewCounter() throws CounterAlreadyExistsException {
        //given
        String login = "test";

        //when
        dao.insertNewCounter(login);

        //then
        assertEquals(1, repository.count());
        RequestCountEntity entity = repository.findById(login).orElse(null);
        assertNotNull(entity);
        assertEquals(1, entity.getRequestCount());
    }

    @Test
    void shouldUpdateExistingCounter() {
        //given
        String login = "test";
        repository.save(new RequestCountEntity(login, 5));

        //when
        dao.updateCounter(login);

        //then
        assertEquals(1, repository.count());
        RequestCountEntity entity = repository.findById(login).orElse(null);
        assertNotNull(entity);
        assertEquals(6, entity.getRequestCount());
    }
}