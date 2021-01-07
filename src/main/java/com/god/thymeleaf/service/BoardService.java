package com.god.thymeleaf.service;

import com.god.thymeleaf.repository.BoardRepository;
import com.god.thymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import  com.god.thymeleaf.model.*;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String userName, Board board){
        User user=userRepository.findByUsername(userName);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
