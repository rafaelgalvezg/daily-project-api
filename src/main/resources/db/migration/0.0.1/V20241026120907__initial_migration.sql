--Description: initial_migration
--User: add your name or user here
-- Date: 2024-10-26 12:09:07
-- SQL: Add the SQL script here
-- Create ENUM types
CREATE TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE', 'BLOCKED', 'SUSPENDED');
CREATE TYPE task_priority AS ENUM ('LOW', 'MEDIUM', 'HIGH');
CREATE TYPE status AS ENUM ('PLANNING','IN_PROGRESS', 'DONE', 'PAUSED', 'BACKLOG', 'CANCELLED');
CREATE TYPE project_role AS ENUM ('PROJECT_LEADER', 'PRODUCT_MANAGER', 'DEVELOPER', 'STAKEHOLDER', 'QUALITY_ASSURANCE', 'BUSINESS_ANALYST', 'OTHER');

-- Table: Users
CREATE TABLE users
(
    id_user           INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username          VARCHAR(50) UNIQUE NOT NULL,
    name              VARCHAR(255)        NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(255)        NOT NULL,
    status            user_status              DEFAULT 'INACTIVE',
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN users.id_user IS 'Unique ID for each user';
COMMENT ON COLUMN users.username IS 'Username of the user, must be unique';
COMMENT ON COLUMN users.name IS 'Full name of the user';
COMMENT ON COLUMN users.email IS 'Email address of the user, must be unique';
COMMENT ON COLUMN users.password IS 'Encrypted password of the user';
COMMENT ON COLUMN users.status IS 'Current status of the user (ENUM)';
COMMENT ON COLUMN users.created_date IS 'Timestamp when the user was created';
COMMENT ON COLUMN users.last_modified_date IS 'Timestamp when the user was last modified';
COMMENT ON COLUMN users.version IS 'Version of the user record';

-- Table: Roles
CREATE TABLE roles
(
    id_role           INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(100) NOT NULL,
    description       VARCHAR(500),
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN roles.id_role IS 'Unique ID for each role';
COMMENT ON COLUMN roles.name IS 'Name of the role (e.g., Scrum Master, Product Owner)';
COMMENT ON COLUMN roles.description IS 'Description of the role';
COMMENT ON COLUMN roles.created_date IS 'Timestamp when the role was created';
COMMENT ON COLUMN roles.last_modified_date IS 'Timestamp when the role was last modified';
COMMENT ON COLUMN roles.version IS 'Version of the role record';

-- Table: Permissions
CREATE TABLE permissions
(
    id_permission     INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(100) NOT NULL,
    url               VARCHAR(255),
    action            VARCHAR(50),
    icon              VARCHAR(50),
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN permissions.id_permission IS 'Unique ID for each permission';
COMMENT ON COLUMN permissions.name IS 'Name of the permission';
COMMENT ON COLUMN permissions.url IS 'URL associated with the permission';
COMMENT ON COLUMN permissions.action IS 'Action associated with the permission';
COMMENT ON COLUMN permissions.icon IS 'Icon associated with the permission';
COMMENT ON COLUMN permissions.created_date IS 'Timestamp when the permission was created';
COMMENT ON COLUMN permissions.last_modified_date IS 'Timestamp when the permission was last modified';
COMMENT ON COLUMN permissions.version IS 'Version of the permission record';

-- Table: Collaborators (new table)
CREATE TABLE collaborators
(
    id_collaborator   INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(255) NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN collaborators.id_collaborator IS 'Unique ID for each collaborator';
COMMENT ON COLUMN collaborators.name IS 'Name of the collaborator';
COMMENT ON COLUMN collaborators.email IS 'Email of the collaborator';
COMMENT ON COLUMN collaborators.created_date IS 'Timestamp when the collaborator was created';
COMMENT ON COLUMN collaborators.last_modified_date IS 'Timestamp when the collaborator was last modified';
COMMENT ON COLUMN collaborators.version IS 'Version of the collaborator record';

-- Table: Projects
CREATE TABLE projects
(
    id_project        INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(255) NOT NULL,
    description       VARCHAR(500),
    start_date        DATE,
    end_date          DATE,
    status            status DEFAULT 'PLANNING',
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN projects.id_project IS 'Unique ID for each project';
COMMENT ON COLUMN projects.name IS 'Name of the project';
COMMENT ON COLUMN projects.description IS 'Description of the project';
COMMENT ON COLUMN projects.start_date IS 'Start date of the project';
COMMENT ON COLUMN projects.end_date IS 'End date of the project';
COMMENT ON COLUMN projects.status IS 'Current status of the project (ENUM)';
COMMENT ON COLUMN projects.created_date IS 'Timestamp when the project was created';
COMMENT ON COLUMN projects.last_modified_date IS 'Timestamp when the project was last modified';
COMMENT ON COLUMN projects.version IS 'Version of the project record';

-- New Table: Project_Team
CREATE TABLE project_team
(
    id_project      INTEGER REFERENCES projects (id_project),
    id_collaborator INTEGER REFERENCES collaborators (id_collaborator),
    role            project_role,
    PRIMARY KEY (id_project, id_collaborator)
);
COMMENT ON COLUMN project_team.id_project IS 'ID of the project';
COMMENT ON COLUMN project_team.id_collaborator IS 'ID of the collaborator in the project team';
COMMENT ON COLUMN project_team.role IS 'Role of the collaborator in the project (ENUM)';


-- Table: Tasks
CREATE TABLE tasks
(
    id_task            INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title              VARCHAR(255) NOT NULL,
    description        VARCHAR(1000),
    start_date         DATE,
    end_date           DATE,
    status              status DEFAULT 'PLANNING',
    priority           task_priority,
    order_task         INTEGER,
    assigned_collaborator INTEGER REFERENCES collaborators (id_collaborator),
    associated_project INTEGER REFERENCES projects (id_project),
    created_date        TIMESTAMP,
    last_modified_date TIMESTAMP,
    version            INTEGER
);
COMMENT ON COLUMN tasks.id_task IS 'Unique ID for each task';
COMMENT ON COLUMN tasks.title IS 'Title of the task';
COMMENT ON COLUMN tasks.description IS 'Description of the task';
COMMENT ON COLUMN tasks.start_date IS 'Start date of the task';
COMMENT ON COLUMN tasks.end_date IS 'End date of the task';
COMMENT ON COLUMN tasks.status IS 'Current status of the task (ENUM)';
COMMENT ON COLUMN tasks.priority IS 'Priority of the task (ENUM)';
COMMENT ON COLUMN tasks.order_task IS 'Order of the task in the list';
COMMENT ON COLUMN tasks.assigned_collaborator IS 'Collaborator assigned to the task';
COMMENT ON COLUMN tasks.associated_project IS 'Project associated with the task';
COMMENT ON COLUMN tasks.created_date IS 'Timestamp when the task was created';
COMMENT ON COLUMN tasks.last_modified_date IS 'Timestamp when the task was last modified';
COMMENT ON COLUMN tasks.version IS 'Version of the task record';

-- Table: Tags
CREATE TABLE tags
(
    id_tag            INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name              VARCHAR(50) NOT NULL,
    color             VARCHAR(50),
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    version           INTEGER
);
COMMENT ON COLUMN tags.id_tag IS 'Unique ID for each tag';
COMMENT ON COLUMN tags.name IS 'Name of the tag';
COMMENT ON COLUMN tags.color IS 'Color associated with the tag';
COMMENT ON COLUMN tags.created_date IS 'Timestamp when the tag was created';
COMMENT ON COLUMN tags.last_modified_date IS 'Timestamp when the tag was last modified';
COMMENT ON COLUMN tags.version IS 'Version of the tag record';

-- Table: Change_Tracking
CREATE TABLE change_tracking
(
    id_tracking       INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_task           INTEGER REFERENCES tasks (id_task),
    field_changed     VARCHAR(255),
    description       VARCHAR(1000),
    change_date       TIMESTAMP,
    collaborator_responsible INTEGER REFERENCES collaborators (id_collaborator),
    version           INTEGER
);
COMMENT ON COLUMN change_tracking.id_tracking IS 'Unique ID for each change record';
COMMENT ON COLUMN change_tracking.id_task IS 'Task associated with the change';
COMMENT ON COLUMN change_tracking.field_changed IS 'Field that was changed';
COMMENT ON COLUMN change_tracking.description IS 'Description of the change';
COMMENT ON COLUMN change_tracking.change_date IS 'Timestamp when the change was made';
COMMENT ON COLUMN change_tracking.collaborator_responsible IS 'Collaborator responsible for the change';
COMMENT ON COLUMN change_tracking.version IS 'Version of the change record';

-- Many-to-Many Relationship Tables
CREATE TABLE user_role
(
    id_user INTEGER REFERENCES users (id_user),
    id_role INTEGER REFERENCES roles (id_role),
    PRIMARY KEY (id_user, id_role)
);
COMMENT ON COLUMN user_role.id_user IS 'ID of the user';
COMMENT ON COLUMN user_role.id_role IS 'ID of the assigned role';

CREATE TABLE role_permission
(
    id_role       INTEGER REFERENCES roles (id_role),
    id_permission INTEGER REFERENCES permissions (id_permission),
    PRIMARY KEY (id_role, id_permission)
);
COMMENT ON COLUMN role_permission.id_role IS 'ID of the role';
COMMENT ON COLUMN role_permission.id_permission IS 'ID of the permission';

CREATE TABLE task_tag
(
    id_task INTEGER REFERENCES tasks (id_task),
    id_tag  INTEGER REFERENCES tags (id_tag),
    PRIMARY KEY (id_task, id_tag)
);
COMMENT ON COLUMN task_tag.id_task IS 'ID of the task';
COMMENT ON COLUMN task_tag.id_tag IS 'ID of the tag';

-- Indices
CREATE INDEX idx_roles_name ON roles (name);
CREATE INDEX idx_permissions_name ON permissions (name);
CREATE INDEX idx_tasks_assigned_collaborator ON tasks (assigned_collaborator);
CREATE INDEX idx_change_tracking_task ON change_tracking (id_task);
CREATE INDEX idx_projects_start_date ON projects (start_date);
CREATE INDEX idx_projects_end_date ON projects (end_date);
CREATE INDEX idx_tasks_start_date ON tasks (start_date);
CREATE INDEX idx_tasks_end_date ON tasks (end_date);
