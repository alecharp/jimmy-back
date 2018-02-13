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

package fr.alecharp.jimmy.back.api;

import fr.alecharp.jimmy.back.model.Account;
import fr.alecharp.jimmy.back.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(
      value = "/api/users",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
)
public class AccountAPI {
    private final AccountService usersService;

    public AccountAPI(AccountService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public Flux<Account> all() {
        return Flux.fromIterable(usersService.all());
    }

    @GetMapping(value = "/{id}")
    public Mono<Account> one(@PathVariable String id) {
        return Mono.justOrEmpty(usersService.byId(id));
    }

    @PostMapping(value = "/{id}")
    public Mono<Account> update(@PathVariable String id, @RequestBody @Valid Account account) {
        return id.equals(account.getId()) ? Mono.justOrEmpty(usersService.save(account)) : Mono.empty();
    }
}
