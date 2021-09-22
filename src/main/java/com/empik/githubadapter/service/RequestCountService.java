package com.empik.githubadapter.service;

import com.empik.githubadapter.dao.RequestCounterDao;
import com.empik.githubadapter.model.exception.CounterAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RequestCountService {

    private final RequestCounterDao requestCounterDao;

    /**
     * Processes the request counter for given login
     *
     * @param login Requested login
     */
    public void processCounter(String login) {
        if (requestCounterDao.counterExists(login)) {
            requestCounterDao.updateCounter(login);
        } else {
            try {
                requestCounterDao.insertNewCounter(login);
            } catch (CounterAlreadyExistsException e) {
                requestCounterDao.updateCounter(login);
            }
        }
    }

}
