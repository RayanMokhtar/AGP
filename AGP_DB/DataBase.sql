-- Drop des tables si elles existent
DROP TABLE IF EXISTS Hotel;
DROP TABLE IF EXISTS Site;
DROP TABLE IF EXISTS Island;

-- Creation de la table Island
CREATE TABLE Island (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creation de la table Hotel
CREATE TABLE Hotel (
  id INT PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  idIsland INT NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  pricePerDay FLOAT NOT NULL,
  stars INT NOT NULL,
  FOREIGN KEY (idIsland) REFERENCES Island(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Creation de la table Site
CREATE TABLE Site (
  id INT PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  entryPrice FLOAT NOT NULL,
  type VARCHAR(45) NOT NULL,
  duration DOUBLE NOT NULL,
  idIsland INT NOT NULL,
  latitude DOUBLE NOT NULL,
  longitude DOUBLE NOT NULL,
  FOREIGN KEY (idIsland) REFERENCES Island(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Données de la table Island
INSERT INTO Island (id, name) VALUES
  (0, 'Guadeloupe'),
  (1, 'Dominica'),
  (2, 'Martinique'),
  (3, 'Reunion'),
  (4, 'Seychelles'),
  (5, 'Mauritius'),
  (6, 'Tahiti')
ON DUPLICATE KEY UPDATE name=VALUES(name);

-- Données de la table Hotel
INSERT INTO Hotel (id, name, idIsland, latitude, longitude, pricePerDay, stars) VALUES
  (0, 'Fleur d''Épices Hotel', 0, 16.04, -61.7, 50.5, 1),
  (1, 'Bois d''Inde Residence', 0, 16.05, -61.57, 75.0, 3),
  (2, 'Eden Colibri', 0, 16.07, -61.72, 103.8, 4),
  (3, 'Kai Woshe Inn', 1, 15.27, -61.25, 85.2, 3),
  (4, 'Castle of Comfort', 1, 15.28, -61.36, 129.9, 4),
  (5, 'Bamboo Residence', 2, 14.7, -61.1, 50.0, 1),
  (6, 'Karibea La Valmenière', 2, 14.6, -61.06, 120.0, 3),
  (7, 'Refuge of Angels', 3, -21.15, 55.53, 140.0, 3),
  (8, 'Vanilla Villa', 3, -21.1, 55.33, 220.0, 5),
  (9, 'Beach Cottage', 4, -4.61, 55.45, 180.0, 4),
  (10, 'Turquoise Sea Bungalow', 4, -4.34, 55.76, 250.0, 5),
  (11, 'Pamplemousse Inn', 5, -20.12, 57.57, 95.0, 3),
  (12, 'Belle Mare Villa', 5, -20.19, 57.46, 180.0, 4),
  (13, 'Tahiti Lodge', 6, -17.54, -149.56, 120.0, 3),
  (14, 'Sunset Beach Pension', 6, -17.6, -149.48, 75.0, 2),
  (15, 'Palm Beach Resort', 4, -4.60, 55.47, 200.0, 4),
  (16, 'Mountain View Lodge', 1, 15.30, -61.30, 90.0, 3),
  (17, 'Coral Paradise', 6, -17.55, -149.55, 150.0, 4),
  (18, 'Emerald Bay Hotel', 5, -20.15, 57.50, 110.0, 3),
  (19, 'Volcano Retreat', 3, -21.20, 55.60, 180.0, 4),
  (20, 'Sunrise Bungalow', 0, 16.10, -61.60, 80.0, 2),
  (21, 'Ocean Breeze Villa', 2, 14.65, -61.05, 130.0, 4),
  (22, 'Tropical Haven', 4, -4.33, 55.80, 240.0, 5),
  (23, 'Rainforest Eco Lodge', 1, 15.35, -61.28, 70.0, 2),
  (24, 'Lagoon View Resort', 6, -17.58, -149.50, 170.0, 4) 
ON DUPLICATE KEY UPDATE name=VALUES(name), pricePerDay=VALUES(pricePerDay), stars=VALUES(stars);

-- Donneés de la table Site
INSERT INTO Site (id, name, entryPrice, type, duration, idIsland, latitude, longitude) VALUES
  (0, 'Guadeloupe National Park', 10.5, 'hobbies', 4, 0, 16.15, -61.54),
  (1, 'Fort Delgrès', 5.0, 'historic', 2, 0, 15.99, -61.73),
  (2, 'La Caravelle Beach', 0.0, 'hobbies', 3, 0, 16.22, -61.35),
  (3, 'Sari Sari Falls', 6.6, 'hobbies', 3, 1, 15.32, -61.26),
  (4, 'Tete Chien Stairs', 8.0, 'historic', 1, 1, 15.46, -61.24),
  (5, 'Cabrits National Park', 25.2, 'hobbies', 5, 1, 15.58, -61.47),
  (6, 'Mount Pelée', 8.0, 'hobbies', 6, 2, 14.81, -61.16),
  (7, 'Banana Museum', 14.5, 'historic', 2, 2, 14.77, -61.01),
  (8, 'Balata Garden', 12.2, 'hobbies', 3, 2, 14.67, -61.09),
  (9, 'Piton de la Fournaise', 15.0, 'hobbies', 8, 3, -21.23, 55.71),
  (10, 'Petit Bazar Tamil Temple', 2.5, 'historic', 1, 3, -20.88, 55.45),
  (11, 'Mafate Cirque', 0.0, 'hobbies', 4, 3, -21.04, 55.42),
  (12, 'Anse Source d''Argent', 10.0, 'hobbies', 2, 4, -4.35, 55.83),
  (13, 'Victoria Clocktower', 5.0, 'historic', 1, 4, -4.62, 55.45),
  (14, 'Morne Seychellois Park', 12.0, 'hobbies', 5, 4, -4.63, 55.42),
  (15, 'Pamplemousses Garden', 8.0, 'hobbies', 3, 5, -20.11, 57.57),
  (16, 'Fort Adelaide', 6.5, 'historic', 2, 5, -20.16, 57.49),
  (17, 'Belle Mare Beach', 0.0, 'hobbies', 2, 5, -20.19, 57.45),
  (18, 'Teahupoo', 0.0, 'hobbies', 4, 6, -17.84, -149.27),
  (19, 'Museum of Tahiti and Islands', 10.0, 'historic', 2, 6, -17.57, -149.6),
  (20, 'Venus Point Beach', 0.0, 'hobbies', 3, 6, -17.5, -149.49),
  (21, 'Deshaies Botanical Garden', 7.5, 'hobbies', 3, 0, 16.30, -61.80),
  (22, 'Boiling Lake', 0.0, 'hobbies', 6, 1, 15.32, -61.29),
  (23, 'Diamond Rock', 0.0, 'hobbies', 2, 2, 14.70, -61.20),
  (24, 'Grand Anse Beach', 0.0, 'hobbies', 3, 4, -4.30, 55.80),
  (25, 'Black River Gorges', 5.0, 'hobbies', 4, 5, -20.40, 57.45),
  (26, 'Fougères Waterfall', 3.0, 'hobbies', 2, 3, -21.10, 55.50),
  (27, 'Arahurahu Marae', 4.0, 'historic', 1, 6, -17.60, -149.55),
  (28, 'Soufrière Volcano', 10.0, 'hobbies', 5, 1, 15.30, -61.35),
  (29, 'Jardin de Balata', 8.0, 'hobbies', 2, 2, 14.68, -61.08),
  (30, 'Anse Lazio', 0.0, 'hobbies', 3, 4, -4.28, 55.75)
ON DUPLICATE KEY UPDATE name=VALUES(name), entryPrice=VALUES(entryPrice), duration=VALUES(duration);
