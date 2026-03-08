# Estrutura de Engenharia do Backend do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento complementar ao prompt de inicialização do backend**

---

## 1. Finalidade deste documento

Este documento orienta o Cursor AI na criação da estrutura arquitetural do backend do Balanzo **após** a etapa de bootstrap do projeto Spring Boot.

Define como o backend deve ser organizado internamente para que a implementação dos módulos de domínio ocorra de forma consistente, modular e alinhada às melhores práticas.

Deve ser utilizado como referência estrutural antes da implementação de qualquer funcionalidade.

---

## 2. Arquitetura adotada

O backend adota o modelo de **monolito modular orientado a domínios**.

- Existe uma única aplicação backend
- Os domínios são separados internamente por módulos
- Cada módulo possui responsabilidades claras
- As dependências entre módulos são controladas

**Benefícios:** simplicidade operacional, boa organização, alta capacidade de evolução, baixo custo de infraestrutura.

---

## 3. Estrutura geral do backend

```
services/backend/
src/main/java/br/com/balanzo/
├── config         # Configurações gerais da aplicação
├── common         # Componentes utilitários compartilhados
├── security       # Autenticação e autorização
├── api            # Controllers e endpoints
├── application    # Casos de uso e orquestração
├── domain         # Entidades e lógica central de negócio
└── infrastructure # Integrações externas e persistência
```

---

## 4. Organização por módulos de domínio

Dentro de `domain/`:

- identidade
- familia
- financeiro
- classificacao
- planejamento
- patrimonio
- colaboracao
- tarefas
- documentos
- notificacoes
- monetizacao
- administracao

---

## 5. Estrutura interna de cada módulo

Exemplo para `domain/financeiro`:

```
entity      # Entidades principais do domínio
service     # Regras de negócio específicas do módulo
repository  # Interfaces de acesso a dados
dto         # Objetos de transferência de dados
exception   # Erros específicos do domínio
event       # Eventos internos do domínio
```

---

## 6. Camada de aplicação

A camada `application` representa os casos de uso.

Coordena interações entre módulos de domínio.

Exemplos: criar transação, registrar meta, gerenciar composição familiar, registrar tarefa, iniciar planejamento.

Não deve conter lógica de persistência direta.

---

## 7. Camada de API

Controllers devem:

- receber requisições
- validar dados básicos
- invocar casos de uso
- retornar respostas apropriadas

Controllers não implementam lógica complexa.

---

## 8. Camada de segurança

- Integração com Supabase Auth
- Interpretação de tokens JWT
- Identificação do usuário autenticado
- Controle inicial de autorização

Autorização completa baseada em domínio será implementada gradualmente.

---

## 9. Camada de infraestrutura

Implementações técnicas:

- persistência
- integrações externas
- mensageria
- armazenamento de arquivos
- integração com Stripe
- integração com Supabase

---

## 10. Persistência de dados

- Spring Data JPA
- PostgreSQL

Regras: entidades representam o domínio; repositórios tratam apenas acesso a dados; consultas complexas devem ser explicitadas.

---

## 11. Integração com Supabase

Supabase como: PostgreSQL, Auth, Storage.

O backend continua sendo a fonte central das regras de negócio. O Supabase não concentra lógica de domínio.

---

## 12. Integração com Stripe

Integração encapsulada no módulo `monetizacao`. Comunicação via componentes isolados na camada de infraestrutura.

---

## 13. Estrutura de testes

Tipos: unitários, integração, domínio.

Foco em: regras financeiras, regras familiares, compartilhamento, metas, tarefas, assinaturas.

---

## 14. Organização de configurações

Configurações sensíveis via variáveis de ambiente e arquivos por ambiente. Nunca credenciais no código.

---

## 15. Logs e observabilidade

Estrutura básica para diagnóstico, monitoramento e rastreamento de erros.

---

## 16. Containerização

- Dockerfile
- Scripts de build
- Compatibilidade com CI/CD
- Execução local e deploy automatizado

---

## 17. Evolução da arquitetura

Evoluções futuras possíveis: fila de eventos, jobs assíncronos, cache distribuído, observabilidade avançada, arquitetura de eventos.

Devem respeitar a modularidade do sistema.

---

## 18. Responsabilidade do Cursor AI

Ao implementar no backend, o Cursor AI deve:

- identificar o módulo correto
- implementar a lógica no domínio apropriado
- evitar misturar responsabilidades
- respeitar a separação de camadas
- seguir as convenções deste documento

---

## 19. Critério de qualidade

Uma implementação é adequada quando:

- está no módulo correto
- respeita a separação de camadas
- não duplica lógica
- mantém o domínio claro
- preserva a testabilidade

---

## 20. Conclusão

Este documento define a organização estrutural do backend do Balanzo.

Garante evolução disciplinada, com domínios claros, responsabilidades bem definidas e código sustentável.

---

## Referência

O Modelo Conceitual de Dados está em `docs/02-arquitetura/modelo-conceitual-de-dados.md`.
