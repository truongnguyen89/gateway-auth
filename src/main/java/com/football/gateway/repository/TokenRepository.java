package com.football.gateway.repository;

import com.football.gateway.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 *
 * @author : truongnq
 * @Date time: 2018-12-26 21:06
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
