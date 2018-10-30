CREATE TABLE IF NOT EXISTS events (
  id VARCHAR(36) NOT  NULL,
  name TEXT NOT NULL CHECK (name <> ''),
  date DATE,
  owners JSONB,
  attendees JSONB,
  PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS ownersIndex ON events USING GIN (owners);
CREATE INDEX IF NOT EXISTS attendeesIndex ON events USING GIN (attendees);
