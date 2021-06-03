DROP TABLE IF EXISTS `friendships`;
DROP TABLE IF EXISTS `groups_memberships_requests`;
DROP TABLE IF EXISTS `friendships_requests`;
DROP TABLE IF EXISTS `groups_moderators`;
DROP TABLE IF EXISTS `groups_members`;
DROP TABLE IF EXISTS `passwords`;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `dialogs_messages`;
DROP TABLE IF EXISTS `group_wall_messages`;
DROP TABLE IF EXISTS `account_wall_messages`;
DROP TABLE IF EXISTS `dialogs`;
DROP TABLE IF EXISTS `phones`;
DROP TABLE IF EXISTS `groups`;
DROP TABLE IF EXISTS `accounts`;

CREATE TABLE IF NOT EXISTS `accounts`
(
    `id`
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    `name`
    VARCHAR
(
    40
) NOT NULL,
    `surname`           VARCHAR(40) NOT NULL,
    `patronymic`        VARCHAR(40),
    `sex`               CHAR(6),
    `birth_date`        DATE,
    `registration_date` DATE,
    `role`              CHAR(5) DEFAULT 'USER',
    `email`             VARCHAR(40) NOT NULL UNIQUE,
    `additional_email`  VARCHAR(40) UNIQUE,
    `icq`               VARCHAR(40),
    `skype`             VARCHAR(40),
    `city`              VARCHAR(40),
    `country`           VARCHAR(40),
    `photo`             MEDIUMBLOB,
    `version`           BIGINT      NOT NULL
);

ALTER TABLE `accounts`
    ADD CHECK (`sex` IN ('MALE', 'FEMALE'));

ALTER TABLE `accounts`
    ADD CHECK (`role` IN ('ADMIN', 'USER'));

CREATE TABLE IF NOT EXISTS `groups`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name`          VARCHAR(50) NOT NULL UNIQUE,
    `description`   VARCHAR(500),
    `owner_id`      BIGINT UNSIGNED,
    `creation_date` DATE,
    `photo`         MEDIUMBLOB,
    `version`       BIGINT      NOT NULL
);

ALTER TABLE `groups`
    ADD CONSTRAINT C_11 FOREIGN KEY (`owner_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS `groups_members`
(
    `account_id` BIGINT NOT NULL,
    `group_id`   BIGINT NOT NULL
);

ALTER TABLE `groups_members`
    ADD CONSTRAINT C_12 PRIMARY KEY (`account_id`, `group_id`);

ALTER TABLE `groups_members`
    ADD CONSTRAINT C_13 FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groups_members`
    ADD CONSTRAINT C_14 FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS `friendships`
(
    `first_account`  BIGINT UNSIGNED,
    `second_account` BIGINT UNSIGNED,
    PRIMARY KEY (`first_account`, `second_account`)
);

ALTER TABLE `friendships`
    ADD CONSTRAINT C_16 FOREIGN KEY (`first_account`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `friendships`
    ADD CONSTRAINT C_17 FOREIGN KEY (`second_account`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `friendships`
    ADD CHECK (`first_account` != `second_account`);

CREATE TABLE IF NOT EXISTS `phones`
(
    `id`       BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `number`   VARCHAR(50)     NOT NULL UNIQUE,
    `type`     CHAR(7),
    `owner_id` BIGINT UNSIGNED NOT NULL,
    `version`  BIGINT          NOT NULL
);

ALTER TABLE `phones`
    ADD CONSTRAINT C_18 FOREIGN KEY (`owner_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `phones`
    ADD CONSTRAINT C_19 CHECK (type IN ('PRIVATE', 'WORK'));

CREATE TABLE `passwords`
(
    `account_id` VARCHAR(40)  NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `version`    BIGINT       NOT NULL
);

ALTER TABLE `passwords`
    ADD CONSTRAINT C_20 PRIMARY KEY (`account_id`);

ALTER TABLE `passwords`
    ADD CONSTRAINT C_21 FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS `groups_moderators`
(
    `account_id` BIGINT UNSIGNED,
    `group_id`   BIGINT UNSIGNED,
    PRIMARY KEY (`account_id`, `group_id`)
);

ALTER TABLE `groups_moderators`
    ADD CONSTRAINT C_24 FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groups_moderators`
    ADD CONSTRAINT C_25 FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS `groups_memberships_requests`
(
    `account_id` BIGINT UNSIGNED,
    `group_id`   BIGINT UNSIGNED,
    PRIMARY KEY (`account_id`, `group_id`)
);

ALTER TABLE `groups_memberships_requests`
    ADD CONSTRAINT C_26 FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `groups_memberships_requests`
    ADD CONSTRAINT C_27 FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE IF NOT EXISTS `friendships_requests`
(
    `requester` BIGINT UNSIGNED,
    `receiver`  BIGINT UNSIGNED,
    PRIMARY KEY (`requester`, `receiver`)
);

ALTER TABLE `friendships_requests`
    ADD CONSTRAINT C_28 FOREIGN KEY (`requester`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `friendships_requests`
    ADD CONSTRAINT C_29 FOREIGN KEY (`receiver`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `account_wall_messages`
(
    `id`          bigint AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime NOT NULL,
    `version`     BIGINT   NOT NULL
);

ALTER TABLE `account_wall_messages`
    ADD CONSTRAINT C_31 FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `account_wall_messages`
    ADD CONSTRAINT C_32 FOREIGN KEY (`receiver_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `group_wall_messages`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime        NOT NULL,
    `version`     BIGINT          NOT NULL
);

ALTER TABLE `group_wall_messages`
    ADD CONSTRAINT C_35 FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `group_wall_messages`
    ADD CONSTRAINT C_36 FOREIGN KEY (`receiver_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE `dialogs`
(
    `id`        bigint unsigned auto_increment PRIMARY KEY,
    `first_id`  bigint unsigned NOT NULL,
    `second_id` bigint unsigned NOT NULL,
    `version`   BIGINT          NOT NULL,
    CONSTRAINT dialogs_accounts_id_fk FOREIGN KEY (`first_id`) REFERENCES `accounts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT dialogs_accounts_id_fk_2 FOREIGN KEY (`second_id`) REFERENCES `accounts` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT asd CHECK ( `first_id` > `second_id` )
);

ALTER TABLE `dialogs`
    ADD CONSTRAINT dialogs_accounts_id UNIQUE (`first_id`, `second_id`);

CREATE TABLE `dialogs_messages`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT,
    `author`      bigint unsigned NOT NULL,
    `message`     varchar(500),
    `image`       mediumblob,
    `receiver_id` bigint unsigned NOT NULL,
    `posted`      datetime        NOT NULL,
    `version`     BIGINT          NOT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `dialogs_messages`
    ADD CONSTRAINT C_33 FOREIGN KEY (`author`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `dialogs_messages`
    ADD CONSTRAINT C_34 FOREIGN KEY (`receiver_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

