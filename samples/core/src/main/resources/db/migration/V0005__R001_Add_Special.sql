
-- *** Special ***
CREATE TABLE Special(
  id BIGINT NOT NULL,
  modificationCounter INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  startingDay INTEGER NOT NULL,
  startingHour INTEGER NOT NULL,
  endingDay INTEGER NOT NULL,
  endingHour INTEGER NOT NULL,
  specialPrice DECIMAL(19, 2) NOT NULL
);

INSERT INTO Special (id, modificationCounter, name, startingDay, startingHour, endingDay, endingHour, specialPrice) VALUES (0, 1, 'Happy Hour Monday', 0, 18, 0, 21, 4.99);
INSERT INTO Special (id, modificationCounter, name, startingDay, startingHour, endingDay, endingHour, specialPrice) VALUES (1, 1, 'Happy Hour Friday', 4, 18, 4, 21, 5.99);
