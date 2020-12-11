create TABLE if NOT exists org_user(
    _id CHAR(36) PRIMARY KEY,
    password BYTEA NOT NULL,
    count INT NOT NULL,
    permission_level user_permission_level NOT NULL
);

create TABLE if NOT exists location(
    _id CHAR(36) PRIMARY KEY,
    _name VARCHAR(255) NOT NULL,
    friendly_name VARCHAR(510) NOT NULL,
    description VARCHAR(765) NOT NULL,
    latitude NUMERIC NOT NULL
        CONSTRAINT lat_top_bound CHECK (latitude<=90),
        CONSTRAINT lat_bottom_bound CHECK (latitude>=-90),
    longitude NUMERIC NOT NULL
        CONSTRAINT long_top_bound CHECK (latitude<=180),
        CONSTRAINT long_bottom_bound CHECK (latitude>=-180),
    location_owner CHAR(36) NOT NULL REFERENCES org_user(_id) ON delete CASCADE,
    picture_uri varchar(255) NOT NULL,
    needs_cleaning BOOLEAN NOT NULL,
    creationDateTime VARCHAR(255) NOT NULL
);

create TABLE if NOT exists location_tag(
    _id CHAR(36) PRIMARY KEY,
    tag_name VARCHAR(255) NOT NULL,
    description VARCHAR(765) NOT NULL
);

create TABLE if NOT exists location_tag_bridge(
    location_id CHAR(36) NOT NULL REFERENCES location(_id) ON delete CASCADE,
    tag_id CHAR(36) NOT NULL REFERENCES location_tag(_id) ON delete CASCADE,
    PRIMARY KEY (location_id, tag_id)
);

DO $$ begin
    create type user_permission_level as ENUM ('USER', 'ADMIN', 'SUPER_ADMIN');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;


create TABLE if not exists org_user_data(
    _id CHAR(36) PRIMARY KEY,
    org_user_id CHAR(36) NOT NULL REFERENCES org_user(_id) ON delete CASCADE,
    username VARCHAR(255) NOT NULL,
    contact VARCHAR(510) NOT NULL,
    description VARCHAR(765) NOT NULL,
    picture_uri varchar(255) NOT NULL
);

