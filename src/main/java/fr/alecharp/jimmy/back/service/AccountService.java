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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<Account> save(Account account) {
        return Mono.justOrEmpty(accountRepository.save(account));
    }

    public Mono<Account> updatePassword(Account account) {
        return Mono.justOrEmpty(accountRepository.save(encrypt(account)));
    }

    private Account encrypt(Account account) {
        return account.setPassword(passwordEncoder.encode(account.getPassword()));
    }

    public Mono<Account> byId(String id) {
        return Mono.justOrEmpty(accountRepository.findById(id));
    }

    public Flux<Account> all() {
        return Flux.fromStream(accountRepository.streamAll());
    }

    public Mono<Account> findByEmail(String email) {
        return Mono.justOrEmpty(accountRepository.findByEmail(email));
    }

    public Mono<Account> create(Account account) {
        return this.save(
              encrypt(account)
                    .setRoles(Role.USER)
                    .setRegistrationDate(ZonedDateTime.now(ZoneOffset.UTC))
                    .setActivated(false)
        );
    }
}
