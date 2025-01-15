--Description: insert_data_catalog
--User: add your name or user here
-- Date: 2025-01-11 16:34:01
-- SQL: Add the SQL script here
-- Tags
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Planning', '#e04891', current_timestamp, current_timestamp, 0);
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Design', '#e1b7ed', current_timestamp, current_timestamp, 0);
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Coding', '#f5e1e2', current_timestamp, current_timestamp, 0);
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Testing', '#d1e389', current_timestamp, current_timestamp, 0);
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Deployment', '#b9de51', current_timestamp, current_timestamp, 0);
INSERT INTO public.tags ( name, color, created_date, last_modified_date, version)
VALUES ('Maintenance', '#00b5b9', current_timestamp, current_timestamp, 0);

-- Collaborators
INSERT INTO public.collaborators (name, email, created_date, last_modified_date, version)
VALUES ('Rafael Galvez', 'rafael.galvez@gmail.com', current_timestamp, current_timestamp, 0);
INSERT INTO public.collaborators (name, email, created_date, last_modified_date, version)
VALUES ('Pablo Gonzalez', 'pablo.gonzalez@gmail.com', current_timestamp, current_timestamp, 0);
INSERT INTO public.collaborators (name, email, created_date, last_modified_date, version)
VALUES ('Manuel Garcia', 'manuel.garcia@gmail.com', current_timestamp, current_timestamp, 0);
INSERT INTO public.collaborators (name, email, created_date, last_modified_date, version)
VALUES ('Maria Lopez', 'maria.lopez@gmail.com', current_timestamp, current_timestamp, 0);
INSERT INTO public.collaborators (name, email, created_date, last_modified_date, version)
VALUES ('JJulia Ramirez', 'julia.ramirez@gmail.com', current_timestamp, current_timestamp, 0);

-- Users
INSERT INTO public.users (username, name, email, password, status, created_date, last_modified_date, version)
VALUES ('rafaelgalvezg', 'Rafael Galvez', 'rafael.galvez@gmail.com', '$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.', 'ACTIVE', current_timestamp, current_timestamp, 0);
INSERT INTO public.users (username, name, email, password, status, created_date, last_modified_date, version)
VALUES ('pablogonzalez', 'Pablo Gonzalez', 'pablo.gonzalez@gmail.com', '$2a$10$ju20i95JTDkRa7Sua63JWOChSBc0MNFtG/6Sps2ahFFqN.HCCUMW.', 'ACTIVE', current_timestamp, current_timestamp, 0);

-- Roles
INSERT INTO public.roles (name, description, created_date, last_modified_date, version)
VALUES ('ADMIN', 'Administrador', current_timestamp, current_timestamp, 0);
INSERT INTO public.roles (name, description, created_date, last_modified_date, version)
VALUES ('USER', 'Usuario', current_timestamp, current_timestamp, 0);

-- User Role
INSERT INTO public.user_role (id_user, id_role) VALUES (1, 1);
INSERT INTO public.user_role (id_user, id_role) VALUES (2, 2);

-- Permissions
INSERT INTO public.permissions (name, url, action, icon, created_date, last_modified_date, version)
VALUES ('Dashboard', '/features/dashboard', null, 'home', current_timestamp, current_timestamp, 0);
INSERT INTO public.permissions (name, url, action, icon, created_date, last_modified_date, version)
VALUES ('Projects', '/features/projects', null, 'dashboard', current_timestamp, current_timestamp, 0);
INSERT INTO public.permissions (name, url, action, icon, created_date, last_modified_date, version)
VALUES ('Collaborators', '/features/collaborators', null, 'person', current_timestamp, current_timestamp, 0);
INSERT INTO public.permissions (name, url, action, icon, created_date, last_modified_date, version)
VALUES ('Tags', '/features/tags', null, 'local_offer', current_timestamp, current_timestamp, 0);

-- Role Permissions
INSERT INTO public.role_permission (id_role, id_permission) VALUES (1, 1);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (1, 2);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (1, 3);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (1, 4);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (2, 1);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (2, 2);
INSERT INTO public.role_permission (id_role, id_permission) VALUES (2, 4);