drop ALL OBJECTS;

create TABLE IF NOT EXISTS Accounts (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    patronymic VARCHAR(40),
    sex CHAR(6),
    birth_date DATE,
    email VARCHAR(40) NOT NULL UNIQUE,
    additional_email VARCHAR(40) UNIQUE,
    icq VARCHAR(40),
    skype VARCHAR(40),
    city VARCHAR(40),
    country VARCHAR(40)
);

create TABLE IF NOT EXISTS _Groups (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    owner_id BIGINT UNSIGNED NOT NULL
);

alter table _Groups
add CONSTRAINT C_11 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON delete CASCADE;

create TABLE IF NOT EXISTS Accounts_in_groups (
    account_id BIGINT UNSIGNED NOT NULL,
    group_id BIGINT UNSIGNED NOT NULL
);

alter table Accounts_in_groups
add CONSTRAINT C_12 PRIMARY KEY (account_id, group_id);

alter table Accounts_in_groups
add CONSTRAINT C_13 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON delete CASCADE;

alter table Accounts_in_groups
add CONSTRAINT C_14 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON delete CASCADE;

insert into Accounts (name, surname, email) values
('Vladimir', 'Lenin',  'rise@communism.su'),
('dracula', 'NOSFERATU',  'drink@blood.com'),
('Alan', 'Turing', 'robot@power.com'),
('Petr', 'Popov', 'popovp@gmail.com');

insert into _Groups (name, description, owner_id) values
('USSR fans', 'building Communism', 1),
('machine learning', '', 3);

insert into Accounts_in_groups values
(1, 1), (2, 1), (3, 2), (1, 2);

CREATE TABLE IF NOT EXISTS Friendships (
        first_account BIGINT UNSIGNED NOT NULL,
        second_account BIGINT UNSIGNED NOT NULL
    );

ALTER TABLE Friendships
ADD CONSTRAINT C_15 PRIMARY KEY(first_account, second_account);

ALTER TABLE Friendships
ADD CONSTRAINT C_16 FOREIGN KEY (first_account) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Friendships
ADD CONSTRAINT C_17 FOREIGN KEY (second_account) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Friendships
ADD CHECK (first_account != second_account);

INSERT INTO Friendships VALUES
(1, 2), (1, 3);

CREATE TABLE IF NOT EXISTS Phones (
        id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        number VARCHAR(50) NOT NULL UNIQUE,
        type CHAR(7),
        owner_id BIGINT UNSIGNED NOT NULL
    );

ALTER TABLE Phones
ADD CONSTRAINT C_18 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Phones
ADD CONSTRAINT C_19 CHECK (type IN ('PRIVATE', 'WORK'));

INSERT INTO Phones (number, type, owner_id) VALUES
('89218942', 'PRIVATE', 4),
('+9 812 123 321', 'WORK', 3),
('444-444-444', 'PRIVATE', 3),
('14-1414-14', 'WORK', 2),
('8 (921) 1234321', 'PRIVATE', 2),
('+7 (920) 123-23-32', 'WORK', 1),
('02', 'WORK', 1);
