package com.football.gateway.component;

import com.football.common.constant.Constant;
import com.football.common.model.auth.Token;
import com.football.common.model.email.Email;
import com.football.common.model.user.User;
import com.football.common.repository.EmailRepository;
import com.football.common.repository.TokenRepository;
import com.football.common.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataAccess {
    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.APPLICATION);

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailRepository emailRepository;

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

    public void saveEmail(Email email) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                emailRepository.save(email);
                try {
                } catch (Exception e) {
                    LOGGER.error("Exception saveEmail ", e);
                }
            }
        });
    }
}
