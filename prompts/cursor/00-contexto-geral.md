# Ponto de Entrada — Balanzo para Cursor AI

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## Finalidade

Este documento é o **ponto de entrada oficial** do projeto Balanzo para o Cursor AI. Antes de gerar código, leia os documentos obrigatórios e siga as referências.

---

## Visão em uma frase

O Balanzo é uma plataforma SaaS de organização financeira e familiar (individual, casal, família). **Fonte completa:** `docs/01-produto/documento-mestre-do-balanzo.md`

---

## Arquitetura resumida

- **Backend:** Java + Spring Boot (centro do domínio)
- **Frontends:** Next.js (web, admin, site)
- **Mobile:** Flutter
- **Infraestrutura:** Supabase (Auth, PostgreSQL, Storage), Stripe, Docker

O Supabase não concentra regras de negócio. **Fonte:** `docs/01-produto/documento-mestre-do-balanzo.md` (seções 17-18)

---

## Estrutura do repositório

`docs/` | `prompts/` | `apps/` | `services/` | `packages/` | `infra/` | `tools/`

**Fonte completa:** `docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md`

---

## Documentos obrigatórios

Antes de implementar, consulte:

| Documento | O que contém |
|-----------|--------------|
| `docs/01-produto/documento-mestre-do-balanzo.md` | Visão, escopo, aplicações |
| `docs/02-arquitetura/mapa-de-modulos-do-backend.md` | 12 módulos, entidades |
| `docs/02-arquitetura/modelo-conceitual-de-dados.md` | User, Family, Account, Transaction, etc. |
| `docs/02-arquitetura/requisitos-nao-funcionais.md` | Segurança, performance, qualidade |
| `docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md` | Organização do monorepo |
| `prompts/cursor/01-regras-de-implementacao.md` | Regras operacionais de código |

---

## Cinco princípios obrigatórios

1. **Backend = centro do domínio** — regras no Java/Spring, não no frontend nem no Supabase
2. **Implementar por módulo** — ver `docs/02-arquitetura/mapa-de-modulos-do-backend.md`
3. **web, admin, site separados** — aplicações distintas
4. **Propriedade, visibilidade, participação** — diferenciar sempre (ver Modelo Conceitual)
5. **Preservar histórico** — mudanças não destroem valor analítico

---

## Regras de comportamento do Cursor

- Ler documentos relevantes antes de gerar código estrutural
- Evitar soluções que conflitem com os docs oficiais
- Não espalhar regras de negócio em camadas inadequadas
- Explicitar quando uma sugestão altera a arquitetura

**Regras detalhadas:** `prompts/cursor/01-regras-de-implementacao.md`

---

## Prompts por área

- **Backend:** `prompts/cursor/10-backend/00-inicializar-backend.md`, `01-estrutura-do-backend.md`
- **Regras gerais:** `prompts/cursor/01-regras-de-implementacao.md`
- **Convenções de código:** `prompts/cursor/02-convencoes-de-codigo.md`

---

## Convenções de linguagem

- **Documentação:** português  
- **Código:** inglês (variáveis, funções, classes, nomes de arquivos)

**Fonte:** `docs/00-governanca/convencoes-de-linguagem.md`

---

## Mapa de fontes canônicas

Para saber onde está a verdade única de cada tema: `docs/00-governanca/fontes-canonicas-do-projeto.md`

---

## Próximo passo

Bootstrap do backend: `prompts/cursor/10-backend/00-inicializar-backend.md`  
Análise de prontidão: `docs/00-governanca/analise-de-prontidao-para-implementacao.md`
