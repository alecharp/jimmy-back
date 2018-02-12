/*
 * Copyright 2018 Adrien Lecharpentier
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

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DBUserDetailsService implements ReactiveUserDetailsService {
    private final AccountService accountService;

    public DBUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.justOrEmpty(
              accountService.findByEmail(username)
                    .map(account ->
                          new User(
                                account.getEmail(),
                                account.getPassword(),
                                account.isActivated(),
                                true,
                                true,
                                true,
                                account.getRoles().stream().map(
                                      role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList()
                                )
                          )
                    )
        );
    }
}
