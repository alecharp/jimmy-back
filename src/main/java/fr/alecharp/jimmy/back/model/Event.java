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

package fr.alecharp.jimmy.back.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.Set;

@Entity
@Table(
      name = "events"
)
public class Event {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotEmpty
    private String name;
    @NotEmpty
    private ZonedDateTime dateTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
          name = "events_admins",
          joinColumns = {@JoinColumn(name = "event_id")},
          inverseJoinColumns = {@JoinColumn(name = "account_id")},
          uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "account_id"})
    )
    private Set<String> admins;
    @ManyToAny(metaColumn = @Column(name = "event_attendees"), fetch = FetchType.EAGER)
    @JoinTable(
          name = "events_attendees",
          joinColumns = {@JoinColumn(name = "event_id")},
          inverseJoinColumns = {@JoinColumn(name = "account_id")},
          uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "account_id"})
    )
    private Set<String> attendees;
}
