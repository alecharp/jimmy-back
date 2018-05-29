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

import fr.alecharp.jimmy.back.model.Event;
import fr.alecharp.jimmy.back.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/events")
public class EventAPI {
    private final EventService eventService;

    public EventAPI(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = {"", "/"})
    public List<Event> events() {
        return eventService.getAllEvents();
    }

    @GetMapping(path = "/{id}")
    public Optional<Event> event(@PathVariable String id) {
        return eventService.byId(id);
    }
}
