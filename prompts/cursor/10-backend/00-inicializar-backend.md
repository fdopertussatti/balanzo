# Prompt de Inicialização do Backend do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Prompt de bootstrap do backend para uso no Cursor AI**

---

## 1. Finalidade deste prompt

Este prompt deve ser utilizado no Cursor AI para iniciar a implementação do backend do projeto Balanzo.

O objetivo é orientar a criação da estrutura inicial do backend em Java com Spring, respeitando:

- arquitetura modular definida no projeto
- estrutura do repositório
- requisitos não funcionais
- modelo conceitual de dados
- convenções de engenharia estabelecidas

**Esta etapa gera apenas a estrutura inicial, sem implementar todas as funcionalidades.**

---

## 2. Contexto do projeto

O Balanzo é uma plataforma SaaS de organização financeira e familiar.

Permite que usuários individuais, casais e famílias gerenciem: finanças pessoais, compartilhadas, planejamento, patrimônio, tarefas familiares, metas, mesadas, projetos domésticos.

O backend central é responsável por todas as regras de negócio.

---

## 3. Arquitetura definida

- **Backend:** Java, Spring Boot, PostgreSQL
- **Infraestrutura:** Supabase (PostgreSQL, Auth, Storage), Stripe, Docker, CI/CD
- **Modelo:** Monolito modular orientado a domínios

---

## 4. Estrutura do repositório

O backend deve ser criado em:

```
services/backend
```

---

## 5. Documentos obrigatórios antes de gerar código

- docs/01-produto/documento-mestre-do-balanzo.md
- docs/02-arquitetura/mapa-de-modulos-do-backend.md
- docs/02-arquitetura/requisitos-nao-funcionais.md
- docs/02-arquitetura/modelo-conceitual-de-dados.md
- docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md
- prompts/cursor/00-contexto-geral.md
- prompts/cursor/01-regras-de-implementacao.md

---

## 6. Objetivo desta etapa

O Cursor AI deve:

1. Criar o projeto Spring Boot
2. Definir dependências principais
3. Organizar a estrutura modular inicial
4. Configurar conexão com PostgreSQL
5. Preparar base para autenticação com Supabase
6. Configurar estrutura inicial de testes
7. Preparar suporte a containerização

**Não implementar** regras de negócio complexas.

---

## 7. Tecnologias a utilizar

- **Linguagem:** Java
- **Framework:** Spring Boot
- **Build:** Maven
- **Banco:** PostgreSQL
- **Auth:** Supabase Auth
- **Containerização:** Docker

**Dependências recomendadas:**

- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL Driver
- Validation API
- Lombok
- Spring Boot Actuator
- Testcontainers (para testes futuros)

---

## 8. Estrutura inicial do projeto

```
services/backend/
src/main/java/br/com/balanzo/
├── config
├── common
├── security
├── api
├── application
├── domain
└── infrastructure
```

**Módulos em `domain/` alinhados ao mapa de módulos:**

- domain/identidade
- domain/familia
- domain/financeiro
- domain/classificacao
- domain/planejamento
- domain/patrimonio
- domain/colaboracao
- domain/tarefas
- domain/documentos
- domain/notificacoes
- domain/monetizacao
- domain/administracao

---

## 9. Estrutura interna inicial de módulo

Exemplo para `domain/financeiro`:

```
entity
service
repository
dto
exception
```

---

## 10. Configuração inicial de banco

- Integração com PostgreSQL
- Configuração externa via variáveis de ambiente
- Sem credenciais fixas no código
- Preparar suporte para migrations futuras

---

## 11. Preparação para autenticação

- Configuração de segurança
- Interpretação de tokens JWT (Supabase)
- Identificação do usuário autenticado
- Não implementar ainda regras complexas de autorização

---

## 12. Preparação para segurança

- Spring Security com estrutura básica de filtros
- Interpretação de identidade
- Preparação para autorização baseada em domínio
- Autorização completa em etapas posteriores

---

## 13. Estrutura de testes

- `src/test/java`
- Configuração para testes unitários e de integração

---

## 14. Observabilidade inicial

- Spring Boot Actuator para monitoramento básico

---

## 15. Preparação para containerização

- Dockerfile para o backend
- Build reproduzível
- Execução simples
- Compatível com deploy futuro

---

## 16. Arquivos iniciais esperados

- pom.xml
- Classe principal do Spring Boot
- Estrutura de pacotes inicial
- Configuração básica de segurança
- Configuração de datasource
- Dockerfile
- Estrutura de testes

---

## 17. O que não deve ser feito nesta etapa

Não implementar ainda:

- regras completas de domínio
- APIs completas
- integrações complexas
- Stripe
- regras de família
- lógica financeira

**Apenas bootstrap estrutural.**

---

## 18. Critério de sucesso

O backend deve:

- compilar corretamente
- iniciar sem erro
- conectar ao banco configurado
- possuir estrutura modular inicial
- estar preparado para autenticação
- estar preparado para testes
- estar preparado para containerização

---

## 19. Resultado esperado

Base estrutural sólida e coerente com a arquitetura, permitindo iniciar a implementação dos módulos de domínio de forma organizada e segura.

---

## 20. Instrução final para o Cursor AI

Com base nas diretrizes acima, gere a estrutura inicial completa do backend do Balanzo em:

```
services/backend
```

**Respeitar:**

- Java com Spring Boot
- Estrutura modular
- Integração futura com Supabase
- Boas práticas de engenharia
- Preparação para testes
- Preparação para containerização

**Não implementar** funcionalidades de negócio nesta etapa. Apenas a estrutura inicial do backend.
