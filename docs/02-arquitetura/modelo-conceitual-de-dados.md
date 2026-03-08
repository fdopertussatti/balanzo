# Modelo Conceitual de Dados do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Base conceitual para modelagem do banco de dados e do domínio do sistema**

---

## 1. Finalidade deste documento

Este documento define o modelo conceitual de dados do Balanzo.

Ele descreve as principais entidades do sistema, seus relacionamentos e os conceitos fundamentais que estruturam o domínio da plataforma.

Serve como referência para:

- modelagem do banco de dados
- implementação das entidades do domínio no backend
- definição de migrations
- consistência entre módulos do sistema
- implementação das APIs

Este documento não representa ainda o schema físico do banco. Ele descreve o modelo conceitual do domínio.

---

## 2. Princípios do modelo de dados

- O usuário é sempre uma pessoa real.
- As famílias são estruturas organizacionais flexíveis.
- Os dados financeiros possuem proprietário, visibilidade e participação.
- O histórico financeiro nunca deve ser destruído.
- Os dados devem permitir análise individual e análise consolidada.
- A privacidade individual dentro do núcleo familiar deve ser respeitada.
- O sistema deve permitir múltiplas moedas.

---

## 3. Entidades centrais do sistema

- User
- Family
- FamilyMember
- Account
- Transaction
- Category
- Budget
- Goal
- Asset
- Task
- Document
- Subscription

Cada uma representa um núcleo funcional do sistema.

---

## 4. Entidade User

Representa uma pessoa com acesso ao sistema.

**Atributos conceituais:** id, email, name, status, created_at, default_currency, timezone

Um usuário pode participar de uma ou mais famílias ou operar individualmente.

---

## 5. Entidade Family

Representa um núcleo familiar (casal, pais e filhos, pessoas que compartilham organização financeira).

**Atributos conceituais:** id, name, created_at, created_by

---

## 6. Entidade FamilyMember

Representa a relação entre usuário e família.

**Atributos:** id, family_id, user_id, role, joined_at, status

**Papéis:** owner, admin, member, child

---

## 7. Entidade Account

Representa conta financeira (corrente, poupança, cartão, carteira digital, investimento, dinheiro em espécie).

**Atributos:** id, owner_user_id, family_id, name, type, currency, institution, created_at

Pode ser individual ou compartilhada.

---

## 8. Entidade Transaction

Representa movimentação financeira.

**Atributos:** id, account_id, amount, currency, type, category_id, description, date, created_by, visibility_scope

**Tipos:** income, expense, transfer

Pode ser privada ou compartilhada (família ou membros específicos).

---

## 9. Entidade Category

Representa classificação de transações.

**Atributos:** id, name, type, parent_category_id, owner_scope

Categorias podem ser globais, familiares ou individuais. Podem formar hierarquias.

---

## 10. Entidade Budget

Representa planejamento financeiro de despesas.

**Atributos:** id, owner_scope, category_id, period, amount, currency

Budgets podem ser para usuário, família ou categoria específica.

---

## 11. Entidade Goal

Representa metas financeiras (casa, viagem, reserva de emergência, veículo).

**Atributos:** id, owner_scope, name, target_amount, currency, target_date, progress

Metas podem receber contribuições ao longo do tempo.

---

## 12. Entidade Asset

Representa patrimônio (imóvel, veículo, investimento, empresa, criptoativo, colecionáveis).

**Atributos:** id, owner_scope, name, type, estimated_value, currency, valuation_date

---

## 13. Entidade Task

Representa tarefas familiares (domésticas, responsabilidades, projetos).

**Atributos:** id, family_id, title, description, assigned_to, status, priority, due_date

Tarefas podem fazer parte de projetos ou grupos.

---

## 14. Entidade TaskTemplate

Representa modelos de tarefas (viagem, mudança, festa, documentos).

**Atributos:** id, name, description, template_structure

---

## 15. Entidade Document

Representa documentos armazenados (contratos, comprovantes, recibos, documentos familiares e financeiros).

**Atributos:** id, owner_scope, file_name, storage_path, document_type, created_at

Arquivos em Supabase Storage.

---

## 16. Entidade Notification

Representa notificações (lembretes, alertas financeiros, metas próximas, eventos familiares).

**Atributos:** id, user_id, type, message, status, created_at

---

## 17. Entidade Subscription

Representa assinatura do produto.

**Atributos:** id, owner_scope, plan_id, status, stripe_customer_id, stripe_subscription_id, start_date, end_date

Conecta o sistema com o Stripe.

---

## 18. Relacionamentos principais

- User → FamilyMember → Family
- Family → Accounts
- Account → Transactions
- Transaction → Category
- Family → Tasks
- Tasks → TaskTemplates
- User → Notifications
- Family → Goals
- Family → Assets
- Family → Documents
- User → Subscription

---

## 19. Suporte a múltiplas moedas

Cada conta e transação possui sua moeda. Conversões podem ocorrer em camadas analíticas.

---

## 20. Privacidade e compartilhamento

Cada entidade relevante deve possuir conceito de escopo.

Exemplos: owner_user_id, family_id, visibility_scope

Permite: dados privados, dados compartilhados, dados parcialmente compartilhados.

---

## 21. Histórico e auditoria

Entidades relevantes devem manter histórico: transações, valoração de ativos, mudanças em metas, mudanças em composição familiar.

---

## 22. Evolução do modelo

O modelo será refinado conforme o projeto evoluir.

Futuras entidades podem incluir: eventos financeiros, investimentos avançados, integrações bancárias, reconciliação automática, inteligência financeira.

---

## 23. Conclusão

Este modelo conceitual define a base de dados do Balanzo.

Serve como referência para implementação do domínio, migrations e APIs.

Garante que o sistema represente corretamente as relações entre indivíduos, famílias, finanças, patrimônio e organização doméstica.

---

## Documentos relacionados

- Requisitos Não Funcionais: `docs/02-arquitetura/requisitos-nao-funcionais.md`
- Mapa de Módulos do Backend: `docs/02-arquitetura/mapa-de-modulos-do-backend.md`
- Fontes canônicas: `docs/00-governanca/fontes-canonicas-do-projeto.md`
