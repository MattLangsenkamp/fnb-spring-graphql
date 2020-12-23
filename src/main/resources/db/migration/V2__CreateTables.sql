create TABLE if NOT exists org_user(
    id int PRIMARY KEY generated always as identity,
    email VARCHAR(510) NOT NULL UNIQUE,
    user_password VARCHAR(60) NOT NULL,
    count INT NOT NULL,
    permission_level VARCHAR(60) NOT NULL
);

create TABLE if NOT exists location(
    id int PRIMARY KEY generated always as identity,
    location_name VARCHAR(255) NOT NULL,
    friendly_name VARCHAR(510) NOT NULL,
    description VARCHAR(765) NOT NULL,
    latitude NUMERIC NOT NULL
        CONSTRAINT lat_top_bound CHECK (latitude<=90),
        CONSTRAINT lat_bottom_bound CHECK (latitude>=-90),
    longitude NUMERIC NOT NULL
        CONSTRAINT long_top_bound CHECK (latitude<=180),
        CONSTRAINT long_bottom_bound CHECK (latitude>=-180),
    location_owner int NOT NULL REFERENCES org_user(id) ON delete CASCADE,
    picture_uri varchar(255) NOT NULL,
    needs_cleaning BOOLEAN NOT NULL,
    creation_date_time VARCHAR(255) NOT NULL
);

create TABLE if NOT exists location_tag(
    id int PRIMARY KEY generated always as identity,
    tag_name VARCHAR(255) NOT NULL,
    description VARCHAR(765) NOT NULL,
    owner_id int NOT NULL REFERENCES org_user(id)
);

create TABLE if NOT exists location_tag_bridge(
    location_id int NOT NULL REFERENCES location(id) ON delete CASCADE,
    tag_id int NOT NULL REFERENCES location_tag(id) ON delete CASCADE,
    PRIMARY KEY (location_id, tag_id)
);

DO $$ begin
    create type user_permission_level as ENUM ('USER', 'ADMIN', 'SUPER_ADMIN');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;


create TABLE if not exists org_user_data(
    id int PRIMARY KEY generated always as identity,
    org_user_id int NOT NULL REFERENCES org_user(id) ON delete CASCADE,
    username VARCHAR(255) NOT NULL,
    contact VARCHAR(510) NOT NULL,
    description VARCHAR(765) NOT NULL,
    picture_uri varchar(255) NOT NULL
);

