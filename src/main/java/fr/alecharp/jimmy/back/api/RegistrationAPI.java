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

package fr.alecharp.jimmy.back.api;

import fr.alecharp.jimmy.back.model.Account;
import fr.alecharp.jimmy.back.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(
      value = "/api/auth"
)
public class RegistrationAPI {
    private final AccountService accountService;

    public RegistrationAPI(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/register")
    public Mono<Account> registration(@RequestBody @Valid Account account) {
        return Mono.justOrEmpty(accountService.create(account));
    }
}