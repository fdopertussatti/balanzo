# Regras de Implementação e Convenções de Código para Cursor AI

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Diretrizes de engenharia para desenvolvimento assistido por IA no projeto Balanzo**

---

## 1. Finalidade deste documento

Este documento define as regras práticas de implementação e as convenções de código que devem ser seguidas pelo Cursor AI ao gerar, modificar ou reorganizar código no projeto Balanzo.

Ele complementa o documento de contexto geral e traduz as decisões arquiteturais em regras operacionais claras.

---

## 2. Princípios gerais de implementação

- Clareza estrutural
- Separação de responsabilidades
- Baixo acoplamento entre módulos
- Domínio explícito no backend
- Privacidade e segurança por padrão
- Código testável
- Evolução incremental segura
- Evitar soluções improvisadas ou atalhos que comprometam a estrutura

---

## 3. Regra central do projeto

**A lógica de negócio do Balanzo deve viver no backend.**

- O frontend é responsável por interface, UX e comunicação com APIs
- O Supabase é infraestrutura de apoio, não repositório primário de regras de domínio
- O Cursor AI não deve criar regras críticas no frontend ou em scripts externos sem justificativa arquitetural clara

---

## 4. Convenções gerais de código

### 4.1 Código legível

- Priorizar legibilidade sobre soluções excessivamente compactas
- Evitar complexidade desnecessária
- Evitar abstrações prematuras
- Preferir nomes claros e descritivos

### 4.2 Nomes alinhados ao domínio

**Bons exemplos:** `FamilyMember`, `FinancialTransaction`, `SharedExpense`, `FinancialGoal`, `AssetPosition`, `HouseholdTask`, `SubscriptionPlan`

**Evitar:** `DataObject`, `Manager`, `Helper`, `Util`, `Stuff`, `Thing`

### 4.3 Evitar lógica duplicada

Avaliar movimentação para componente compartilhado quando houver duplicação relevante. Não mover para `packages/` por antecipação.

### 4.4 Arquivos com responsabilidade clara

Arquivos grandes devem ser divididos. Cada arquivo deve ter responsabilidade única e clara.

---

## 5. Convenções para backend Java com Spring

### 5.1 Organização por módulo

Seguir o mapa de módulos: identidade, família, financeiro, classificação, planejamento, patrimônio, colaboração, tarefas, documentos, notificações, monetização, administração.

Não misturar regras de módulos diferentes no mesmo contexto sem justificativa.

### 5.2 Estrutura interna de módulo

Exemplo conceitual: `controller`, `application`, `domain`, `repository`, `dto`, `mapper`, `events`, `exceptions`

### 5.3 Controllers finos

Controllers devem: receber requisição, validar entrada básica, delegar para camada de aplicação, retornar resposta adequada.

Não devem conter lógica de negócio complexa.

### 5.4 Lógica no domínio ou na aplicação

Regras importantes no domínio ou na camada de aplicação. Evitar lógica de negócio em controllers, repositórios ou frontend.

### 5.5 Repositórios para persistência

Repositórios tratam: acesso a dados, consultas, persistência. Não contêm lógica de domínio complexa.

### 5.6 Integrações externas isoladas

Integrações (Supabase, Stripe, e-mail, etc.) em componentes específicos. Não espalhar chamadas externas pelo código.

### 5.7 Tratamento de erros

Tratamento consistente. Criar exceções específicas de domínio quando necessário. Evitar retorno silencioso ou exceções genéricas sem contexto.

---

## 6. Convenções para APIs

### 6.1 Estrutura previsível

Exemplo: `/api/v1/users`, `/api/v1/families`, `/api/v1/transactions`, `/api/v1/goals`, `/api/v1/assets`, `/api/v1/tasks`

### 6.2 Respostas estruturadas

Dados solicitados, paginação quando aplicável, mensagens claras em erro.

### 6.3 Evitar excesso de dados

Preferir paginação e filtros.

### 6.4 Segurança de endpoints

Considerar: identidade, escopo de acesso, participação no núcleo familiar, propriedade do dado.

---

## 7. Convenções para Next.js

### 7.1 Organização por contexto

Exemplo: `dashboard`, `finances`, `family`, `planning`, `assets`, `tasks`, `settings`

### 7.2 Separação entre aplicações

web, admin e site são aplicações distintas. Não misturar lógica sem justificativa.

### 7.3 Componentes reutilizáveis

Em `packages/shared-ui` quando fizer sentido. Componentes específicos permanecem na aplicação.

### 7.4 Sem lógica crítica no frontend

Validações importantes devem existir também no backend.

---

## 8. Convenções para Flutter

- Organização por funcionalidades: dashboard, transactions, family, tasks, notifications, settings
- Consumir APIs do backend
- Priorizar rapidez, clareza e fluxos simples

---

## 9. Convenções para banco de dados

- PostgreSQL como banco principal
- Supabase para PostgreSQL, Auth, Storage, Realtime
- Migrações versionadas para alterações estruturais
- Integridade referencial preservada

---

## 10. Convenções para Stripe

- Integração encapsulada no módulo de monetização
- Eventos de cobrança via webhooks
- Backend como fonte da verdade para elegibilidade de recursos

---

## 11. Convenções de testes

### 11.1 Cobertura de fluxos críticos

Regras financeiras, compartilhamento familiar, metas, reembolsos, assinaturas, autorização.

### 11.2 Tipos de teste

Unitários, integração, fluxos críticos.

### 11.3 Clareza

Testes devem expressar claramente o comportamento esperado. Evitar testes frágeis ou excessivamente acoplados.

---

## 12. Convenções de commits

**Bons exemplos:**
```
feat: add family membership domain module
fix: correct transaction reconciliation logic
refactor: simplify goal contribution calculation
docs: update backend module architecture
```

**Evitar:** `update`, `fix stuff`, `changes`, `misc`

Ver: `docs/00-governanca/convencoes-de-commit-e-push.md`

---

## 13. Convenções para refatorações

- Preservar comportamento
- Alteração de comportamento = alteração de funcionalidade
- Evitar refatorações massivas sem justificativa

---

## 14. Regras para novos módulos

Antes de criar novo módulo, verificar:
- se já existe no mapa de módulos
- se a funcionalidade pode viver em módulo existente
- se criação é realmente necessária

---

## 15. Regras para alterações estruturais

Alterações que impactem arquitetura, estrutura de repositório, módulos principais ou integrações críticas devem ser explicitadas antes da implementação.

---

## 16. Regra de ouro

O Cursor AI deve sempre considerar implicitamente:

**Esta implementação respeita:**
- o Documento Mestre do Balanzo
- o mapa de módulos do backend
- os requisitos não funcionais
- o modelo conceitual de dados
- a estrutura do repositório

Se a resposta não for claramente sim, reconsiderar a implementação.

---

## 17. Conclusão

Este documento estabelece as regras práticas que garantem consistência no desenvolvimento assistido por IA do projeto Balanzo.

---

## Próximo passo recomendado

O próximo passo mais estratégico é criar:

**Prompt de Inicialização do Backend no Cursor AI**

Esse prompt orientará o Cursor a:
- criar a estrutura inicial do backend Spring
- configurar o projeto Java
- estabelecer módulos iniciais
- configurar integração com PostgreSQL e Supabase
- preparar estrutura para autenticação
- configurar base de testes
- preparar estrutura para containerização
