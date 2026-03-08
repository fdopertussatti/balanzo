# Estrutura de Engenharia do Backend do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## Finalidade

Define como o backend deve ser organizado internamente. Usar como referência estrutural antes de implementar funcionalidades.

**Módulos e responsabilidades detalhadas:** `docs/02-arquitetura/mapa-de-modulos-do-backend.md`  
**Entidades e modelo de dados:** `docs/02-arquitetura/modelo-conceitual-de-dados.md`

---

## Arquitetura

Monolito modular orientado a domínios. Uma aplicação, domínios separados, dependências controladas.

---

## Estrutura geral

```
services/backend/src/main/java/br/com/balanzo/
├── config         # Configurações
├── common         # Utilitários compartilhados
├── security       # Auth e autorização
├── api            # Controllers
├── application    # Casos de uso
├── domain         # Entidades e lógica de negócio
└── infrastructure # Persistência, integrações
```

**Módulos em domain/:** identidade, familia, financeiro, classificacao, planejamento, patrimonio, colaboracao, tarefas, documentos, notificacoes, monetizacao, administracao  
(Detalhes em `docs/02-arquitetura/mapa-de-modulos-do-backend.md`)

---

## Estrutura interna de módulo

```
entity | service | repository | dto | exception | event
```

---

## Camadas

- **API:** receber, validar, delegar, retornar. Sem lógica complexa.
- **Application:** casos de uso, orquestração.
- **Domain:** regras de negócio.
- **Infrastructure:** persistência, Supabase, Stripe.

---

## Regras

- Controllers finos
- Lógica no domínio ou application
- Repositórios só persistência
- Integrações encapsuladas
- Configurações sensíveis via ambiente

---

## Testes, observabilidade, containerização

Conforme `docs/02-arquitetura/requisitos-nao-funcionais.md`.
