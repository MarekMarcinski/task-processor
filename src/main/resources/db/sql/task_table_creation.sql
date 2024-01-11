CREATE TABLE TASK
(
    id       bigserial PRIMARY KEY,
    uuid     uuid not null,
    input    varchar,
    pattern  varchar,
    status   varchar,
    progress integer,
    position integer,
    typos    integer
);
--------------------------------------------------------