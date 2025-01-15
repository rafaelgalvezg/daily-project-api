-- Rollback generated from: V20250111163401__insert_data_catalog.sql
--User: add your name or user here
-- Date: 2025-01-11 16:34:54
-- SQL: Add the SQL script here

-- Rollback for tags
DELETE FROM public.tags WHERE name IN ('Planning', 'Design', 'Coding', 'Testing', 'Deployment', 'Maintenance');

-- Rollback for collaborators
DELETE FROM public.collaborators WHERE email IN ('rafael.galvez@gmail.com', 'pablo.gonzalez@gmail.com', 'manuel.garcia@gmail.com', 'maria.lopez@gmail.com', 'julia.ramirez@gmail.com');

-- Rollback for users
DELETE FROM public.users WHERE username IN ('rafaelgalvezg', 'pablogonzalez');

-- Rollback for roles
DELETE FROM public.roles WHERE name IN ('ADMIN', 'USER');

-- Rollback for user_role
DELETE FROM public.user_role WHERE id_user IN (1, 2);

-- Rollback for permissions
DELETE FROM public.permissions WHERE name IN ('Dashboard', 'Projects', 'Collaborators', 'Tags');

-- Rollback for role_permission
DELETE FROM public.role_permission WHERE id_role IN (1, 2);