drop ALL OBJECTS;

create TABLE IF NOT EXISTS Accounts (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    patronymic VARCHAR(40),
    sex CHAR(6),
    birth_date DATE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    additional_phone VARCHAR(20),
    email VARCHAR(40) NOT NULL UNIQUE,
    additional_email VARCHAR(40) UNIQUE,
    icq VARCHAR(40),
    skype VARCHAR(40),
    city VARCHAR(40),
    country VARCHAR(40)
);

create TABLE IF NOT EXISTS _Groups (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500),
    owner_id INT UNSIGNED NOT NULL
);

alter table _Groups
add CONSTRAINT C_11 FOREIGN KEY (owner_id) REFERENCES Accounts (id) ON delete CASCADE;

create TABLE IF NOT EXISTS Accounts_in_groups (
    account_id INT UNSIGNED NOT NULL,
    group_id INT UNSIGNED NOT NULL
);

alter table Accounts_in_groups
add CONSTRAINT C_12 PRIMARY KEY (account_id, group_id);

alter table Accounts_in_groups
add CONSTRAINT C_13 FOREIGN KEY (account_id) REFERENCES Accounts (id) ON delete CASCADE;

alter table Accounts_in_groups
add CONSTRAINT C_14 FOREIGN KEY (group_id) REFERENCES _Groups (id) ON delete CASCADE;

insert into Accounts (name, surname, phone, email) values
('Vladimir', 'Lenin', '1918', 'rise@communism'),
('dracula', 'NOSFERATU', '666', 'drink@blood.com'),
('Alan', 'Turing', '121314', 'robot@power.com'),
('Petr', 'Popov', '8921-849-43-42', 'popovp@gmail.com');

insert into _Groups (name, description, owner_id) values
('USSR fans', 'building Communism', 1),
('machine learning', '', 3);

insert into Accounts_in_groups values
(1, 1), (2, 1), (3, 2), (1, 2);
