DROP TABLE IF EXISTS Friendships;
DROP TABLE IF EXISTS Groups_memberships_requests;
DROP TABLE IF EXISTS Friendships_requests;
DROP TABLE IF EXISTS Groups_moderators;
DROP TABLE IF EXISTS Groups_members;
DROP TABLE IF EXISTS Passwords;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS dialogs_messages;
DROP TABLE IF EXISTS group_wall_messages;
DROP TABLE IF EXISTS account_wall_messages;
DROP TABLE IF EXISTS dialogs;
DROP TABLE IF EXISTS Phones;
DROP TABLE IF EXISTS _Groups;
DROP TABLE IF EXISTS Accounts;

CREATE TABLE IF NOT EXISTS Accounts
(
    id                BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(40) NOT NULL,
    surname           VARCHAR(40) NOT NULL,
    patronymic        VARCHAR(40),
    sex               CHAR(6),
    birth_date        DATE,
    registration_date DATE,
    role              CHAR(5) DEFAULT 'USER',
    email             VARCHAR(40) NOT NULL UNIQUE,
    additional_email  VARCHAR(40) UNIQUE,
    icq               VARCHAR(40),
    skype             VARCHAR(40),
    city              VARCHAR(40),
    country           VARCHAR(40),
    photo             MEDIUMBLOB
);

ALTER TABLE Accounts
    ADD CHECK (sex IN ('MALE', 'FEMALE'));

ALTER TABLE Accounts
    ADD CHECK (role IN ('ADMIN', 'USER'));

CREATE TABLE IF NOT EXISTS _Groups
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL UNIQUE,
    description   VARCHAR(500),
    owner_id      BIGINT UNSIGNED,
    creation_date DATE,
    photo         MEDIUMBLOB
);

ALTER TABLE _Groups
    ADD CONSTRAINT C_11 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS Groups_members
(
    account_id BIGINT NOT NULL,
    group_id   BIGINT NOT NULL
);

ALTER TABLE Groups_members
    ADD CONSTRAINT C_12 PRIMARY KEY (account_id, group_id);

ALTER TABLE Groups_members
    ADD CONSTRAINT C_13 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_members
    ADD CONSTRAINT C_14 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS Friendships
(
    first_account  BIGINT UNSIGNED,
    second_account BIGINT UNSIGNED,
    PRIMARY KEY (first_account, second_account)
);

ALTER TABLE Friendships
    ADD CONSTRAINT C_16 FOREIGN KEY (first_account) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Friendships
    ADD CONSTRAINT C_17 FOREIGN KEY (second_account) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Friendships
    ADD CHECK (first_account != second_account);

CREATE TABLE IF NOT EXISTS Phones
(
    id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    number   VARCHAR(50)     NOT NULL UNIQUE,
    type     CHAR(7),
    owner_id BIGINT UNSIGNED NOT NULL
);

ALTER TABLE Phones
    ADD CONSTRAINT C_18 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Phones
    ADD CONSTRAINT C_19 CHECK (type IN ('PRIVATE', 'WORK'));

CREATE TABLE Passwords
(
    email    VARCHAR(40)  NOT NULL,
    password VARCHAR(255) NOT NULL
);

ALTER TABLE Passwords
    ADD CONSTRAINT C_20 PRIMARY KEY (email, password);

ALTER TABLE Passwords
    ADD CONSTRAINT C_21 FOREIGN KEY (email) REFERENCES Accounts (email) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS Groups_moderators
(
    account_id BIGINT UNSIGNED,
    group_id   BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_moderators
    ADD CONSTRAINT C_24 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_moderators
    ADD CONSTRAINT C_25 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS Groups_memberships_requests
(
    account_id BIGINT UNSIGNED,
    group_id   BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_memberships_requests
    ADD CONSTRAINT C_26 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_memberships_requests
    ADD CONSTRAINT C_27 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS Friendships_requests
(
    requester BIGINT UNSIGNED,
    receiver  BIGINT UNSIGNED,
    PRIMARY KEY (requester, receiver)
);

ALTER TABLE Friendships_requests
    ADD CONSTRAINT C_28 FOREIGN KEY (requester) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Friendships_requests
    ADD CONSTRAINT C_29 FOREIGN KEY (receiver) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `account_wall_messages`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime        NOT NULL
);

ALTER TABLE account_wall_messages
    ADD CONSTRAINT C_31 FOREIGN KEY (author) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE account_wall_messages
    ADD CONSTRAINT C_32 FOREIGN KEY (receiver_id) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `group_wall_messages`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime        NOT NULL
);

ALTER TABLE group_wall_messages
    ADD CONSTRAINT C_35 FOREIGN KEY (author) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE group_wall_messages
    ADD CONSTRAINT C_36 FOREIGN KEY (receiver_id) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE dialogs
(
    id        bigint unsigned auto_increment PRIMARY KEY,
    first_id  bigint unsigned NOT NULL,
    second_id bigint unsigned NOT NULL,
    CONSTRAINT dialogs_accounts_id_fk FOREIGN KEY (first_id) REFERENCES accounts (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT dialogs_accounts_id_fk_2 FOREIGN KEY (second_id) REFERENCES accounts (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `dialogs_messages`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime        NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE dialogs_messages
    ADD CONSTRAINT C_33 FOREIGN KEY (author) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE dialogs_messages
    ADD CONSTRAINT C_34 FOREIGN KEY (receiver_id) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

