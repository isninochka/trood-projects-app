CREATE TABLE IF NOT EXISTS trood_project (
                                             id SERIAL PRIMARY KEY,
                                             name VARCHAR(255),
                                             description TEXT
);

CREATE TABLE IF NOT EXISTS vacancy (
                                       id SERIAL PRIMARY KEY,
                                       title VARCHAR(255) NOT NULL,
                                       description VARCHAR(1000) NOT NULL,
                                       project_id BIGINT NOT NULL,
                                       CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES trood_project (id) ON DELETE CASCADE
);
