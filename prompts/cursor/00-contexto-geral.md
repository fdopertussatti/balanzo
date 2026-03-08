# Prompt Mestre do Projeto para Cursor AI

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento de contexto principal para uso no Cursor AI**

---

## 1. Finalidade deste documento

Este documento é o **ponto de entrada oficial** do projeto Balanzo para uso no Cursor AI.

Seu objetivo é fornecer ao Cursor o contexto essencial do sistema, sua arquitetura geral, a estrutura do repositório, as tecnologias adotadas, os princípios de implementação e as regras obrigatórias que devem ser respeitadas antes de gerar, editar ou reorganizar código.

Este arquivo não substitui a documentação detalhada do projeto. Ele funciona como guia de contexto e governança para garantir que qualquer implementação assistida por IA permaneça alinhada com a visão, a arquitetura e as decisões formais do Balanzo.

---

## 2. O que é o Balanzo

O Balanzo é uma **plataforma SaaS de organização financeira e familiar**.

O sistema foi projetado para atender usuários individuais, casais e famílias, permitindo:

- gestão financeira pessoal
- compartilhamento seletivo entre membros do núcleo familiar
- planejamento financeiro
- acompanhamento patrimonial
- tarefas familiares e projetos domésticos
- templates organizacionais
- mesadas supervisionadas

O Balanzo não é apenas um aplicativo de controle de gastos. É uma plataforma de gestão financeira pessoal, conjugal e familiar com visão patrimonial e coordenação doméstica.

---

## 3. Arquitetura geral da plataforma

A plataforma possui múltiplas aplicações e um backend central:

- **Backend principal** em Java com Spring
- **Frontend principal** em Next.js
- **Frontend administrativo** em Next.js
- **Site institucional** em Next.js
- **Aplicativo mobile** em Flutter
- **Supabase** como infraestrutura de dados e serviços auxiliares
- Infraestrutura com containers, CI/CD, ambientes separados e observabilidade

O backend em Java com Spring é o **centro do domínio** do sistema. O Supabase não deve concentrar a lógica de negócio.

---

## 4. Stack principal do projeto

- Java
- Spring
- Next.js
- Flutter
- PostgreSQL
- Supabase
- Stripe (gateway de pagamento)
- Docker
- GitHub Actions ou equivalente de CI/CD

---

## 5. Aplicações do projeto

### 5.1 Frontend principal — `apps/web`

Experiência do usuário final: onboarding, dashboard, finanças, família, planejamento, patrimônio, tarefas, documentos, configurações.

### 5.2 Frontend administrativo — `apps/admin`

Operação interna: gestão de usuários, planos, assinaturas, auditoria, monitoramento, suporte, incidentes.

### 5.3 Site institucional — `apps/site`

Presença pública: apresentação do produto, páginas institucionais, planos, conteúdo comercial, captação, SEO.

### 5.4 Aplicativo mobile — `apps/mobile`

Experiência móvel: registro rápido, consulta cotidiana, notificações, tarefas, acompanhamento familiar.

### 5.5 Backend principal — `services/backend`

Regras de negócio, autorização de domínio, persistência, integrações, orquestração dos módulos, APIs.

---

## 6. Estrutura do repositório

Monorepo organizado por contexto:

- `docs/` — documentação oficial
- `prompts/` — materiais operacionais de IA
- `apps/` — interfaces da plataforma
- `services/` — backend
- `packages/` — compartilhamentos controlados
- `infra/` — infraestrutura e automação
- `tools/` — ferramentas auxiliares

Nenhuma nova estrutura deve ser criada sem coerência com essa organização.

---

## 7. Documentos obrigatórios de referência

Antes de implementar funcionalidades estruturais, considerar:

1. `docs/01-produto/documento-mestre-do-balanzo.md`
2. `docs/02-arquitetura/mapa-de-modulos-do-backend.md`
3. `docs/02-arquitetura/requisitos-nao-funcionais.md`
4. `docs/02-arquitetura/modelo-conceitual-de-dados.md`
5. `docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md`
6. `prompts/cursor/00-contexto-geral.md` (este documento)

---

## 8. Princípios obrigatórios de implementação

### 8.1 Backend é o centro do domínio

Toda regra crítica de negócio deve viver no backend em Java com Spring. O frontend não deve concentrar regras de negócio. O Supabase não deve ser repositório de regras centrais.

### 8.2 Implementar por módulo

O backend deve seguir o mapa de módulos: identidade, família, financeiro, classificação, planejamento, patrimônio, colaboração, tarefas, documentos, notificações, monetização, administração.

### 8.3 Não misturar aplicações

web, admin e site são aplicações diferentes. Reaproveitamento permitido quando saudável, sem destruir a separação.

### 8.4 Privacidade e compartilhamento seletivo

O sistema precisa diferenciar: **propriedade do dado**, **visibilidade do dado**, **participação analítica do dado**.

### 8.5 Preservar histórico

Mudanças em composição familiar, metas, categorias, patrimônio e compartilhamento não devem destruir o valor histórico dos dados.

---

## 9. Regras de comportamento esperadas do Cursor AI

- Ler os documentos relevantes antes de gerar código estrutural
- Evitar soluções genéricas que conflitem com os documentos oficiais
- Evitar espalhar regras de negócio em camadas inadequadas
- Evitar abstrações desnecessárias antes da hora
- Preferir implementação clara, modular e evolutiva
- Explicitar quando uma sugestão implica alteração arquitetural
- Não assumir simplificações que contrariem a modelagem de família, compartilhamento, multimoeda, monetização ou estrutura modular

---

## 10. Regras específicas para o backend

- Organizar por módulos de domínio
- Evitar arquitetura anêmica
- Evitar concentração excessiva de lógica em controllers
- Evitar repositórios com responsabilidades de negócio
- Manter regras no domínio e na aplicação
- Usar padrões consistentes de DTOs, entidades e casos de uso
- Encapsular integrações externas
- Tratar segurança como parte do domínio
- Preparar código para testes

---

## 11. Regras específicas para aplicações web

- Não misturar código de web, admin e site sem critério
- Manter organização por contexto funcional
- Preservar coerência de UX
- Evitar duplicação desnecessária quando houver real possibilidade de compartilhamento saudável
- Não empurrar regra de negócio crítica para o cliente
- Priorizar clareza, manutenção e previsibilidade

---

## 12. Regras específicas para o mobile

- Priorizar fluxos frequentes
- Favorecer uso rápido e claro
- Não replicar toda a densidade da web na primeira fase
- Integrar ao backend como consumidor de APIs
- Respeitar contexto familiar, notificações e uso cotidiano

---

## 13. Regras específicas para Supabase

Supabase como infraestrutura de apoio: PostgreSQL, Auth, Storage, Realtime.

O Cursor **não** deve transferir para Supabase a lógica central do domínio. O uso deve ser disciplinado e compatível com o backend central em Spring.

---

## 14. Regras específicas para Stripe

Quando a monetização for implementada, o Stripe será o gateway de pagamento. Integração encapsulada, segura e desacoplada do domínio central.

---

## 15. Critérios de qualidade obrigatórios

- Clareza estrutural
- Baixo acoplamento
- Modularidade
- Testabilidade
- Segurança por padrão
- Rastreabilidade
- Compatibilidade com CI/CD e containerização
- Coerência com a documentação oficial

---

## 16. O que o Cursor deve evitar

- Inventar estrutura nova fora da arquitetura sem justificar
- Misturar admin, web e site
- Colocar regra central no frontend
- Usar Supabase como segundo backend de negócio
- Ignorar o modelo de família e compartilhamento
- Ignorar a necessidade de histórico
- Quebrar a modularidade do backend
- Espalhar integrações externas pelo código
- Gerar código "rápido" que comprometa a base estrutural

---

## 17. Modo de trabalho recomendado

Para qualquer tarefa relevante:

1. Ler este documento
2. Ler os documentos de arquitetura e modelo relevantes
3. Entender qual aplicação ou módulo está sendo afetado
4. Propor estrutura coerente
5. Implementar de forma modular
6. Explicar impactos arquiteturais quando houver
7. Não extrapolar o escopo sem deixar explícito

---

## 18. Instrução de uso prático

Quando uma nova tarefa for solicitada ao Cursor, o contexto mínimo recomendado é:

**Considere obrigatoriamente os seguintes documentos:**
- docs/01-produto/documento-mestre-do-balanzo.md
- docs/02-arquitetura/mapa-de-modulos-do-backend.md
- docs/02-arquitetura/requisitos-nao-funcionais.md
- docs/02-arquitetura/modelo-conceitual-de-dados.md
- docs/00-governanca/estrutura-de-repositorio-aplicacoes-e-convencoes.md
- prompts/cursor/00-contexto-geral.md

**Implemente respeitando:**
- backend central em Java com Spring
- frontends separados em Next.js
- mobile em Flutter
- Supabase como infraestrutura de apoio
- Stripe como gateway de pagamento
- modularidade forte
- segurança e privacidade por padrão
- preservação de histórico e contexto familiar

---

## 19. Convenções de linguagem

- **Documentação:** português
- **Código:** inglês (variáveis, funções, classes, comentários, nomes de arquivos)

---

## 20. Conclusão

Este documento é a porta de entrada oficial do Balanzo para o Cursor AI.

Deve ser usado como referência inicial obrigatória antes de qualquer implementação estrutural relevante.

Seu papel é garantir que o desenvolvimento assistido por IA respeite a visão do produto, a arquitetura do sistema, a organização do repositório e as decisões centrais já tomadas no projeto.

---

## Próximo passo do projeto

O próximo artefato recomendado é:

**Regras de Implementação e Convenções de Código para Cursor AI**

Esse documento detalhará padrões práticos de implementação, organização de arquivos, convenções de nomenclatura, comportamento esperado do Cursor ao criar código, estrutura de commits, cuidados com refatorações e critérios técnicos operacionais para manter consistência no desenvolvimento assistido por IA.
