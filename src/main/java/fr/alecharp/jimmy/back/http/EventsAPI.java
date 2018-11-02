/*
 * Copyright 2018 Jimmy, Adrien Lecharpentier and others
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

package fr.alecharp.jimmy.back.http;

import fr.alecharp.jimmy.back.http.model.EventCreationRequest;
import fr.alecharp.jimmy.back.model.Event;
import fr.alecharp.jimmy.back.service.EventService;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Is responsible for the manipulation of the events lists.
 */
@RestController
@RequestMapping(
      value = "/api/events",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class EventsAPI {
    private static final Logger LOG = LoggerFactory.getLogger(EventsAPI.class);
    private final EventService service;

    public EventsAPI(EventService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Event> get(@AuthenticationPrincipal KeycloakAuthenticationToken user) {
        LOG.debug("Getting events for {}", user.getName());
        return Flux.fromIterable(service.accessibleFor(user.getName()));
    }

    @PreAuthorize("hasRole('ROLE_EVENT_PLANNER') || hasRole('ROLE_ADMIN')")
    @PostMapping
    public Mono<Event> create(@AuthenticationPrincipal KeycloakAuthenticationToken user, @RequestBody EventCreationRequest request) {
        LOG.debug("Creating new event {} for {}", request.getName(), user.getName());
        return Mono.justOrEmpty(service.create(user.getName(), request.getName()));
    }
}
