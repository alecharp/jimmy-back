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

import fr.alecharp.jimmy.back.api.model.AccountPasswordRequest;
import fr.alecharp.jimmy.back.api.model.AccountUpdateRequest;
import fr.alecharp.jimmy.back.model.Account;
import fr.alecharp.jimmy.back.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.security.Principal;

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
    @PreAuthorize(value = "hasRole('ADMIN')")
    public Flux<Account> all() {
        return usersService.all().doOnNext(account -> account.setPassword(null));
    }

    @GetMapping(value = "/me")
    @PreAuthorize(value = "isAuthenticated()")
    public Mono<Account> me(@AuthenticationPrincipal Mono<Principal> principal) {
        return principal
              .map(Principal::getName)
              .flatMap(usersService::findByEmail)
              .doOnNext(account -> account.setPassword(null));
    }

    @PutMapping(value = "/me")
    @PreAuthorize(value = "isAuthenticated()")
    public Mono<Account> update(@RequestBody AccountUpdateRequest account,
                                @AuthenticationPrincipal Mono<Principal> principal) {
        return principal
              .map(Principal::getName)
              .flatMap(usersService::findByEmail)
              .map(user -> user.setFirstName(account.getFirstName()).setLastName(account.getLastName()))
              .flatMap(usersService::save)
              .map(user -> user.setPassword(null));
    }

    @PutMapping(value = "/me/password")
    @PreAuthorize(value = "isAuthenticated()")
    public Mono<Account> updatePassword(@RequestBody AccountPasswordRequest password,
                                        @AuthenticationPrincipal Mono<Principal> principal) {
        return principal
              .map(Principal::getName)
              .flatMap(usersService::findByEmail)
              .map(user -> user.setPassword(password.getPassword()))
              .flatMap(usersService::updatePassword);
    }
}
