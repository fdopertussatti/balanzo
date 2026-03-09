# Estratégia de Autorização e Compartilhamento Familiar do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## 1. Objetivo

Define como o Balanzo controla acesso, visibilidade, colaboração e consolidação analítica em cenários individuais, conjugais e familiares. Foco em **autorização de domínio** (não autenticação técnica).

---

## 2. Problema central

O sistema precisa suportar:
- Conta privada com transações que entram no consolidado familiar
- Despesa paga por um membro, pertence economicamente ao casal
- Dependente como centro de gasto sem ver todos os dados do núcleo
- Meta compartilhada com contribuições de contas separadas
- Tarefa visível a todos, editável apenas pelo responsável

A autorização não pode ser binária. Deve ser **contextual**.

---

## 3. Quatro eixos da autorização

Para decidir se algo é permitido, o backend deve responder:

1. **Quem** é o usuário
2. **Em qual contexto** está operando
3. **Qual a relação** dele com o dado
4. **Que tipo de ação** está tentando executar

---

## 4. Autenticação vs Autorização

- **Autenticação:** Supabase Auth — confirma identidade técnica
- **Autorização:** backend Spring — decide visualizar, editar, compartilhar, usar em consolidação, administrar

Separação formal e inegociável.

---

## 5. Quatro conceitos do modelo

### 5.1 Proprietário do dado
Pessoa ou entidade principal à qual o dado pertence. Não é necessariamente o único que vê ou altera.

### 5.2 Visibilidade do dado
Quem pode enxergar. Escopos: privado, compartilhado com núcleo, compartilhado com membros específicos, apenas analítico.

### 5.3 Permissão operacional
O que alguém pode fazer: visualizar, editar, administrar, aprovar, excluir, reclassificar, compartilhar.

### 5.4 Participação analítica
Se e como o dado entra nas análises consolidadas. Dado pode ser invisível em detalhe mas participar do consolidado familiar.

---

## 6. Contextos de operação

| Contexto | Exemplo |
|----------|---------|
| Individual | Conta pessoal, transação privada, meta individual |
| Conjugal | Meta conjunta, orçamento do casal, despesa compartilhada |
| Familiar | Despesas da casa, tarefas, projetos, mesadas |
| Dependente | Mesada do filho, carteira supervisionada, tarefas do dependente |
| Administrativo | Suporte, auditoria, incidentes |

---

## 7. Níveis de visibilidade

| Nível | Significado |
|-------|-------------|
| PRIVATE | Somente proprietário e operadores internos autorizados |
| SHARED_READ | Outros membros podem visualizar, não editar |
| SHARED_EDIT | Outros membros podem visualizar e editar |
| SHARED_MANAGE | Outros membros podem administrar |
| ANALYTICAL_ONLY | Dado não exposto em detalhe, participa de agregações |

ANALYTICAL_ONLY é estratégico para o Balanzo.

---

## 8. Níveis de pertencimento

INDIVIDUAL | COUPLE | FAMILY | DEPENDENT | SYSTEM_ADMIN

---

## 9. Regra de ouro

Nenhuma decisão de acesso depende apenas do papel do usuário.

Combinar: papel global, papel no núcleo, pertencimento do dado, visibilidade do dado, tipo de ação, relação direta com a entidade.

---

## 10. Matriz de decisão

### Pode visualizar
- É proprietário
- É participante explicitamente autorizado
- Dado pertence ao contexto familiar e visibilidade permite leitura
- Dado em ANALYTICAL_ONLY e visualização é agregada
- É operador interno autorizado

### Pode editar
- É proprietário com permissão
- Coadministrador ou participante com SHARED_EDIT ou SHARED_MANAGE
- É responsável operacional pela entidade
- Operador administrativo com permissão

### Pode administrar
- Proprietário principal, coadministrador do núcleo, operador interno com permissão específica

### Pode usar em consolidação
- Dado marcado para participação analítica naquele contexto

---

## 11. Implementação no backend

Criar camada explícita de autorização de domínio. Componentes:

- **AuthorizationContext:** usuário, papel, núcleos, tipo de operação
- **ResourceScope:** tipo de entidade, proprietário, pertencimento, visibilidade
- **AccessPolicy:** políticas por domínio (TransactionAccessPolicy, AccountAccessPolicy, etc.)
- **DomainAuthorizationService:** orquestra avaliação

Cada caso de uso importante consulta essa camada.

---

## 12. Persistência do modelo

Entidades relevantes devem carregar:
- owner_user_id
- family_id
- scope_type (individual, couple, family, dependent)
- visibility_type (private, shared_read, shared_edit, shared_manage, analytical_only)
- responsible_member_id
- dependent_id

Tabelas de participantes explícitos quando necessário (metas, projetos, rateios).

---

## 13. O que não fazer

- Não colocar toda autorização em anotações superficiais
- Não confiar no frontend para filtrar
- Não usar apenas papel da família como critério
- Não acoplar autorização ao provedor de autenticação
- Não deixar cada módulo inventar lógica própria
- Não tratar ANALYTICAL_ONLY como SHARED_READ

---

## 14. Casos de validação

- **Conta individual com despesa familiar:** Zahara não vê detalhes da conta; despesa entra no consolidado
- **Meta do casal com contas separadas:** ambos veem meta; aportes rastreáveis; detalhes privados não expostos
- **Dependente com carteira supervisionada:** responsáveis veem tudo; dependente vê apenas seu contexto
- **Tarefa atribuída a membro:** membros autorizados visualizam; Zahara edita status; admins reatribuem

---

## 15. Decisão arquitetural

A autorização será implementada no backend Spring por meio de camada dedicada de políticas de domínio. O modelo separa explicitamente propriedade, visibilidade, permissão operacional e participação analítica. O frontend consome resultados já autorizados. Supabase Auth cuida da identidade, não da autorização de negócio.

---

## Documentos relacionados

- `docs/02-arquitetura/requisitos-nao-funcionais.md` (segurança)
- `docs/02-arquitetura/modelo-conceitual-de-dados.md` (entidades, escopo)
- `docs/02-arquitetura/estrategia-de-autenticacao-e-autorizacao.md` (referência)
