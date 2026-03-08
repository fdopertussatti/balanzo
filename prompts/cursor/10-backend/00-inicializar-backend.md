# Prompt de Inicialização do Backend do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

---

## Finalidade

Este prompt orienta o Cursor AI a criar a **estrutura inicial** do backend em Java com Spring. Gera apenas bootstrap estrutural, sem regras de negócio.

---

## Documentos obrigatórios antes de gerar código

Consulte estes documentos (listados em `prompts/cursor/00-contexto-geral.md`):

- docs/01-produto/documento-mestre-do-balanzo.md
- docs/02-arquitetura/mapa-de-modulos-do-backend.md
- docs/02-arquitetura/modelo-conceitual-de-dados.md
- docs/02-arquitetura/requisitos-nao-funcionais.md
- docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md
- prompts/cursor/01-regras-de-implementacao.md

---

## Objetivo desta etapa

1. Criar projeto Spring Boot
2. Definir dependências principais
3. Organizar estrutura modular inicial
4. Configurar conexão com PostgreSQL
5. Preparar base para autenticação com Supabase
6. Configurar estrutura inicial de testes
7. Preparar suporte a containerização

**Não implementar:** regras de negócio, APIs completas, Stripe, lógica financeira.

---

## Tecnologias

- Java, Spring Boot, Maven, PostgreSQL, Docker
- **Auth:** Supabase Auth

**Dependências:** Spring Web, Spring Security, Spring Data JPA, PostgreSQL Driver, Validation API, Lombok, Spring Boot Actuator, Testcontainers

---

## Estrutura e módulos

**Estrutura geral:** ver `prompts/cursor/10-backend/01-estrutura-do-backend.md`

**Módulos em domain/:** ver `docs/02-arquitetura/mapa-de-modulos-do-backend.md`

- identidade, familia, financeiro, classificacao, planejamento, patrimonio
- colaboracao, tarefas, documentos, notificacoes, monetizacao, administracao

---

## Estrutura interna de módulo (exemplo)

```
entity | service | repository | dto | exception
```

---

## Configuração

- Banco: variáveis de ambiente, sem credenciais no código
- Segurança: Spring Security, JWT (Supabase), estrutura para autorização futura
- Testes: src/test/java
- Observabilidade: Spring Boot Actuator
- Container: Dockerfile

---

## Arquivos esperados

pom.xml, classe principal Spring Boot, estrutura de pacotes, configuração de segurança, datasource, Dockerfile, testes básicos

---

## Critério de sucesso

Compila, inicia, conecta ao banco, possui estrutura modular, pronto para auth e testes.

---

## Instrução final

Gerar estrutura inicial em `services/backend`, respeitando Java/Spring Boot, estrutura modular e boas práticas. Não implementar funcionalidades de negócio.
