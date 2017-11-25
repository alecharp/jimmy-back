/*
 * Copyright 2017 Jimmy, Adrien Lecharpentier and others
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
package fr.alecharp.jimmy.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.UUID;

@SpringBootApplication
public class JimmyBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(JimmyBackApplication.class, args);
    }

    @RestController
    @RequestMapping(value = "/api/events")
    public static class EventAPI {
        @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public Flux<UUID> event() {
            return Flux.generate(sink -> sink.next(UUID.randomUUID()))
                    .zipWith(Flux.interval(Duration.ofSeconds(1)))
                    .map(Tuple2::getT1)
                    .map(uuid -> (UUID) uuid);
        }
    }

    @RestController
    @RequestMapping(value = "/api/ping")
    public static class PingAPI {
        @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
        public String get() {
            return "Hello you..";
        }
    }
}
