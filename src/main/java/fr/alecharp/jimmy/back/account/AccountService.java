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

package fr.alecharp.jimmy.back.account;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AccountService {
    private final AccountRepository accountRepository;

    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    Optional<Account> save(Account account) {
        return Optional.of(accountRepository.save(account));
    }

    Optional<Account> byId(String id) {
        return accountRepository.findById(id);
    }

    Iterable<Account> all() {
        return accountRepository.findAll();
    }

    boolean knows(String email) {
        return accountRepository.existsByEmail(email);
    }
}
