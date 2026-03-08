# Balanzo

## Apresentação

Balanzo é um projeto de gestão financeira pessoal. Este repositório é um monorepo organizado por contexto.

## Estrutura do repositório

```
balanzo/
├── docs/           # Documentação oficial do projeto
├── prompts/        # Prompts e materiais para Cursor AI
├── apps/           # Aplicações de interface (web, admin, site, mobile)
├── services/       # Backend e serviços
├── packages/       # Pacotes compartilhados (tipos, UI, config, utils)
├── infra/          # Infraestrutura (Docker, CI/CD, Supabase)
├── tools/          # Ferramentas e geradores
└── tmp/            # Arquivos temporários (não versionados)
```

## Documentação oficial

Toda a documentação conceitual, arquitetural e funcional está em **`docs/`**:

- `docs/00-governanca/` — Visão, escopo, princípios e roadmap
- `docs/01-produto/` — Documento mestre, requisitos, módulos e prioridades
- `docs/02-arquitetura/` — Arquitetura técnica, dados, autenticação
- `docs/03-frontend/` — Frontend principal, admin e site
- `docs/04-mobile/` — App mobile
- `docs/05-backend/` — Domínios, API, jobs, storage
- `docs/06-devops/` — Ambientes, CI/CD, containers, observabilidade
- `docs/07-decisoes/` — ADRs (Architecture Decision Records)
- `docs/08-prompts-base/` — Prompts mestres por área

## Prompts para Cursor AI

Os prompts operacionais para implementação assistida estão em **`prompts/cursor/`**.

**Ponto de entrada:** `prompts/cursor/00-contexto-geral.md` — leia primeiro para entender o projeto.

## Aplicações

| Aplicação | Caminho      | Descrição                    |
|-----------|--------------|------------------------------|
| Web       | `apps/web/`  | Aplicação principal do usuário |
| Admin     | `apps/admin/`| Painel administrativo        |
| Site      | `apps/site/` | Site institucional           |
| Mobile    | `apps/mobile/` | App mobile (Flutter)       |

## Backend

Backend principal em Java: **`services/backend/`**

## Status atual

- Estrutura do monorepo criada
- Documentação e prompts em elaboração
- Código principal ainda não iniciado

## Convenções

- **Commits e push:** [Conventional Commits](docs/00-governanca/convencoes-de-commit-e-push.md)
- **Linguagem:** documentação em português, código em inglês — [detalhes](docs/00-governanca/convencoes-de-linguagem.md)

## Próximos passos

1. Consolidar documentação em `docs/`
2. Preencher `prompts/cursor/00-contexto-geral.md`
3. Iniciar backend em `services/backend/`
4. Iniciar frontends em `apps/`
