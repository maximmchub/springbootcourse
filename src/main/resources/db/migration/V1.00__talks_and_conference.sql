CREATE TABLE talks
(
    id            BIGSERIAL primary key,
    caption       varchar(255) not null,
    description   varchar(255) not null,
    speaker       varchar(255) not null,
    talk_type     varchar(255) not null,
    conference_id BIGSERIAL
);

CREATE TABLE conference
(
    id            BIGSERIAL primary key,
    caption       varchar(255) not null,
    description   varchar(255) not null,
    capacity      integer,
    date_start    date,
    date_end      date
);
