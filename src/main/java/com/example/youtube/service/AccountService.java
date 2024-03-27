package com.example.youtube.service;

import com.example.youtube.model.Account;
import com.example.youtube.model.Video;
import com.example.youtube.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AccountService implements IAccountService{
    @Autowired
   private IAccountRepository iAccountRepository;
    @Override
    public Iterable<Account> findAll() {
        return iAccountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
    public Iterable<Account> findAllByNameContaining(String username){
        return iAccountRepository.findAllByUserNameContaining(username);
    }
}
