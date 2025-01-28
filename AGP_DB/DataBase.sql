DROP DATABASE IF EXISTS traveldb;
CREATE DATABASE traveldb;
USE traveldb;

-- Création des tables
CREATE TABLE Island (
  id INTEGER NOT NULL,
  name VARCHAR(20),
  PRIMARY KEY (id)
);

CREATE TABLE Place (
  id INTEGER NOT NULL,
  name VARCHAR(70),
  type ENUM('historic', 'activity'),
  visitDuration INT,
  entrancePrice FLOAT,
  
  latitude FLOAT,
  longitude FLOAT,
  idIsland INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (idIsland) REFERENCES Island (id)
);

CREATE TABLE Hotel (
  id INTEGER NOT NULL,
  name VARCHAR(70),
  pricePerDay FLOAT,

  latitude FLOAT,
  longitude FLOAT,
    
  idIsland INTEGER,
  PRIMARY KEY (id),
  FOREIGN KEY (idIsland) REFERENCES Island (id)
);


-- Remplissage
INSERT INTO Island (id, name) VALUES
  (0, 'Guadeloupe'),
  (1, 'Dominique'),
  (2, 'Martinique');

INSERT INTO Place (id, name, type, visitDuration, entrancePrice, latitude, longitude, idIsland) VALUES
  (0, 'Savane des Pétrifications', 				'activity', 7, 8.5  , 14.40 , -60.85 ,2),
  (1,  'Musée du Café',  							'historic', 1, 5.0  , 16.06 , -61.76 ,0),
  (2,  'Distillerie Rhum Bologne',  				'activity', 1, 12.0 , 16.00 , -61.73 ,0),
  (3,  'Fort Delgrès',  							'activity', 2, 23.5 , 15.98 , -61.72 ,0),
  (4,  'Parc Archéologique des Roches Gravées', 	'historic', 3, 13.2 , 15.97 , -61.64 ,0),
  (5,  'Allée Dumanoir',  							'historic', 2, 14.0 , 16.03 , -61.56 ,0),
  (6,  'Maison de la Kassaverie',  					'activity', 3, 6.4  , 16.05 , -61.57 ,0),
  (7,  'Anse Montillor',  							'activity', 4, 4.9  , 16.49 , -61.48 ,0),
  (8,  'Plage de La Porte Enfer',  					'activity', 2, 1.9  , 16.48 , -61.44 ,0),
  (9,  'Aquarium de la Guadeloupe',  				'activity', 10, 8.50 , 16.22 , -61.52 ,0),
  (10, 'Wizosky', 									'historic', 1, 12.5 , 16.33 , -61.34 ,0),
  (11, 'Les chutes de Bwa Nef', 					'activity', 1, 4.9  , 15.62 , -61.42 ,1),
  (12, 'Centre culturel et musée Old Mill', 		'historic', 2, 5.2  , 15.33 , -61.38 ,1),
  (13, 'Musée de la Dominique', 					'historic', 4, 9.2  , 15.29 , -61.38 ,1),
  (14, 'Plage de la baie de douglas', 				'activity', 2, 4.6  , 15.59 , -61.46 ,1),
  (15, 'Wavine Cyrique Falls', 						'activity', 2, 7.2  , 15.36 , -61.25 ,1),
  (16, 'Chutes de Sari Sari', 						'activity', 1, 6.6  , 15.32 , -61.26 ,1),
  (17, 'Escalier Tete Chien', 						'activity', 3, 16.4 , 15.46 , -61.24 ,1),
  (18, 'Forêts classées du nord', 					'activity', 9, 17.3 , 15.51 , -61.35 ,1),
  (19, 'Cabrits National Park', 					'activity', 1, 25.2 , 15.58 , -61.47 ,1),
  (20, 'Cascades de Trafalgar', 					'activity', 1, 23.1 , 15.32 , -61.33 ,1),
  (21, 'Musée de la banane', 						'historic', 2, 14.5 , 14.77 , -61.01 ,2),
  (22, 'Distillerie Saint James',					'historic', 4, 12.3 , 14.78 , -60.99 ,2),
  (23, 'Montagne Pelée', 							'activity', 9, 8.0  , 14.81 , -61.16 ,2),
  (24, 'Musée Franck Perret', 						'historic', 1, 4.2  , 14.74 , -61.17 ,2),
  (25, 'Pointe Vivé', 								'activity', 2, 30.5 , 14.84 , -61.08 ,2),
  (26, 'Jardin de Balata', 							'activity', 3, 12.2 , 14.67 , -61.09 ,2),
  (27, 'Cap 110', 									'historic', 8, 21.6 , 14.46 , -61.04 ,2),
  (28, 'La Baignoire de Joséphine', 				'activity', 1, 11.5 , 14.61 , -60.89 ,2),
  (29, 'Musée départemental de préhistoire de la martinique', 	'historic', 2, 14.3 , 14.60 , -61.06 ,2);

INSERT INTO Hotel (id, name, pricePerDay, latitude, longitude, idIsland) VALUES
  (0, 'Residence Roxelle' 			, 63.2 , 14.76 , -61.16 , 2),
  (1, 'Le Relax Hotêl' 				, 158.6 , 16.35 , -61.45 , 0),
  (2, 'Les Nids de Belle Eau' 		, 235.5 , 16.02 , -61.60 , 0),
  (3, 'Eden Des Colibris' 			, 103.8 , 16.07 , -61.72 , 0),
  (4, 'Comfort Cottages' 			, 348.5 , 15.59 , -61.39 , 1),
  (5, 'Castle of Comfort' 			, 129.9 , 15.28 , -61.36 , 1),
  (6, 'Kai Woshe Inn' 				, 85.2 , 15.27 , -61.25 , 1),
  (7, 'la Dunette' 					, 280.0 , 14.44 , -60.88 , 2),
  (8, 'Le refuge du bon repos' 		, 206.9 , 14.83 , -61.07 , 2);
