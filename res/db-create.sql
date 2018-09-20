CREATE TABLE users(
  id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT KEY,
  login VARCHAR(16),
  latitude REAL,
  longitude REAL,
  bearing INTEGER
)
