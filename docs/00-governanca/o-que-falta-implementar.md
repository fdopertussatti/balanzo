# O que falta implementar — Balanzo

**Versão:** 1.2  
**Última atualização:** 2026-04-06  
**Status:** Ativo

> **Regra de desenvolvimento:** Sempre manter este documento e os demais em `docs/` atualizados após cada entrega ou mudança relevante. Ver `.cursor/rules/docs-sempre-atualizados.mdc` e `docs/00-governanca/convencoes-de-commit-e-push.md`. **Atalho no Cursor:** mensagem apenas `1` dispara commit + push conforme essas convenções (ver seção "Atalho no Cursor" no mesmo arquivo).

---

## 1. Resumo executivo

| Área | Implementado | Pendente |
|------|--------------|----------|
| Backend — estrutura | ✅ | — |
| Backend — identidade | ⚠️ Parcial | Ciclo de vida (encerramento, exclusão) |
| Backend — família | ⚠️ Parcial | Convites por email, aceitar/rejeitar, sair, dependentes, visibilidade |
| Backend — financeiro | ⚠️ Parcial | Transferências, importação, faturas cartão, parcelamentos |
| Backend — planejamento | ⚠️ Parcial | Provisões, ciclo de revisão |
| Backend — patrimônio | ⚠️ Parcial | Passivos/dívidas, consolidado |
| Backend — colaboração | ❌ | Despesas compartilhadas, rateios, reembolsos, mesadas, carteira supervisionada |
| Backend — tarefas | ⚠️ Parcial | TaskTemplate, projetos, templates, gamificação |
| Backend — documentos | ❌ | Document entity, Storage, anexos |
| Backend — notificações | ❌ | Alertas, lembretes, preferências |
| Backend — monetização | ❌ | Plan, Subscription, Stripe |
| Backend — administração | ❌ | Auditoria, visão admin, incidentes |
| Autorização de domínio | ⚠️ Parcial | AccessPolicy por entidade, políticas finas |
| Frontend web | ⚠️ Parcial | Telas completas, UX |
| Frontend admin | ❌ | — |
| App mobile | ❌ | — |
| Site institucional | ❌ | — |
| Infra/DevOps | ⚠️ Parcial | Backend configurado para Postgres+JWT no Supabase (env); CI/CD, deploy |

---

## 2. Backend — Detalhamento

### 2.1 Já implementado

- [x] Bootstrap Spring Boot (pom, config, security)
- [x] JWT Supabase Auth (condicional)
- [x] Dev local: JDBC sem proxy SOCKS (`BalanzoApplication` + `spring-boot-maven-plugin`; ver README)
- [x] Flyway em Supabase: `baseline-on-migrate` + `baseline-version: 0` (schema `public` já não vazio; ver README)
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
| API GET /api/v1/me (perfil) | ✅ |
| API PATCH /api/v1/me (atualizar perfil) | ✅ |

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
| Account entity + repository | ✅ |
| Transaction entity + repository | ✅ |
| API CRUD contas | ✅ |
| API CRUD transações | ✅ |
| Transferências internas | ❌ |
| Importação de extratos | ❌ |
| Faturas de cartão | ❌ |
| Parcelamentos | ❌ |

### 2.5 Módulo categorização

| Item | Status |
|------|--------|
| Category entity + repository | ✅ |
| Categorias globais/familiares/individuais | ✅ |
| Hierarquia (parent) | ✅ |
| Tags | ❌ |
| Regras de classificação | ❌ |
| API categorias | ✅ |

### 2.6 Módulo planejamento

| Item | Status |
|------|--------|
| Budget entity + repository | ✅ |
| Goal entity + repository | ✅ |
| Provisões | ❌ |
| Ciclo de revisão | ❌ |
| API orçamento | ✅ |
| API metas | ✅ |

### 2.7 Módulo patrimônio

| Item | Status |
|------|--------|
| Asset entity + repository | ✅ |
| Passivos/Dívidas | ❌ |
| Patrimônio consolidado | ❌ |
| API ativos | ✅ |

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
| Task entity + repository | ✅ |
| API tarefas (GET/POST por família) | ✅ |
| TaskTemplate entity + repository | ❌ |
| Projetos familiares | ❌ |
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
| AuthorizationContext | ✅ |
| ResourceScope | ✅ |
| AuthorizationContextResolver + FamilyScopeAccess | ✅ |
| AccessPolicy (por entidade) | ⚠️ Parcial (lógica em DomainAuthorizationService) |
| DomainAuthorizationService | ✅ |
| Integração escopo familiar (Asset, Budget, Goal, Task) | ✅ |
| Transações: visibilidade por conta familiar + nível `visibility_scope` | ✅ |
| POST/PATCH transação com `visibilityScope` | ✅ |
| GET /accounts/{id}/transactions/aggregate (incl. ANALYTICAL_ONLY) | ✅ |
| Políticas por papel (admin/owner para MANAGE) | ✅ |
| Consulta em cada caso de uso | ⚠️ Parcial (ex.: políticas finas por papel na família) |
| Níveis PRIVATE, SHARED_READ, ANALYTICAL_ONLY | ✅ |

---

## 4. Aplicações

### 4.1 Frontend web (apps/web)

| Item | Status |
|------|--------|
| Projeto Next.js | ✅ |
| Auth Supabase (login/cadastro) | ✅ |
| Dashboard (contas, transações) | ✅ |
| Telas principais | ⚠️ Mínimo (listagem) |

Cadastro: quando o projeto Supabase exige confirmação por email, não há sessão após `signUp`; a UI explica que o envio é do Supabase, sugere checagens (spam, SMTP, painel) e oferece reenvio (`auth.resend`). Login com `email_not_confirmed` mostra mensagem em PT e atalho de reenvio. O modo **Entrar** / **Criar conta** é um seletor em abas no topo do formulário (feedback visual claro).

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
| Supabase (projeto configurado) | ⚠️ Credenciais em env local (`DATABASE_PASSWORD`, `.env.local` no web) |
| CI/CD (GitHub Actions) | ❌ |
| Docker Compose local (Postgres) | ✅ |
| Variáveis de ambiente (secrets) | 📄 Docs existem |
| Deploy (Vercel, etc.) | ❌ |

---

## 6. Prioridade sugerida (próximas entregas)

Ordem sugerida com base no que já está implementado e no caminho crítico para MVP:

| # | Entregável | Motivo |
|---|------------|--------|
| 1 | **Frontend web** — telas completas, UX, CRUD contas/transações | Já tem login + dashboard; evoluir para fluxo completo |
| 2 | **Convites por email** — fluxo de convite/aceitar/rejeitar na família | Diferencial colaborativo; depende de família para consolidar dados |
| 3 | **Transferências internas** — entre contas do mesmo usuário | Funcionalidade financeira esperada; complementa CRUD transações |
| 4 | **Módulo colaboração** — rateios, reembolsos (ou despesas compartilhadas) | Núcleo do valor familiar do produto |
| 5 | **Passivos/dívidas** — extensão do patrimônio | Complementa visão patrimonial |
| 6 | **CI/CD + Docker Compose** | Automação e ambiente reprodutível |
| 7 | **TaskTemplate + API templates** | Completa módulo tarefas |
| 8 | **Monetização (Stripe)** — planos, assinatura | Viabilidade comercial |
| 9 | **Importação de extratos** | Conveniência para usuário |
| 10 | **Notificações base** | Alertas, lembretes |

**Já entregue (base do MVP):** autorização de domínio, financeiro base, planejamento, patrimônio (ativos), tarefas, categorização, identidade (perfil), frontend web mínimo (login + dashboard).

---

## 7. Documentos relacionados

- `docs/01-produto/documento-mestre-do-balanzo.md`
- `docs/02-arquitetura/mapa-de-modulos-do-backend.md`
- `docs/02-arquitetura/modelo-conceitual-de-dados.md`
- `docs/02-arquitetura/estrategia-de-autorizacao-e-compartilhamento-familiar.md`
- `docs/00-governanca/analise-de-prontidao-para-implementacao.md`
- `docs/00-governanca/convencoes-de-commit-e-push.md`
- `.cursor/rules/docs-sempre-atualizados.mdc`
