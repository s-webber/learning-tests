CREATE TABLE employee (
  id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE
);

INSERT INTO employee VALUES (1, 'Jack', 'Abbey', '1996-08-01', null);
INSERT INTO employee VALUES (2, 'Amelia', 'Cropper', '2010-11-30', null);
INSERT INTO employee VALUES (3, 'James', 'Jones', '1996-08-01', null);
INSERT INTO employee VALUES (4, 'Olivia', 'Hodges', '1996-08-01', null);
INSERT INTO employee VALUES (5, 'William', 'Marsden', '1996-08-01', null);
INSERT INTO employee VALUES (6, 'Isla', 'Oliver', '1996-08-01', null);
INSERT INTO employee VALUES (7, 'Thomas', 'Peterson', '2010-11-30', null);
INSERT INTO employee VALUES (8, 'Emily', 'Ramsey', '1996-08-01', null);
INSERT INTO employee VALUES (9, 'Jacob', 'Smith', '1996-08-01', null);
INSERT INTO employee VALUES (10, 'Poppy', 'Young  ', '1996-08-01', null);

CREATE TABLE customer (
  id INT PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL
);

INSERT INTO customer VALUES (1, 'Fred', 'Heslop');
INSERT INTO customer VALUES (2, 'Lucy', 'Johnson');
INSERT INTO customer VALUES (3, 'John', 'Harvey');
