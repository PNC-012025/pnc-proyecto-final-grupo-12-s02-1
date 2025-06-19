INSERT INTO "role" VALUES(1, 'ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO "role" VALUES(2, 'ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO "role" VALUES(3, 'ROLE_SYSADMIN') ON CONFLICT DO NOTHING;

INSERT INTO "brand" (brand) VALUES
('Toyota'),
('Hyundai'),
('Nissan'),
('Kia'),
('Chevrolet'),
('Ford'),
('Honda'),
('Mitsubishi'),
('Suzuki'),
('Mazda'),
('Volkswagen'),
('Mercedes-Benz'),
('BMW'),
('Audi'),
('Isuzu'),
('Jeep')
ON CONFLICT DO NOTHING;

INSERT INTO Model (model, brand_id) VALUES
-- Toyota (1)
('Corolla', 1),
('Yaris', 1),
('RAV4', 1),
('Hilux', 1),
('Land Cruiser', 1),
('4Runner', 1),
('Fortuner', 1),
('Corolla Cross', 1),
('Tacoma', 1),

-- Hyundai (2)
('Tucson', 2),
('Elantra', 2),
('Santa Fe', 2),
('Kona', 2),
('Creta', 2),
('Accent', 2),
('Venue', 2),

-- Nissan (3)
('Sentra', 3),
('X-Trail', 3),
('Patrol', 3),
('Versa', 3),
('Kicks', 3),
('Juke', 3),
('Qashqai', 3),
('Pathfinder', 3),
('Murano', 3),
('Rogue', 3),
('Frontier', 3),

-- Kia (4)
('Soul', 4),
('Sportage', 4),
('Sorento', 4),
('Rio', 4),
('Seltos', 4),
('Picanto', 4),
('Forte', 4),

-- Chevrolet (5)
('Aveo', 5),
('Sonic', 5),
('Silverado', 5),
('Tracker', 5),
('Equinox', 5),
('Trailblazer', 5),
('Captiva', 5),
('Tahoe', 5),
('Suburban', 5),
('Colorado', 5),
('Blazer', 5),
('Traverse', 5),
('Spark', 5),
('Trax', 5),

-- Ford (6)
('Focus', 6),
('Explorer', 6),
('F-150', 6),
('Escape', 6),
('Ranger', 6),
('Edge', 6),
('Bronco', 6),
('Fiesta', 6),
('EcoSport', 6),

-- Honda (7)
('Civic', 7),
('Accord', 7),
('CR-V', 7),
('HR-V', 7),
('Fit', 7),
('Pilot', 7),
('Ridgeline', 7),
('Odyssey', 7),

-- Mitsubishi (8)
('Lancer', 8),
('Outlander', 8),
('Montero', 8),
('Outlander Sport', 8),
('Eclipse Cross', 8),
('ASX', 8),
('Mirage', 8),
('L200', 8),

-- Suzuki (9)
('Swift', 9),
('Vitara', 9),
('Baleno', 9),
('S-Cross', 9),
('Jimny', 9),

-- Mazda (10)
('3', 10),
('6', 10),
('CX-3', 10),
('CX-30', 10),
('CX-5', 10),
('CX-60', 10),
('CX-9', 10),
('CX-90', 10),
('BT-50', 10),
('MX-5', 10),

-- Volkswagen (11)
('Golf', 11),
('Jetta', 11),
('Tiguan', 11),
('Passat', 11),
('Teramont', 11),
('T-Cross', 11),
('Taos', 11),
('Amarok', 11),
('Virtus', 11),
('Nivus', 11),

-- Mercedes-Benz (12)
('A-Class', 12),
('C-Class', 12),
('E-Class', 12),
('S-Class', 12),
('CLA', 12),
('GLB', 12),
('GLC', 12),
('GLE', 12),
('GLS', 12),
('G-Class', 12),
('Sprinter', 12),

-- BMW (13)
('X1', 13),
('X2', 13),
('X3', 13),
('X4', 13),
('X5', 13),
('X6', 13),
('X7', 13),
('Serie 3', 13),
('Serie 5', 13),
('Serie 6', 13),

-- Audi (14)
('A3', 14),
('A4', 14),
('A6', 14),
('A8', 14),
('Q2', 14),
('Q3', 14),
('Q5', 14),
('Q7', 14),
('Q8', 14),

-- Isuzu (15)
('D-Max', 15),
('MU-X', 15),

-- Jeep (16)
('Wrangler', 16),
('Cherokee', 16),
('Compass', 16),
('Renegade', 16),
('Grand Cherokee', 16),
('Gladiator', 16),
('Patriot', 16)
ON CONFLICT DO NOTHING;


INSERT INTO "year" (year) VALUES
(2015),
(2016),
(2017),
(2018),
(2019),
(2020),
(2021),
(2022),
(2023),
(2024),
(2025)
ON CONFLICT DO NOTHING;
