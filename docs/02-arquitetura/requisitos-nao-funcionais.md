# Requisitos Não Funcionais do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento de requisitos não funcionais da plataforma**

---

## 1. Finalidade deste documento

Este documento define os requisitos não funcionais do Balanzo.

Seu objetivo é estabelecer como o sistema deve se comportar do ponto de vista técnico, operacional, arquitetural e qualitativo, complementando os requisitos funcionais já definidos anteriormente.

Enquanto os requisitos funcionais descrevem o que o sistema faz, os requisitos não funcionais definem como ele deve operar, evoluir, proteger dados, responder a falhas, sustentar crescimento e manter qualidade de engenharia.

Este documento servirá como base para:

- decisões arquiteturais
- definição de padrões técnicos
- estruturação da infraestrutura
- estratégia de segurança
- critérios de qualidade de código
- planejamento de observabilidade
- governança do desenvolvimento
- orientação de implementação no Cursor AI

---

## 2. Princípios gerais de engenharia

O Balanzo deve ser desenvolvido seguindo princípios modernos e disciplinados de engenharia de software.

O sistema deve priorizar clareza arquitetural, separação de responsabilidades, modularidade, legibilidade, testabilidade, segurança, rastreabilidade e capacidade de evolução contínua.

As decisões de implementação devem sempre favorecer previsibilidade operacional e redução de acoplamento indevido entre partes do sistema.

O backend em Java com Spring deve ser construído como um **monolito modular orientado a domínios**, evitando tanto complexidade prematura quanto improvisação estrutural.

---

## 3. Requisitos de arquitetura

### 3.1 Estrutura arquitetural

O backend deve adotar uma arquitetura modular baseada em contextos de negócio bem definidos.

A estrutura não deve ser organizada apenas por camadas genéricas, mas por módulos coerentes com os domínios definidos no mapa de módulos do backend.

Cada módulo deve possuir responsabilidade clara, fronteira conceitual estável e comunicação controlada com os demais módulos.

### 3.2 Framework principal

O backend deve utilizar **Spring** como base principal de desenvolvimento, com uso criterioso do ecossistema Spring para construção de APIs, segurança, configuração, integração, persistência e processamento assíncrono.

O uso do framework deve seguir boas práticas e não deve gerar dependência desorganizada de conveniências que prejudiquem clareza arquitetural.

### 3.3 Evolução controlada

A arquitetura deve permitir evolução incremental do sistema, sem exigir reescritas amplas para adicionar novos módulos, novas regras de negócio ou novas interfaces consumidoras.

A base do sistema deve ser preparada para suportar crescimento funcional com baixo impacto colateral.

---

## 4. Requisitos de segurança

### 4.1 Segurança por padrão

A segurança deve ser tratada como requisito estrutural do sistema, não como camada adicional aplicada posteriormente.

Toda funcionalidade nova deve nascer com critérios explícitos de autenticação, autorização, validação de acesso e proteção de dados.

### 4.2 Autenticação

A autenticação inicial do sistema deverá usar **Supabase Auth** como provedor de identidade.

O backend em Spring deve validar corretamente os tokens recebidos e tratar o usuário autenticado como identidade de entrada para o domínio.

### 4.3 Autorização de domínio

A autorização não pode se limitar à autenticação básica.

O backend deve validar contexto, papel do usuário, pertencimento ao núcleo familiar, dono do dado, escopo de visibilidade, escopo analítico e tipo de operação solicitada.

A autorização deve ser aplicada no domínio, e não apenas em filtros superficiais de rota.

### 4.4 Princípio do menor privilégio

Todo usuário, membro administrativo ou processo interno deve possuir apenas os privilégios estritamente necessários para sua função.

Isso vale para usuários finais, membros da equipe interna, integrações externas e componentes de infraestrutura.

### 4.5 Proteção de segredos

Credenciais, tokens, chaves de API, segredos criptográficos e variáveis sensíveis nunca devem estar hardcoded no código-fonte.

Todo segredo deve ser gerenciado por mecanismo apropriado de configuração segura por ambiente.

### 4.6 Proteção contra acesso indevido

O sistema deve proteger contra acesso indevido entre contextos individuais, conjugais, familiares e administrativos.

Nenhum usuário deve conseguir visualizar ou alterar dados fora de seu escopo funcional autorizado.

### 4.7 Arquivos e documentos

Uploads e downloads de arquivos devem ser protegidos por validações de autorização e escopo.

O simples conhecimento de uma URL não pode ser suficiente para acesso irrestrito a documentos sensíveis.

### 4.8 Auditoria de ações sensíveis

Ações relevantes devem ser auditáveis.

Isso inclui exclusões, alterações críticas, mudanças de compartilhamento, convites familiares, reclassificações sensíveis, ações administrativas e mudanças em assinatura ou elegibilidade comercial.

---

## 5. Requisitos de privacidade e proteção de dados

### 5.1 Privacidade por desenho

O sistema deve ser desenhado para preservar privacidade individual mesmo em cenários de compartilhamento familiar.

A modelagem precisa distinguir claramente propriedade do dado, visibilidade do dado e participação analítica do dado.

### 5.2 Minimização de exposição

O frontend e o backend devem trafegar e expor apenas as informações necessárias para cada caso de uso.

Não deve haver retorno excessivo de dados sensíveis em APIs sem necessidade funcional.

### 5.3 Separação de contextos

Dados privados, dados compartilhados e dados administrativos devem ser tratados como contextos distintos de exposição.

Essa distinção deve aparecer tanto na modelagem quanto nas regras de resposta das APIs.

### 5.4 Exclusão e ciclo de vida dos dados

O sistema deve prever regras para arquivamento, desativação, encerramento de conta e eventual exclusão de dados conforme política do produto e obrigações legais aplicáveis.

---

## 6. Requisitos de performance

### 6.1 Desempenho percebido

As operações mais frequentes do sistema devem apresentar tempo de resposta adequado para uso cotidiano confortável.

Consultas simples, criação de transações, leitura de dashboards resumidos, navegação entre contextos e registro de tarefas devem operar com boa responsividade.

### 6.2 Consultas pesadas

Operações potencialmente mais custosas, como importações extensas, reconciliações mais complexas, cálculos patrimoniais amplos e relatórios grandes, devem ser desenhadas para não degradar a experiência geral do sistema.

Sempre que necessário, essas operações devem ser tratadas por processamento assíncrono.

### 6.3 Escalabilidade progressiva

A arquitetura deve suportar crescimento gradual de usuários, dados e complexidade analítica sem necessidade de reconstrução precoce.

### 6.4 Eficiência de banco de dados

As consultas ao banco devem ser desenhadas com atenção a índices, paginação, filtros, agregações e volume esperado de dados.

O sistema não deve depender de consultas ingênuas ou carregamento excessivo de dados para operar corretamente.

---

## 7. Requisitos de disponibilidade e resiliência

### 7.1 Disponibilidade operacional

O sistema deve buscar alta disponibilidade compatível com um produto SaaS em crescimento, ainda que sem exigir complexidade extrema na primeira versão.

### 7.2 Tolerância a falhas parciais

Falhas em partes não críticas do sistema não devem necessariamente derrubar a operação central.

Por exemplo, problemas de notificação, realtime ou anexos não devem interromper completamente o uso das funções financeiras principais, sempre que isso for tecnicamente viável.

### 7.3 Falhas de integração

Falhas em integrações externas devem ser tratadas de forma explícita, rastreável e recuperável.

O sistema deve sinalizar erros de integração sem corromper o estado do domínio.

### 7.4 Reprocessamento

Sempre que possível, operações assíncronas importantes devem permitir reprocessamento controlado em caso de falha.

---

## 8. Requisitos de consistência e integridade

### 8.1 Consistência de domínio

Regras críticas de negócio devem ser executadas de forma consistente e transacional sempre que necessário.

### 8.2 Integridade referencial

A base de dados deve preservar integridade referencial adequada entre entidades centrais do sistema.

### 8.3 Preservação histórica

Mudanças em composição familiar, classificações, metas, patrimônio, assinatura e outras estruturas relevantes não devem destruir a capacidade de interpretação histórica.

### 8.4 Rastreabilidade

O sistema deve permitir rastrear origem de dados importados, alterações relevantes e decisões operacionais que influenciem o estado atual de determinados registros.

---

## 9. Requisitos de observabilidade

### 9.1 Logs estruturados

O sistema deve produzir logs estruturados, consistentes e úteis para diagnóstico técnico e operacional.

### 9.2 Métricas

A plataforma deve expor métricas mínimas de saúde da aplicação, uso de recursos, falhas, jobs assíncronos, integrações e comportamento operacional relevante.

### 9.3 Monitoramento de erros

Erros relevantes devem ser monitorados e correlacionados para análise rápida.

### 9.4 Visibilidade de jobs

Processos assíncronos devem possuir rastreabilidade, status e capacidade de inspeção operacional.

### 9.5 Eventos de domínio relevantes

Eventos importantes, como falha de importação, erro de reconciliação, falha de notificação ou inconsistência de integração, devem ser observáveis.

---

## 10. Requisitos de testabilidade e qualidade

### 10.1 Código testável

O sistema deve ser desenvolvido de forma a favorecer testes automatizados, com baixa dependência de acoplamento oculto e com separação clara entre lógica de domínio e infraestrutura.

### 10.2 Estratégia de testes

O projeto deve contemplar múltiplos níveis de testes, incluindo testes unitários, testes de integração e testes de fluxos críticos.

### 10.3 Cobertura orientada a risco

A prioridade de testes deve recair sobre regras críticas de domínio, fluxos financeiros, autorização, compartilhamento familiar, importação, reconciliação, monetização e integridade de dados.

### 10.4 Validação contínua

O pipeline de CI deve executar validações automáticas para impedir avanço de código com regressões básicas, falhas de build ou violações críticas de qualidade.

---

## 11. Requisitos de manutenibilidade

### 11.1 Clareza estrutural

O código deve ser organizado para facilitar compreensão por novos desenvolvedores e por ferramentas assistidas por IA.

### 11.2 Baixo acoplamento

Módulos devem evitar dependências desnecessárias e comunicação arbitrária.

### 11.3 Convenções consistentes

Nomenclatura, estrutura de pacotes, contratos, tratamento de erros e padrões de implementação devem seguir convenções estáveis.

### 11.4 Refatorabilidade

A estrutura do sistema deve permitir refatorações locais sem provocar efeito em cadeia desproporcional.

---

## 12. Requisitos de escalabilidade técnica

### 12.1 Escalabilidade vertical inicial

A arquitetura deve aceitar crescimento inicial por aumento de capacidade da infraestrutura sem exigir distribuição complexa prematura.

### 12.2 Escalabilidade modular futura

A modularidade do backend deve permitir, se necessário no futuro, extração seletiva de partes específicas para serviços separados.

### 12.3 Crescimento de dados

O sistema deve ser preparado para aumento consistente de transações, arquivos, usuários, núcleos familiares, tarefas e históricos operacionais.

---

## 13. Requisitos de processamento assíncrono

### 13.1 Jobs internos

Operações custosas ou demoradas devem poder ser processadas assíncronamente.

### 13.2 Casos típicos

Importação de arquivos, reconciliação, notificações, geração de relatórios extensos, reprocessamentos e sincronizações devem estar entre os casos naturalmente elegíveis a processamento assíncrono.

### 13.3 Rastreabilidade

Todo job relevante deve possuir status, histórico, erro associado quando aplicável e possibilidade de acompanhamento administrativo.

---

## 14. Requisitos de integração

### 14.1 Integração com Supabase

O sistema deve se integrar ao Supabase de forma disciplinada, usando-o como base de autenticação, banco, storage e recursos auxiliares, sem deslocar para ele o núcleo do domínio.

### 14.2 Integração com Stripe

O gateway de pagamento definido para a plataforma será o **Stripe**.

A integração futura com Stripe deverá seguir boas práticas de segurança, rastreabilidade, eventos de cobrança, reconciliação de assinatura e tratamento de falhas externas.

### 14.3 Isolamento das integrações

Integrações externas devem ficar encapsuladas em componentes específicos, sem espalhar dependências de fornecedor pelo domínio central do sistema.

---

## 15. Requisitos de deploy e ambientes

### 15.1 Containerização

Todos os componentes principais do projeto devem ser preparados para execução em containers.

### 15.2 Ambientes separados

O sistema deve possuir ambientes distintos para desenvolvimento local, homologação e produção.

### 15.3 Configuração por ambiente

Variáveis, endpoints, segredos, parâmetros e políticas operacionais devem ser configuráveis por ambiente sem necessidade de alteração de código.

### 15.4 Reprodutibilidade

O ambiente local deve ser o mais previsível e reproduzível possível para reduzir divergência entre desenvolvimento e produção.

---

## 16. Requisitos de CI/CD

### 16.1 Integração contínua

Todo código novo deve passar por pipeline automatizado com build, testes e validações mínimas antes de integração.

### 16.2 Entrega contínua

A publicação em ambientes deve seguir fluxo controlado, reproduzível e auditável.

### 16.3 Qualidade como gate

Falhas de testes, build quebrado, problemas críticos de linting ou violações importantes de qualidade devem bloquear avanço no pipeline.

### 16.4 Migrations controladas

Mudanças de banco de dados devem seguir processo versionado, rastreável e compatível com evolução contínua do sistema.

---

## 17. Requisitos de documentação técnica

### 17.1 Documentação viva

A documentação do projeto deve ser tratada como artefato vivo e versionado.

### 17.2 Alinhamento entre código e documento

Mudanças estruturais relevantes devem atualizar também a documentação correspondente.

### 17.3 Material para IA

O projeto deve manter documentação adequada para consumo por ferramentas de desenvolvimento assistido por IA, com estrutura clara e contexto estável.

---

## 18. Requisitos de usabilidade técnica e UX operacional

### 18.1 Operação fluida

Mesmo sendo um requisito parcialmente funcional, a experiência do sistema deve ser desenhada para minimizar atrito em tarefas frequentes.

### 18.2 Fluxos críticos simples

Registro de transação, categorização, navegação entre contextos, criação de tarefas, leitura de alertas e visualização de informação principal devem ter complexidade operacional baixa.

### 18.3 Mobile pragmático

O aplicativo móvel deve priorizar rapidez, clareza e uso cotidiano, evitando excesso de densidade em fluxos frequentes.

---

## 19. Requisitos de governança técnica

### 19.1 Decisões explícitas

Decisões arquiteturais relevantes devem ser registradas formalmente.

### 19.2 Padrões obrigatórios

O projeto deve adotar padrões explícitos para arquitetura, testes, segurança, nomenclatura, versionamento e integração.

### 19.3 Redução de improviso

Implementações estruturais não devem ser conduzidas apenas por conveniência momentânea. Elas devem respeitar a documentação-base do projeto.

---

## 20. Critérios gerais de qualidade do backend

O backend do Balanzo será considerado bem implementado se apresentar as seguintes características:

- estrutura modular clara
- regras de negócio centralizadas no domínio
- segurança aplicada por padrão
- baixo acoplamento entre módulos
- testes adequados para fluxos críticos
- observabilidade suficiente para diagnóstico
- integrações encapsuladas
- capacidade de evolução sem degradação estrutural

---

## 21. Conclusão

Os requisitos não funcionais do Balanzo estabelecem a base de qualidade e disciplina técnica necessária para que o sistema não seja apenas funcional, mas também seguro, sustentável, evolutivo e confiável.

Eles complementam os requisitos funcionais e o mapa de módulos do backend, transformando a visão do projeto em um sistema tecnicamente governável.

A partir deste ponto, o projeto já possui três pilares documentais fundamentais:

1. Documento Mestre
2. Mapa de Módulos do Backend
3. Requisitos Não Funcionais

---

## Próximo passo do projeto

O próximo artefato recomendado é:

**Modelo Conceitual de Dados do Balanzo**

Esse documento definirá as principais entidades do sistema, seus relacionamentos centrais, seus contextos de propriedade, visibilidade e consolidação, servindo como ponte entre arquitetura e modelagem real de banco de dados.
