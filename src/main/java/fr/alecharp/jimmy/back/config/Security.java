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

package fr.alecharp.jimmy.back.config;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class Security {
    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        //@formatter:off
        return http
          .authorizeExchange()
            .matchers(EndpointRequest.to("info", "health")).permitAll()
            .pathMatchers("/api/auth/register").permitAll()
            .pathMatchers("/api/auth/logout").authenticated()
            .anyExchange().authenticated()
          .and()
            .formLogin()
            .loginPage("/api/auth/login")
            .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
                return Mono.empty();
            })
            .authenticationFailureHandler((webFilterExchange, exception) -> {
                webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return Mono.empty();
            })
          .and()
            .exceptionHandling()
              .authenticationEntryPoint((exchange, e) -> {
                  exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                  return Mono.empty();
              })
          .and()
            .logout()
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler((exchange, authentication) -> {
                exchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
                return Mono.empty();
            })
          .and()
            .csrf()
              .disable()
            .build();
        //@formatter:on
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
