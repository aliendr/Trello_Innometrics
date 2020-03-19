package com.example.trello.services;

import com.example.trello.entries.Authentication;
import com.example.trello.repositories.AuthenticationRepository;
import com.example.trello.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public void setToken(String token, String key){
        authenticationRepository.save(new Authentication(token,key));
    }

}

