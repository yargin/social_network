CREATE TABLE IF NOT EXISTS Accounts (
        id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(40) NOT NULL,
        surname VARCHAR(40) NOT NULL,
        patronymic VARCHAR(40),
        sex CHAR(6),
        birth_date DATE,
        registration_date DATE,
        role CHAR(5) DEFAULT 'USER',
        email VARCHAR(40) NOT NULL UNIQUE,
        additional_email VARCHAR(40) UNIQUE,
        icq VARCHAR(40),
        skype VARCHAR(40),
        city VARCHAR(40),
        country VARCHAR(40)
);

ALTER TABLE Accounts
ADD CHECK (sex IN ('MALE', 'FEMALE'));

ALTER TABLE Accounts
ADD CHECK (role IN ('ADMIN', 'USER'));

CREATE TABLE IF NOT EXISTS _Groups (
        id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(50) NOT NULL UNIQUE,
        description VARCHAR(500),
        owner_id BIGINT UNSIGNED,
        creation_date DATE,
        photo MEDIUMBLOB
);

ALTER TABLE _Groups
ADD CONSTRAINT C_11 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS Groups_members (
        account_id BIGINT UNSIGNED,
        group_id BIGINT UNSIGNED
);

ALTER TABLE Groups_members
ADD CONSTRAINT C_12 PRIMARY KEY(account_id, group_id);

ALTER TABLE Groups_members
ADD CONSTRAINT C_13 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Groups_members
ADD CONSTRAINT C_14 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Friendships (
        first_account BIGINT UNSIGNED,
        second_account BIGINT UNSIGNED,
        PRIMARY KEY(first_account, second_account)
);

ALTER TABLE Friendships
ADD CONSTRAINT C_16 FOREIGN KEY (first_account) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Friendships
ADD CONSTRAINT C_17 FOREIGN KEY (second_account) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Friendships
ADD CHECK (first_account != second_account);

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

CREATE TABLE Passwords (
    email VARCHAR(40),
    password VARCHAR(255)
);

ALTER TABLE Passwords
ADD CONSTRAINT C_20 PRIMARY KEY(email, password);

ALTER TABLE Passwords
ADD CONSTRAINT C_21 FOREIGN KEY (email) REFERENCES Accounts (email) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Account_photo (
    owner_id BIGINT UNSIGNED PRIMARY KEY,
    photo MEDIUMBLOB
);

ALTER TABLE Account_photo
ADD CONSTRAINT C_22 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Groups_moderators (
    account_id BIGINT UNSIGNED,
    group_id BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_moderators
ADD CONSTRAINT C_24 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Groups_moderators
ADD CONSTRAINT C_25 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Groups_memberships_requests (
    account_id BIGINT UNSIGNED,
    group_id BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_memberships_requests
ADD CONSTRAINT C_26 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Groups_memberships_requests
ADD CONSTRAINT C_27 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Friendships_requests (
    requester BIGINT UNSIGNED,
    receiver BIGINT UNSIGNED,
    PRIMARY KEY (requester, receiver)
);

ALTER TABLE Friendships_requests
ADD CONSTRAINT C_28 FOREIGN KEY (requester) REFERENCES Accounts (id) ON DELETE CASCADE;

ALTER TABLE Friendships_requests
ADD CONSTRAINT C_29 FOREIGN KEY (receiver) REFERENCES Accounts (id) ON DELETE CASCADE;

INSERT INTO Accounts (id, name, surname, email) VALUES
(1, 'Vladimir', 'Lenin', 'rise@communism.su'),
(2, 'dracula', 'NOSFERATU', 'drink@blood.com'),
(3, 'Alan', 'Turing','robot@power.com'),
(4, 'Petr', 'Popov', 'popovp@gmail.com');

INSERT INTO _Groups (id, name, description, owner_id, creation_date) VALUES
(1, 'USSR fans', 'building Communism', 1, '2020-01-01'),
(2, 'machine learning', '', 2, '2018-12-31');

INSERT INTO Groups_members VALUES
(1, 1), (2, 1), (3, 2), (1, 2);

INSERT INTO Friendships VALUES
(1, 2), (1, 3);

INSERT INTO Groups_moderators VALUES
(1, 2), (1, 1);

INSERT INTO Passwords (email, password) VALUES
('rise@communism.su', SHA2('communism1', 0)),
('drink@blood.com', SHA2('blood2', 0)),
('robot@power.com', SHA2('power3', 0)),
('popovp@gmail.com', SHA2('gmail4', 0));

INSERT INTO Phones (number, type, owner_id) VALUES
('89218942', 'PRIVATE', 4),
('+9 812 123 321', 'WORK', 3),
('444-444-444', 'PRIVATE', 3),
('14-1414-14', 'WORK', 2),
('8 (921) 1234321', 'PRIVATE', 2),
('+7 (920) 123-23-32', 'WORK', 1),
('02', 'WORK', 1);
