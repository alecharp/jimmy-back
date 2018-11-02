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
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Is responsible for the manipulation of one specific event.
 */
@RestController
@RequestMapping(
      value = "/api/events",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class EventAPI {
    private final EventService service;

    public EventAPI(EventService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_EVENT_PLANNER')")
    @PutMapping("/{id}")
    public Mono<Event> update(
          @AuthenticationPrincipal KeycloakAuthenticationToken user,
          @PathVariable String id,
          @RequestBody EventCreationRequest request) {
        return Mono.justOrEmpty(
              service.getEventById(id)
                    .filter(event -> event.getOwners().contains(user.getName()))
                    .map(event -> event.setName(request.getName()).setDate(request.getDate()))
                    .flatMap(service::update)
        );
    }
}
