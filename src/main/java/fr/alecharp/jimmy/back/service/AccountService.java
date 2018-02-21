/*
 * Copyright 2017 Adrien Lecharpentier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.alecharp.jimmy.back.service;

import fr.alecharp.jimmy.back.model.Account;
import fr.alecharp.jimmy.back.model.Role;
import fr.alecharp.jimmy.back.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Account> save(Account account) {
        return Optional.of(accountRepository.save(account));
    }

    public Optional<Account> updatePassword(Account account) {
        return Optional.of(accountRepository.save(encrypt(account)));
    }

    private Account encrypt(Account account) {
        return account.setPassword(passwordEncoder.encode(account.getPassword()));
    }

    public Optional<Account> byId(String id) {
        return accountRepository.findById(id);
    }

    public Iterable<Account> all() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Optional<Account> create(Account account) {
        return this.save(
              encrypt(account)
                    .setRoles(Role.USER)
                    .setRegistrationDate(ZonedDateTime.now(ZoneOffset.UTC))
                    .setActivated(false)
        );
    }
}
