# O que falta implementar — Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Ativo

---

## 1. Resumo executivo

| Área | Implementado | Pendente |
|------|--------------|----------|
| Backend — estrutura | ✅ | — |
| Backend — identidade | ⚠️ Parcial | Perfil, preferências, ciclo de vida |
| Backend — família | ⚠️ Parcial | Convites, visibilidade, dependentes |
| Backend — financeiro | ❌ | Contas, transações, categorias |
| Backend — planejamento | ❌ | Orçamento, metas |
| Backend — patrimônio | ❌ | Ativos, passivos |
| Backend — colaboração | ❌ | Rateios, reembolsos |
| Backend — tarefas | ❌ | Tarefas, projetos, templates |
| Backend — documentos | ❌ | Anexos, storage |
| Backend — notificações | ❌ | Alertas, eventos |
| Backend — monetização | ❌ | Planos, Stripe |
| Backend — administração | ❌ | Auditoria, admin |
| Autorização de domínio | ❌ | AuthorizationContext, AccessPolicy |
| Frontend web | ❌ | — |
| Frontend admin | ❌ | — |
| App mobile | ❌ | — |
| Site institucional | ❌ | — |
| Infra/DevOps | ❌ | CI/CD, Supabase, deploy |

---

## 2. Backend — Detalhamento

### 2.1 Já implementado

- [x] Bootstrap Spring Boot (pom, config, security)
- [x] JWT Supabase Auth (condicional)
- [x] Flyway + schema SQL inicial (V1)
- [x] Trigger auth.users → public.user (V2)
- [x] Entidades: User, Family, FamilyMember
- [x] Repositórios: UserRepository, FamilyRepository, FamilyMemberRepository
- [x] API: GET /api/v1/families, POST /api/v1/families, POST /api/v1/families/{id}/members
- [x] Casos de uso: CreateFamily, AddFamilyMember
- [x] GlobalExceptionHandler

### 2.2 Módulo identidade (parcial)

| Item | Status |
|------|--------|
| User entity | ✅ |
| UserRepository | ✅ |
| Perfil do usuário | ❌ |
| Preferências (idioma, moeda, timezone) | ⚠️ Campos em User |
| Ciclo de vida da conta (encerramento, exclusão) | ❌ |
| API GET /api/v1/me (perfil) | ❌ |
| API PATCH /api/v1/me (atualizar perfil) | ❌ |

### 2.3 Módulo família (parcial)

| Item | Status |
|------|--------|
| Family, FamilyMember | ✅ |
| Criar família | ✅ |
| Adicionar membro (por userId) | ✅ |
| Convites por email | ❌ |
| Aceitar/rejeitar convite | ❌ |
| Sair da família | ❌ |
| Dependentes (child, carteira supervisionada) | ❌ |
| Configuração de visibilidade | ❌ |

### 2.4 Módulo financeiro base

| Item | Status |
|------|--------|
| Account entity + repository | ❌ |
| Transaction entity + repository | ❌ |
| API CRUD contas | ❌ |
| API CRUD transações | ❌ |
| Transferências internas | ❌ |
| Importação de extratos | ❌ |
| Faturas de cartão | ❌ |
| Parcelamentos | ❌ |

### 2.5 Módulo categorização

| Item | Status |
|------|--------|
| Category entity + repository | ❌ |
| Categorias globais/familiares/individuais | ❌ |
| Hierarquia (parent) | ❌ |
| Tags | ❌ |
| Regras de classificação | ❌ |
| API categorias | ❌ |

### 2.6 Módulo planejamento

| Item | Status |
|------|--------|
| Budget entity + repository | ❌ |
| Goal entity + repository | ❌ |
| Provisões | ❌ |
| Ciclo de revisão | ❌ |
| API orçamento | ❌ |
| API metas | ❌ |

### 2.7 Módulo patrimônio

| Item | Status |
|------|--------|
| Asset entity + repository | ❌ |
| Passivos/Dívidas | ❌ |
| Patrimônio consolidado | ❌ |
| API ativos | ❌ |

### 2.8 Módulo colaboração familiar

| Item | Status |
|------|--------|
| Despesas compartilhadas | ❌ |
| Rateios | ❌ |
| Reembolsos | ❌ |
| Mesadas | ❌ |
| Carteira supervisionada | ❌ |

### 2.9 Módulo tarefas

| Item | Status |
|------|--------|
| Task entity + repository | ❌ |
| TaskTemplate entity + repository | ❌ |
| Projetos familiares | ❌ |
| API tarefas | ❌ |
| API templates | ❌ |
| Gamificação (pontuação) | ❌ |

### 2.10 Módulo documental

| Item | Status |
|------|--------|
| Document entity + repository | ❌ |
| Integração Supabase Storage | ❌ |
| Anexos a transações | ❌ |

### 2.11 Módulo notificações

| Item | Status |
|------|--------|
| Notification entity + repository | ❌ |
| Alertas financeiros | ❌ |
| Lembretes | ❌ |
| Preferências de notificação | ❌ |

### 2.12 Módulo monetização

| Item | Status |
|------|--------|
| Plan entity + repository | ❌ |
| Subscription entity + repository | ❌ |
| Integração Stripe | ❌ |
| Limites por plano | ❌ |

### 2.13 Módulo administração

| Item | Status |
|------|--------|
| Auditoria de alterações | ❌ |
| Visão administrativa | ❌ |
| Incidentes | ❌ |

---

## 3. Autorização de domínio

Conforme `docs/02-arquitetura/estrategia-de-autorizacao-e-compartilhamento-familiar.md`:

| Componente | Status |
|------------|--------|
| AuthorizationContext | ❌ |
| ResourceScope | ❌ |
| AccessPolicy (por entidade) | ❌ |
| DomainAuthorizationService | ❌ |
| Consulta em cada caso de uso | ❌ |
| Níveis PRIVATE, SHARED_READ, ANALYTICAL_ONLY | ⚠️ No schema, não no código |

---

## 4. Aplicações

### 4.1 Frontend web (apps/web)

| Item | Status |
|------|--------|
| Projeto Next.js | ❌ (apenas .gitkeep) |
| Auth Supabase | ❌ |
| Dashboard | ❌ |
| Telas principais | ❌ |

### 4.2 Frontend admin (apps/admin)

| Item | Status |
|------|--------|
| Projeto | ❌ (apenas .gitkeep) |

### 4.3 App mobile (apps/mobile)

| Item | Status |
|------|--------|
| Projeto Flutter | ❌ (apenas .gitkeep) |

### 4.4 Site institucional (apps/site)

| Item | Status |
|------|--------|
| Projeto | ❌ (apenas .gitkeep) |

---

## 5. Infraestrutura e DevOps

| Item | Status |
|------|--------|
| Supabase (projeto configurado) | ❌ |
| CI/CD (GitHub Actions) | ❌ |
| Docker Compose local | ❌ |
| Variáveis de ambiente (secrets) | 📄 Docs existem |
| Deploy (Vercel, etc.) | ❌ |

---

## 6. Prioridade sugerida (MVP)

1. **Camada de autorização** — base para filtrar dados por usuário/família
2. **Módulo financeiro base** — Account, Transaction, Category
3. **API contas e transações** — CRUD com autorização
4. **Frontend web mínimo** — login + dashboard + listagem
5. **Módulo planejamento** — Budget, Goal
6. **Módulo tarefas** — Task, TaskTemplate
7. **Monetização (Stripe)** — planos, assinatura

---

## 7. Documentos relacionados

- `docs/01-produto/documento-mestre-do-balanzo.md`
- `docs/02-arquitetura/mapa-de-modulos-do-backend.md`
- `docs/02-arquitetura/modelo-conceitual-de-dados.md`
- `docs/02-arquitetura/estrategia-de-autorizacao-e-compartilhamento-familiar.md`
- `docs/00-governanca/analise-de-prontidao-para-implementacao.md`
