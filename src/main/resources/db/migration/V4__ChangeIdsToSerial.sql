create sequence location_seq;
alter table location
alter COLUMN _id TYPE serial int
USING _id::integer,
alter column _id set default nextval('location_seq')

create sequence location_tag_seq;
alter table location_tag
alter COLUMN _id TYPE serial int
USING _id::integer,
alter column _id set default nextval('location_tag_seq')

create sequence org_user_data_seq;
alter table org_user_data
alter COLUMN _id TYPE serial int
USING _id::integer,
alter column _id set default nextval('org_user_data_seq')

create sequence org_user_seq
alter table org_user
alter COLUMN _id TYPE serial int
USING _id::integer,
alter column _id set default nextval('org_user_seq')

alter table location_tag_bridge
alter COLUMN location_id TYPE int, USING location_id::integer,
alter COLUMN tag_id TYPE int, USING tag_id::integer;

