package com.empik.githubadapter.service;

import com.empik.githubadapter.entity.RequestCountEntity;
import com.empik.githubadapter.repository.RequestCountEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RequestCountServiceIntegrationTest {

    @Autowired
    private RequestCountEntityRepository repository;

    @Autowired
    private RequestCountService service;

    @BeforeEach
    public void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldInsertNewRecord() {
        //given
        String login = "testlogin";

        //when
        service.processCounter(login);

        //then
        assertEquals(1, repository.count());
        RequestCountEntity entity = repository.findById(login).orElse(null);
        assertNotNull(entity);
        assertEquals(1, entity.getRequestCount());
    }

    @Test
    void shouldUpdateExistingRecord() {
        //given
        String login = "testlogin";
        repository.save(new RequestCountEntity(login, 1));

        //when
        service.processCounter(login);

        //then
        assertEquals(1, repository.count());
        RequestCountEntity entity = repository.findById(login).orElse(null);
        assertNotNull(entity);
        assertEquals(2, entity.getRequestCount());
    }

}