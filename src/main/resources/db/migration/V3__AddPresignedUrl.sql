alter table "location"
drop COLUMN
IF EXISTS picture_uri,
drop COLUMN
IF EXISTS pre_signed_url ;

alter table org_user_data
drop COLUMN
IF EXISTS picture_uri ;


create table if not exists image_url(
  id        int PRIMARY KEY generated always as identity,
  type      VARCHAR(250) NOT NULL,
  owner_id  int NOT NULL,
  image_uri VARCHAR(1500) NOT NULL
);
