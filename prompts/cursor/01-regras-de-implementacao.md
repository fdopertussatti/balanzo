# Regras de Implementação e Convenções de Código

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## Finalidade

Define regras práticas que o Cursor AI deve seguir ao gerar ou modificar código.

**Conceitos e decisões arquiteturais:** ver docs em `prompts/cursor/00-contexto-geral.md` (Documentos obrigatórios).

---

## Regra central

A lógica de negócio vive no backend. Frontend = interface e UX. Supabase = infraestrutura, não regras de domínio.

**Fonte:** `docs/01-produto/documento-mestre-do-balanzo.md`, `docs/02-arquitetura/requisitos-nao-funcionais.md`

---

## Princípios gerais

Clareza estrutural, separação de responsabilidades, baixo acoplamento, domínio no backend, privacidade/segurança por padrão, código testável, evolução incremental.

---

## Convenções de código

### Nomes

Bons: `FamilyMember`, `FinancialTransaction`, `SharedExpense`, `FinancialGoal`, `AssetPosition`  
Evitar: `DataObject`, `Manager`, `Helper`, `Util`, `Stuff`, `Thing`

### Estrutura

- Arquivos com responsabilidade única
- Evitar lógica duplicada
- Compartilhamento em `packages/` só quando justificado

---

## Backend Java com Spring

**Módulos:** ver `docs/02-arquitetura/mapa-de-modulos-do-backend.md`  
**Estrutura interna:** ver `prompts/cursor/10-backend/01-estrutura-do-backend.md`

- Controllers finos: receber, validar, delegar, retornar
- Lógica no domínio ou application
- Repositórios só persistência
- Integrações encapsuladas (Supabase, Stripe)
- Erros: exceções de domínio, sem retorno silencioso

---

## APIs

- Estrutura: `/api/v1/users`, `/api/v1/transactions`, etc.
- Respostas estruturadas, paginação
- Segurança: identidade, escopo, propriedade do dado

---

## Next.js, Flutter

- web, admin, site separados
- Sem lógica crítica no frontend
- Mobile: fluxos simples, consumir APIs

---

## Banco, Stripe, testes

- PostgreSQL, Supabase, migrations versionadas
- Stripe encapsulado no módulo monetizacao
- Testes: unitários, integração, fluxos críticos

---

## Commits

Ver `docs/00-governanca/convencoes-de-commit-e-push.md` (Conventional Commits)

---

## Novos módulos e alterações estruturais

Antes de criar módulo: verificar se já existe em `docs/02-arquitetura/mapa-de-modulos-do-backend.md`.  
Alterações que impactam arquitetura: explicitar antes de implementar.

---

## Regra de ouro

Esta implementação respeita: Documento Mestre, mapa de módulos, requisitos não funcionais, modelo conceitual, estrutura do repositório?  
Se não, reconsiderar.
