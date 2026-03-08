# Balanzo

## Apresentação

Balanzo é uma plataforma SaaS de organização financeira e familiar. Monorepo organizado por contexto.

**Visão completa:** `docs/01-produto/documento-mestre-do-balanzo.md`

---

## Estrutura do repositório

```
balanzo/
├── docs/           # Documentação oficial
├── prompts/        # Materiais para Cursor AI
├── apps/           # web, admin, site, mobile
├── services/       # Backend
├── packages/       # Compartilhados
├── infra/          # Docker, CI/CD, Supabase
└── tools/          # Ferramentas
```

**Detalhes:** `docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md`

---

## Ponto de entrada para Cursor AI

**`prompts/cursor/00-contexto-geral.md`** — leia primeiro.

---

## Documentação canônica

| Documento | Conteúdo |
|-----------|----------|
| `docs/01-produto/documento-mestre-do-balanzo.md` | Visão, escopo, aplicações |
| `docs/02-arquitetura/mapa-de-modulos-do-backend.md` | 12 módulos do backend |
| `docs/02-arquitetura/modelo-conceitual-de-dados.md` | Entidades, relacionamentos |
| `docs/02-arquitetura/requisitos-nao-funcionais.md` | Segurança, performance, qualidade |
| `docs/00-governanca/fontes-canonicas-do-projeto.md` | Mapa de fontes únicas |

---

## Stack

Java, Spring Boot, Next.js, Flutter, PostgreSQL, Supabase, Stripe, Docker

---

## Status

- Documentação consolidada
- Pronto para bootstrap do backend
- Ver `docs/00-governanca/analise-de-prontidao-para-implementacao.md`

---

## Convenções

- **Commits:** [Conventional Commits](docs/00-governanca/convencoes-de-commit-e-push.md)
- **Linguagem:** docs em português, código em inglês — [detalhes](docs/00-governanca/convencoes-de-linguagem.md)
