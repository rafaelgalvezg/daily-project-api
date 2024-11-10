-- Rollback generated from: V20241026120907__initial_migration.sql
--User: add your name or user here
-- Date: 2024-10-26 12:09:13
-- SQL: Add the SQL script here
-- Rollback Script
-- Drop Many-to-Many Relationship Tables
DROP TABLE IF EXISTS task_tag CASCADE;
DROP TABLE IF EXISTS role_permission CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;

-- Drop Tables
DROP TABLE IF EXISTS change_tracking CASCADE;
DROP TABLE IF EXISTS tags CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS project_team CASCADE;
DROP TABLE IF EXISTS projects CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS collaborators CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Drop ENUM Types
DROP TYPE IF EXISTS project_role CASCADE;
DROP TYPE IF EXISTS status CASCADE;
DROP TYPE IF EXISTS task_priority CASCADE;
DROP TYPE IF EXISTS user_status CASCADE;
