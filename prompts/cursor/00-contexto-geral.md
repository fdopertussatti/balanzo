# Contexto geral — Balanzo

**Ponto de entrada para o Cursor AI.** Leia este arquivo primeiro para entender o projeto.

---

## O que é o Balanzo

Balanzo é um sistema de gestão financeira pessoal. Este repositório é um monorepo organizado por contexto.

## Aplicações

| Aplicação | Caminho | Stack | Descrição |
|-----------|---------|-------|-----------|
| Web | `apps/web/` | — | Aplicação principal do usuário final |
| Admin | `apps/admin/` | — | Painel administrativo |
| Site | `apps/site/` | — | Site institucional |
| Mobile | `apps/mobile/` | Flutter | App mobile |

## Backend

- **Caminho:** `services/backend/`
- **Stack:** Java (Spring Boot)
- **Papel:** API REST, regras de negócio, integrações

## Infraestrutura

- **Supabase:** Auth, banco Postgres, storage, funções serverless
- **Docker:** Containers para desenvolvimento e deploy
- **Configuração:** `infra/`

## Documentação obrigatória

Antes de implementar, consulte:

1. `docs/01-produto/documento-mestre-do-balanzo.md` — Visão do produto
2. `docs/02-arquitetura/arquitetura-tecnica-inicial.md` — Arquitetura
3. `docs/02-arquitetura/mapa-de-modulos-do-backend.md` — Módulos do backend
4. `docs/01-produto/requisitos-funcionais-consolidados.md` — Requisitos

## Prompts por área

- Backend: `prompts/cursor/10-backend/`
- Web: `prompts/cursor/20-web/`
- Admin: `prompts/cursor/30-admin/`
- Site: `prompts/cursor/40-site/`
- Mobile: `prompts/cursor/50-mobile/`
- Infra: `prompts/cursor/60-infra/`

## Regras gerais

- `prompts/cursor/01-regras-de-implementacao.md` — Regras de implementação
- `prompts/cursor/02-convencoes-de-codigo.md` — Convenções de código
- **Linguagem:** documentação em português; **código sempre em inglês** (variáveis, funções, classes, comentários)

---

*Atualize este arquivo conforme a stack e a documentação forem definidas.*
