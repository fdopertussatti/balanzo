# Balanzo Web

Frontend web da plataforma Balanzo. Next.js 16, Supabase Auth, Tailwind CSS.

## Pré-requisitos

- Node.js 18+
- Projeto Supabase (gratuito em [supabase.com](https://supabase.com))
- Backend rodando em `http://localhost:8080` (ou configurar `NEXT_PUBLIC_API_URL`)

## Setup

```bash
# Instalar dependências
npm install

# Copiar variáveis de ambiente
cp .env.example .env.local

# Editar .env.local e preencher:
# - NEXT_PUBLIC_SUPABASE_URL
# - NEXT_PUBLIC_SUPABASE_ANON_KEY
# - NEXT_PUBLIC_API_URL (opcional, default: http://localhost:8080)
```

## Rodar

```bash
# Desenvolvimento
npm run dev

# Build
npm run build

# Produção
npm start
```

Acesse `http://localhost:3000` (ou a porta indicada no terminal).

## Fluxo

1. **Login** — Supabase Auth (email/senha)
2. **Dashboard** — Perfil, contas, transações (via API backend)
3. **Backend** — Deve estar rodando e com JWT validado para `/api/**`

## Backend + Supabase

Para o backend validar o JWT do Supabase, configure:

```properties
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://<seu-projeto>.supabase.co/auth/v1/.well-known/jwks.json
```

E garanta que o trigger `auth_user_sync` (V2) sincronize `auth.users` → `public.user`.
