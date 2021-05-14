CREATE TABLE Accounts
(
    id                BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name              VARCHAR(40) NOT NULL,
    surname           VARCHAR(40) NOT NULL,
    patronymic        VARCHAR(40),
    sex               CHAR(6),
    birth_date        DATE,
    registration_date DATE,
    role              CHAR(5)     NOT NULL DEFAULT 'USER',
    email             VARCHAR(40) NOT NULL UNIQUE,
    additional_email  VARCHAR(40) UNIQUE,
    icq               VARCHAR(40),
    skype             VARCHAR(40),
    city              VARCHAR(40),
    country           VARCHAR(40),
    photo             MEDIUMBLOB,
    version           BIGINT      NOT NULL
);

ALTER TABLE Accounts ADD CHECK (sex IN ('MALE', 'FEMALE'));

ALTER TABLE Accounts ADD CHECK (role IN ('ADMIN', 'USER'));

CREATE TABLE `Groups`
(
    id            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL UNIQUE,
    description   VARCHAR(500),
    owner_id      BIGINT UNSIGNED,
    creation_date DATE,
    photo         MEDIUMBLOB,
    version       BIGINT NOT NULL
);

ALTER TABLE `groups` ADD CONSTRAINT C_11 FOREIGN KEY (owner_id) REFERENCES Accounts (id);

CREATE TABLE IF NOT EXISTS Groups_members
(
    account_id BIGINT UNSIGNED,
    group_id   BIGINT UNSIGNED
);

ALTER TABLE Groups_members ADD CONSTRAINT C_12 PRIMARY KEY (account_id, group_id);

ALTER TABLE Groups_members
    ADD CONSTRAINT C_13 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_members ADD CONSTRAINT C_14 FOREIGN KEY (group_id) REFERENCES `groups` (id) ON DELETE CASCADE;

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

ALTER TABLE Friendships ADD CHECK (first_account > second_account);

CREATE TABLE Phones
(
    id       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    number   VARCHAR(50)     NOT NULL UNIQUE,
    type     CHAR(7),
    owner_id BIGINT UNSIGNED NOT NULL,
    version  BIGINT NOT NULL
);

ALTER TABLE Phones
ADD CONSTRAINT C_18 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Phones ADD CONSTRAINT C_19 CHECK (type IN ('PRIVATE', 'WORK'));

CREATE TABLE Passwords (
                           email VARCHAR(40),
                           password VARCHAR(255),
                           CONSTRAINT c_21 FOREIGN KEY (email) REFERENCES Accounts (email) ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE Passwords ADD CONSTRAINT C_20 PRIMARY KEY(email);

CREATE TABLE Groups_moderators
(
    account_id BIGINT UNSIGNED,
    group_id   BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_moderators
    ADD CONSTRAINT C_24 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_moderators
    ADD CONSTRAINT C_25 FOREIGN KEY (group_id) REFERENCES `groups` (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_moderators ADD CONSTRAINT C_Groups_moderators_3 UNIQUE (account_id, group_id);

CREATE TABLE Groups_memberships_requests
(
    account_id BIGINT UNSIGNED,
    group_id   BIGINT UNSIGNED,
    PRIMARY KEY (account_id, group_id)
);

ALTER TABLE Groups_memberships_requests
    ADD CONSTRAINT C_26 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Groups_memberships_requests
    ADD CONSTRAINT C_27 FOREIGN KEY (group_id) REFERENCES `groups` (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `Friendships_requests`
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
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `author`      BIGINT UNSIGNED NOT NULL,
    `message`     VARCHAR(500),
    `image`       MEDIUMBLOB,
    `receiver_id` BIGINT UNSIGNED NOT NULL,
    `posted`      DATETIME NOT NULL,
    `version`     BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    KEY           `C_32` (`receiver_id`),
    KEY           `C_31` (`author`),
    CONSTRAINT `C_31` FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `C_32` FOREIGN KEY (`receiver_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `group_wall_messages`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `author`      BIGINT UNSIGNED NOT NULL,
    `message`     VARCHAR(500),
    `image`       MEDIUMBLOB,
    `receiver_id` BIGINT UNSIGNED NOT NULL,
    `posted`      DATETIME        NOT NULL,
    `version`     BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    KEY `C_36` (`receiver_id`),
    KEY `C_35` (`author`),
    CONSTRAINT `C_35` FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `C_36` FOREIGN KEY (`receiver_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE dialogs
(
    id        BIGINT UNSIGNED auto_increment PRIMARY KEY,
    first_id  BIGINT UNSIGNED NOT NULL,
    second_id BIGINT UNSIGNED NOT NULL,
    `version`     BIGINT NOT NULL,
    CONSTRAINT dialogs_accounts_id_fk FOREIGN KEY (first_id) REFERENCES accounts (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT dialogs_accounts_id_fk_2 FOREIGN KEY (second_id) REFERENCES accounts (id) ON UPDATE CASCADE ON DELETE
        CASCADE,
    CONSTRAINT dialogs_accounts_id UNIQUE (first_id, second_id)
);

CREATE TABLE `dialogs_messages`
(
    `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `author`      BIGINT UNSIGNED NOT NULL,
    `message`     VARCHAR(500),
    `image`       MEDIUMBLOB,
    `receiver_id` BIGINT UNSIGNED NOT NULL,
    `posted`      DATETIME        NOT NULL,
    `version`     BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    KEY `C_34` (`receiver_id`),
    KEY `C_33` (`author`),
    CONSTRAINT `C_33` FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `C_34` FOREIGN KEY (`receiver_id`) REFERENCES `dialogs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
