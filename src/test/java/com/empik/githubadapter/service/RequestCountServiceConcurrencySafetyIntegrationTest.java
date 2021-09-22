package com.empik.githubadapter.service;

import com.empik.githubadapter.dao.RequestCounterDao;
import com.empik.githubadapter.entity.RequestCountEntity;
import com.empik.githubadapter.model.exception.CounterAlreadyExistsException;
import com.empik.githubadapter.repository.RequestCountEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RequestCountServiceConcurrencySafetyIntegrationTest {

    @Autowired
    private RequestCountEntityRepository repository;

    @Autowired
    private RequestCountService service;

    @MockBean
    private RequestCounterDao requestCounterDao;

    @BeforeEach
    public void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldUpdateCounterInsteadOfInsertingExistingOne() throws Exception {
        //given
        String login = "testlogin";
        repository.save(new RequestCountEntity(login, 1));
        Mockito.doThrow(new CounterAlreadyExistsException()).when(requestCounterDao).insertNewCounter(login);

        //when
        service.processCounter(login);

        //then
        assertEquals(1, repository.count());
        Mockito.verify(requestCounterDao, Mockito.times(1)).updateCounter(login);
    }

}