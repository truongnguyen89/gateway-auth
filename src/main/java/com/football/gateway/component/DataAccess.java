package com.football.gateway.component;

import com.football.gateway.model.Token;
import com.football.gateway.model.User;
import com.football.gateway.repository.TokenRepository;
import com.football.gateway.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataAccess.class);

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    ExecutorService executorService;
    private int numThreads = 10;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(numThreads);
    }

    public void saveToken(Token token) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                tokenRepository.save(token);
                try {
                } catch (Exception e) {
                    LOGGER.error("Exception saveToken ", e);
                }
            }
        });
    }

    public void saveUser(User user) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                userRepository.save(user);
                try {
                } catch (Exception e) {
                    LOGGER.error("Exception saveToken ", e);
                }
            }
        });
    }
}
