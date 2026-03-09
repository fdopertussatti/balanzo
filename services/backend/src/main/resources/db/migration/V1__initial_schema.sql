-- Balanzo - Initial Schema
-- Based on: docs/02-arquitetura/modelo-conceitual-de-dados.md
-- User id references Supabase auth.users(id) - UUID

-- Enum types
CREATE TYPE user_status AS ENUM ('active', 'inactive', 'pending_deletion');
CREATE TYPE family_member_role AS ENUM ('owner', 'admin', 'member', 'child');
CREATE TYPE family_member_status AS ENUM ('active', 'invited', 'left');
CREATE TYPE account_type AS ENUM ('checking', 'savings', 'credit_card', 'digital_wallet', 'investment', 'cash');
CREATE TYPE transaction_type AS ENUM ('income', 'expense', 'transfer');
CREATE TYPE category_type AS ENUM ('income', 'expense');
CREATE TYPE owner_scope AS ENUM ('user', 'family');
CREATE TYPE task_status AS ENUM ('todo', 'in_progress', 'done', 'cancelled');
CREATE TYPE task_priority AS ENUM ('low', 'medium', 'high');
CREATE TYPE visibility_scope AS ENUM ('private', 'shared_read', 'shared_edit', 'shared_manage', 'analytical_only');
CREATE TYPE subscription_status AS ENUM ('active', 'canceled', 'past_due', 'trialing');
CREATE TYPE notification_status AS ENUM ('unread', 'read', 'dismissed');

-- Users (domain identity - links to Supabase auth.users via id)
CREATE TABLE "user" (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255),
    status user_status NOT NULL DEFAULT 'active',
    default_currency VARCHAR(3) NOT NULL DEFAULT 'BRL',
    timezone VARCHAR(64) NOT NULL DEFAULT 'America/Sao_Paulo',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Families
CREATE TABLE family (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    created_by UUID NOT NULL REFERENCES "user"(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Family members
CREATE TABLE family_member (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    family_id UUID NOT NULL REFERENCES family(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    role family_member_role NOT NULL DEFAULT 'member',
    status family_member_status NOT NULL DEFAULT 'active',
    joined_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(family_id, user_id)
);

CREATE INDEX idx_family_member_family ON family_member(family_id);
CREATE INDEX idx_family_member_user ON family_member(user_id);

-- Categories
CREATE TABLE category (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    type category_type NOT NULL,
    parent_id UUID REFERENCES category(id) ON DELETE SET NULL,
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_category_parent ON category(parent_id);
CREATE INDEX idx_category_owner_user ON category(owner_user_id);
CREATE INDEX idx_category_owner_family ON category(owner_family_id);

-- Plans (for subscription)
CREATE TABLE plan (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    stripe_price_id VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Accounts
CREATE TABLE account (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_user_id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    family_id UUID REFERENCES family(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    type account_type NOT NULL,
    currency VARCHAR(3) NOT NULL DEFAULT 'BRL',
    institution VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_account_owner ON account(owner_user_id);
CREATE INDEX idx_account_family ON account(family_id);

-- Transactions
CREATE TABLE transaction (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    type transaction_type NOT NULL,
    category_id UUID REFERENCES category(id) ON DELETE SET NULL,
    description VARCHAR(500),
    date DATE NOT NULL,
    visibility_scope visibility_scope NOT NULL DEFAULT 'private',
    created_by UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transaction_account ON transaction(account_id);
CREATE INDEX idx_transaction_date ON transaction(date);
CREATE INDEX idx_transaction_category ON transaction(category_id);
CREATE INDEX idx_transaction_created_by ON transaction(created_by);

-- Budgets
CREATE TABLE budget (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    category_id UUID REFERENCES category(id) ON DELETE CASCADE,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_budget_owner_user ON budget(owner_user_id);
CREATE INDEX idx_budget_owner_family ON budget(owner_family_id);
CREATE INDEX idx_budget_period ON budget(period_start, period_end);

-- Goals
CREATE TABLE goal (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    target_amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    target_date DATE,
    current_progress DECIMAL(19, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_goal_owner_user ON goal(owner_user_id);
CREATE INDEX idx_goal_owner_family ON goal(owner_family_id);

-- Assets
CREATE TABLE asset (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    estimated_value DECIMAL(19, 4),
    currency VARCHAR(3),
    valuation_date DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_asset_owner_user ON asset(owner_user_id);
CREATE INDEX idx_asset_owner_family ON asset(owner_family_id);

-- Tasks
CREATE TABLE task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    family_id UUID NOT NULL REFERENCES family(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    assigned_to UUID REFERENCES "user"(id) ON DELETE SET NULL,
    status task_status NOT NULL DEFAULT 'todo',
    priority task_priority NOT NULL DEFAULT 'medium',
    due_date DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_task_family ON task(family_id);
CREATE INDEX idx_task_assigned ON task(assigned_to);
CREATE INDEX idx_task_status ON task(status);

-- Task templates
CREATE TABLE task_template (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    template_structure JSONB,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Documents
CREATE TABLE document (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    file_name VARCHAR(255) NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    document_type VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_document_owner_user ON document(owner_user_id);
CREATE INDEX idx_document_owner_family ON document(owner_family_id);

-- Notifications
CREATE TABLE notification (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    type VARCHAR(100) NOT NULL,
    message TEXT,
    status notification_status NOT NULL DEFAULT 'unread',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_notification_user ON notification(user_id);
CREATE INDEX idx_notification_status ON notification(status);

-- Subscriptions
CREATE TABLE subscription (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_scope owner_scope NOT NULL,
    owner_user_id UUID REFERENCES "user"(id) ON DELETE CASCADE,
    owner_family_id UUID REFERENCES family(id) ON DELETE CASCADE,
    plan_id UUID NOT NULL REFERENCES plan(id) ON DELETE RESTRICT,
    status subscription_status NOT NULL,
    stripe_customer_id VARCHAR(255),
    stripe_subscription_id VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_subscription_owner_user ON subscription(owner_user_id);
CREATE INDEX idx_subscription_owner_family ON subscription(owner_family_id);
CREATE INDEX idx_subscription_stripe ON subscription(stripe_subscription_id);

-- Enable RLS placeholder (Supabase): tables exist for backend; RLS policies can be added in Supabase project
