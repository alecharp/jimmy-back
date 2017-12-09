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

package fr.alecharp.jimmy.back.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UsersService {
    private final UsersRepository usersRepository;

    UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    Optional<User> save(User user) {
        return Optional.of(usersRepository.save(user));
    }

    Optional<User> byId(String id) {
        return usersRepository.findById(id);
    }

    Iterable<User> all() {
        return usersRepository.findAll();
    }

    boolean knows(String email) {
        return usersRepository.existsByEmail(email);
    }
}
