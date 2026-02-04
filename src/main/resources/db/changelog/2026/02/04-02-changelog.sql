-- liquibase formatted sql

-- changeset salikhdev:1770209685479-1
CREATE TABLE schedule_days
(
    group_id    BIGINT NOT NULL,
    day_of_week VARCHAR(255)
);

-- changeset salikhdev:1770209685479-2
ALTER TABLE schedule_days
    ADD CONSTRAINT fk_schedule_days_on_group FOREIGN KEY (group_id) REFERENCES groups (id);

