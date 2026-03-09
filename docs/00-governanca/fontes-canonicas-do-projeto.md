# Fontes Canônicas do Projeto Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## Objetivo

Este documento define **onde está a verdade única** para cada tema do projeto. Outros documentos devem **referenciar** essas fontes, não duplicar.

---

## Mapa de fontes canônicas

| Tema | Fonte canônica | Outros documentos referenciam |
|------|----------------|------------------------------|
| O que é o Balanzo, visão, escopo, público | `docs/01-produto/documento-mestre-do-balanzo.md` | 00-contexto, README |
| Stack, arquitetura geral, aplicações | `docs/01-produto/documento-mestre-do-balanzo.md` (seções 17-18) | 00-contexto |
| 12 módulos do backend, entidades por módulo | `docs/02-arquitetura/mapa-de-modulos-do-backend.md` | 00-contexto, 01-regras, 10-backend/* |
| Entidades, relacionamentos, modelo de dados | `docs/02-arquitetura/modelo-conceitual-de-dados.md` | 01-estrutura |
| Segurança, performance, qualidade, integrações | `docs/02-arquitetura/requisitos-nao-funcionais.md` | 00-contexto, 01-regras |
| Estrutura de pastas, organização do monorepo | `docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md` | 00-contexto, README |
| Convenções de commit | `docs/00-governanca/convencoes-de-commit-e-push.md` | 01-regras |
| Convenções de linguagem (PT/EN) | `docs/00-governanca/convencoes-de-linguagem.md` | 00-contexto |
| Regras operacionais do Cursor | `prompts/cursor/01-regras-de-implementacao.md` | 00-contexto |
| Estrutura do backend (camadas, pacotes) | `prompts/cursor/10-backend/01-estrutura-do-backend.md` | 00-inicializar |
| Bootstrap do backend | `prompts/cursor/10-backend/00-inicializar-backend.md` | — |
| Estratégia de autorização familiar | `docs/02-arquitetura/estrategia-de-autorizacao-e-compartilhamento-familiar.md` | RNF, estrategia-auth |
| Análise de prontidão | `docs/00-governanca/analise-de-prontidao-para-implementacao.md` | README |

---

## Regra de ouro

**Não duplicar.** Se o tema já está em uma fonte canônica, use: *"Ver [documento]"* ou *"Conforme [documento], ..."*.
