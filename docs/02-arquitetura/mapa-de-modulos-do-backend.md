# Mapa de Módulos do Backend do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento de estrutura modular do backend**

---

## 1. Finalidade deste documento

Este documento define o mapa de módulos do backend do Balanzo.

Seu objetivo é transformar a visão conceitual do produto e a arquitetura técnica inicial em uma estrutura concreta de engenharia, organizada por domínios de negócio e preparada para implementação em um backend Java modular.

Este material deve servir como referência para:

- organização do backend em módulos claros
- delimitação de responsabilidades por domínio
- definição de fronteiras internas do sistema
- planejamento de entidades, serviços e APIs
- orientação de implementação no Cursor AI
- redução de acoplamento entre partes do sistema

O princípio central adotado neste documento é que o backend do Balanzo deve ser construído como um **monolito modular orientado a domínios**, e não como um conjunto difuso de camadas genéricas.

---

## 2. Diretriz estrutural do backend

O backend do Balanzo deve ser organizado em módulos de negócio independentes do ponto de vista conceitual, porém integrados dentro de uma única aplicação principal.

Cada módulo deve possuir responsabilidade clara, linguagem de domínio própria, entidades e serviços coerentes com seu contexto e contratos bem definidos para interação com os demais módulos.

A modularização não é apenas uma decisão de organização de pastas. Ela é uma decisão de arquitetura. Isso significa que cada módulo deve representar um recorte real do negócio e não apenas uma divisão artificial do código.

A meta é garantir:

- clareza de responsabilidade
- isolamento relativo entre domínios
- facilidade de manutenção
- preparação para crescimento futuro
- redução de impacto colateral entre mudanças

---

## 3. Visão geral do mapa modular

O backend do Balanzo deve ser organizado em doze grandes módulos centrais.

1. **Módulo de identidade e acesso**
2. **Módulo de estrutura familiar**
3. **Módulo financeiro base**
4. **Módulo de categorização e organização financeira**
5. **Módulo de planejamento financeiro**
6. **Módulo de patrimônio e investimentos**
7. **Módulo de colaboração familiar e compensações**
8. **Módulo de tarefas, projetos e templates familiares**
9. **Módulo documental**
10. **Módulo de notificações e eventos operacionais**
11. **Módulo de monetização e assinatura**
12. **Módulo administrativo e de auditoria operacional**

Além desses módulos centrais, o backend deve conter uma **camada transversal de infraestrutura interna**, responsável por persistência, mensageria, segurança, jobs, integração externa, observabilidade e suporte técnico ao funcionamento do sistema.

---

## 4. Módulo de identidade e acesso

### 4.1 Objetivo do módulo

Este módulo é responsável por representar a identidade básica dos usuários e seu relacionamento inicial com a plataforma.

Ele não deve concentrar a lógica completa de autorização de domínio, mas deve ser o ponto de referência para dados de usuário, perfil básico, preferências pessoais, status de conta e vínculo com o provedor de autenticação.

### 4.2 Responsabilidades

Este módulo deve tratar:

- cadastro lógico de usuário dentro do domínio
- vínculo com identidade vinda do Supabase Auth
- perfil principal do usuário
- preferências operacionais básicas
- idioma, moeda principal e parâmetros pessoais
- estado da conta do usuário
- ciclo de vida da conta
- encerramento ou solicitação de exclusão

### 4.3 Entidades principais esperadas

As principais entidades conceituais deste módulo tendem a incluir:

- Usuário
- Perfil do Usuário
- Preferências do Usuário
- Conta da Plataforma
- Sessão lógica, se necessária ao domínio

### 4.4 Dependências e relações

Este módulo será referenciado por praticamente todos os outros módulos, pois o usuário é a unidade básica de identidade do sistema.

Ainda assim, ele não deve se tornar um módulo "dono de tudo". Ele fornece identidade. Os demais módulos devem carregar sua própria lógica de domínio.

---

## 5. Módulo de estrutura familiar

### 5.1 Objetivo do módulo

Este módulo é responsável por modelar a composição familiar e os espaços compartilhados do sistema.

Ele representa um dos maiores diferenciais do Balanzo e deve permitir coexistência entre contexto individual, conjugal e familiar.

### 5.2 Responsabilidades

Este módulo deve tratar:

- núcleos familiares
- espaços financeiros compartilhados
- participantes do núcleo
- papéis dentro do núcleo
- convites e aceitação
- temporalidade da composição do grupo
- níveis de visibilidade entre participantes
- regras estruturais de compartilhamento
- dependentes e participantes com acesso restrito

### 5.3 Entidades principais esperadas

As entidades conceituais mais prováveis são:

- Núcleo Familiar
- Membro do Núcleo
- Convite para Núcleo
- Papel do Membro
- Participação Temporal
- Dependente
- Configuração de Visibilidade Familiar

### 5.4 Observação arquitetural importante

Este módulo não deve tentar resolver sozinho tudo o que é compartilhado no sistema.

Ele define a estrutura da família e do contexto compartilhado.

O significado do compartilhamento em contas, transações, metas, ativos e tarefas será tratado nos respectivos módulos, mas sempre com apoio conceitual deste módulo.

---

## 6. Módulo financeiro base

### 6.1 Objetivo do módulo

Este é o núcleo transacional do sistema. Ele concentra a modelagem de contas, cartões, transações, transferências, saldos e movimentações financeiras.

### 6.2 Responsabilidades

Este módulo deve tratar:

- contas financeiras
- cartões de crédito e estruturas equivalentes
- transações financeiras
- receitas
- despesas
- transferências internas
- ajustes de saldo
- faturas de cartão
- parcelamentos
- importação manual de extratos
- lotes de importação
- reconciliação inicial de registros

### 6.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Conta Financeira
- Cartão
- Fatura
- Transação
- Lançamento Parcelado
- Transferência Interna
- Lote de Importação
- Item Importado
- Reconciliação Financeira

### 6.4 Fronteira deste módulo

Este módulo não deve assumir a função completa de análise financeira nem de planejamento.

Ele registra e organiza a base factual da movimentação financeira.

A leitura analítica, o orçamento, as metas e a colaboração familiar estendida pertencem a módulos adjacentes.

---

## 7. Módulo de categorização e organização financeira

### 7.1 Objetivo do módulo

Este módulo existe para organizar semanticamente a movimentação financeira.

Ele separa a dimensão da movimentação da dimensão da interpretação do gasto ou da receita.

### 7.2 Responsabilidades

Este módulo deve tratar:

- categorias padrão
- categorias personalizadas
- subcategorias
- tags
- regras automáticas de categorização
- reclassificação em lote
- histórico de padrões de classificação
- centros de responsabilidade familiar
- classificações de contexto econômico

### 7.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Categoria
- Subcategoria
- Tag
- Regra de Classificação
- Centro de Responsabilidade
- Classificação de Contexto
- Preferência de Classificação

### 7.4 Relação com outros módulos

Este módulo será fortemente usado pelo módulo financeiro base, pelo módulo de planejamento financeiro e pelo módulo de colaboração familiar.

Ele é um módulo semântico e organizacional.

---

## 8. Módulo de planejamento financeiro

### 8.1 Objetivo do módulo

Este módulo trata da camada prospectiva e organizacional das finanças.

Enquanto o módulo financeiro base registra fatos, o módulo de planejamento organiza intenção, meta, revisão, disciplina e acompanhamento de ciclo.

### 8.2 Responsabilidades

Este módulo deve tratar:

- orçamentos individuais
- orçamentos familiares
- metas financeiras
- provisões para despesas futuras
- hábitos financeiros
- fechamento de período
- rotina de revisão financeira
- planejamento de compras importantes
- checklist de saúde financeira
- objetivos operacionais não estritamente monetários

### 8.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Orçamento
- Item de Orçamento
- Meta Financeira
- Aporte de Meta
- Provisão
- Ciclo de Revisão
- Fechamento de Período
- Indicador de Saúde Financeira
- Objetivo Operacional

### 8.4 Fronteira deste módulo

Este módulo não deve controlar diretamente os lançamentos financeiros, mas deve consumi-los para fins de comparação entre planejado e realizado.

Também não deve assumir a modelagem patrimonial profunda, embora converse com ela em metas e consolidações.

---

## 9. Módulo de patrimônio e investimentos

### 9.1 Objetivo do módulo

Este módulo representa a camada patrimonial do sistema.

Seu papel é consolidar bens, investimentos, passivos patrimoniais e visão de patrimônio líquido.

### 9.2 Responsabilidades

Este módulo deve tratar:

- ativos patrimoniais
- bens físicos
- investimentos financeiros
- carteiras
- passivos patrimoniais
- dívidas estruturadas
- cronogramas de amortização
- patrimônio líquido
- consolidação patrimonial
- multimoeda patrimonial

### 9.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Ativo
- Classe de Ativo
- Carteira de Investimentos
- Posição de Investimento
- Passivo
- Dívida
- Parcela de Dívida
- Patrimônio Consolidado
- Snapshot Patrimonial

### 9.4 Observação importante

Este módulo deve distinguir patrimônio individual, patrimônio conjugal e patrimônio familiar, inclusive quando a titularidade formal e o contexto econômico não coincidirem.

Essa nuance é central para o Balanzo.

---

## 10. Módulo de colaboração familiar e compensações

### 10.1 Objetivo do módulo

Este módulo trata das interações financeiras entre membros do núcleo familiar.

Ele é necessário porque a vida financeira compartilhada envolve rateios, compensações, reembolsos, distribuição de responsabilidade e acompanhamento de participação econômica.

### 10.2 Responsabilidades

Este módulo deve tratar:

- despesas compartilhadas
- rateios
- reembolsos entre membros
- compensações internas
- responsabilidade de pagamento
- responsabilidade econômica
- participação de dependentes
- mesadas e carteiras supervisionadas
- repasses periódicos a dependentes
- visões híbridas entre individual e compartilhado

### 10.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Despesa Compartilhada
- Rateio
- Quota de Participação
- Reembolso
- Compensação Interna
- Carteira Supervisionada
- Mesada
- Responsabilidade Econômica

### 10.4 Relação com outros módulos

Este módulo conversa fortemente com o módulo financeiro base, com a estrutura familiar e com o módulo de planejamento.

Ele não substitui o financeiro base. Ele o complementa com lógica de compartilhamento econômico e responsabilidade entre membros.

---

## 11. Módulo de tarefas, projetos e templates familiares

### 11.1 Objetivo do módulo

Este módulo representa a camada organizacional não estritamente financeira do sistema.

Seu papel é tornar o Balanzo também um centro de organização familiar, conectando tarefas, projetos domésticos, responsabilidades e elementos de engajamento.

### 11.2 Responsabilidades

Este módulo deve tratar:

- tarefas simples
- grupos de tarefas
- projetos familiares
- responsáveis e participantes
- status operacionais
- kanban de atividades
- histórico de execução
- templates de tarefas
- templates de projetos
- reutilização de estruturas
- gamificação e incentivos
- pontuação, bônus e recompensas simples

### 11.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Tarefa
- Grupo de Tarefas
- Projeto Familiar
- Participação em Tarefa
- Template de Tarefa
- Template de Projeto
- Pontuação
- Recompensa
- Histórico de Atividade

### 11.4 Integração com o restante do sistema

Este módulo pode se relacionar com metas, provisões, projetos patrimoniais e planejamento financeiro, mas deve preservar sua autonomia de domínio.

Ele não deve ser modelado como simples "anexo do financeiro".

---

## 12. Módulo documental

### 12.1 Objetivo do módulo

Este módulo gerencia o vínculo entre arquivos e entidades do sistema.

Seu foco não é apenas armazenar arquivos, mas garantir integridade documental no contexto do domínio.

### 12.2 Responsabilidades

Este módulo deve tratar:

- anexos de transações
- documentos de ativos
- arquivos de importação
- comprovantes
- documentos de tarefas ou projetos
- vínculo entre arquivo e entidade
- metadados do documento
- controle lógico de acesso ao documento

### 12.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Documento
- Anexo
- Tipo de Documento
- Referência Documental
- Arquivo Importado
- Metadado de Arquivo

### 12.4 Observação arquitetural

Os arquivos em si devem estar em storage de objetos. Este módulo controla o significado documental e o vínculo com o domínio.

---

## 13. Módulo de notificações e eventos operacionais

### 13.1 Objetivo do módulo

Este módulo trata comunicação operacional, emissão de alertas e reação a eventos relevantes do sistema.

### 13.2 Responsabilidades

Este módulo deve tratar:

- alertas financeiros
- lembretes de vencimento
- notificações de tarefas
- notificações de convites familiares
- avisos de orçamento
- alertas de falha de integração
- eventos operacionais internos
- fila de emissão de notificações
- preferências de notificação

### 13.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Notificação
- Evento do Sistema
- Canal de Notificação
- Preferência de Notificação
- Entrega de Notificação
- Regra de Alerta

### 13.4 Fronteira deste módulo

Este módulo não decide sozinho as regras do negócio. Ele recebe eventos e critérios produzidos por outros módulos e cuida da materialização comunicacional.

---

## 14. Módulo de monetização e assinatura

### 14.1 Objetivo do módulo

Este módulo representa a camada comercial do produto SaaS.

### 14.2 Responsabilidades

Este módulo deve tratar:

- planos
- recursos liberados por plano
- limites de uso
- assinaturas
- status de assinatura
- mudança de plano
- períodos de avaliação
- regras de acesso comercial
- controle de elegibilidade funcional por plano

### 14.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Plano
- Recurso de Plano
- Limite de Plano
- Assinatura
- Histórico de Assinatura
- Elegibilidade Comercial

### 14.4 Observação importante

A cobrança em si poderá depender de integração externa. Ainda assim, a lógica de elegibilidade e restrição funcional deve morar no domínio do backend.

---

## 15. Módulo administrativo e auditoria operacional

### 15.1 Objetivo do módulo

Este módulo serve à operação interna do Balanzo.

Ele deve permitir que a plataforma seja administrada de forma segura, rastreável e organizada.

### 15.2 Responsabilidades

Este módulo deve tratar:

- visão administrativa de usuários
- estado de contas e assinaturas
- análise operacional de uso
- acompanhamento de falhas
- logs funcionais
- auditoria de alterações relevantes
- suporte operacional
- visão administrativa de integrações
- incidentes e acompanhamento interno

### 15.3 Entidades principais esperadas

As entidades conceituais mais prováveis incluem:

- Registro de Auditoria
- Evento Administrativo
- Incidente Operacional
- Atendimento Interno
- Ação Administrativa
- Log Funcional

### 15.4 Fronteira deste módulo

Este módulo não substitui observabilidade técnica de infraestrutura. Ele trata a camada operacional e funcional da plataforma do ponto de vista do negócio e da administração do produto.

---

## 16. Camadas transversais internas do backend

Além dos módulos de domínio, o backend deve conter camadas internas transversais que sustentam o funcionamento da aplicação.

### 16.1 Camada de API

Responsável por expor endpoints, validar entrada, adaptar requisições e respostas e encaminhar para os casos de uso adequados.

### 16.2 Camada de aplicação

Responsável por orquestrar casos de uso, fluxos entre módulos e operações compostas.

### 16.3 Camada de domínio

Responsável por entidades, regras, invariantes, serviços de domínio e contratos centrais.

### 16.4 Camada de persistência

Responsável por repositórios, mapeamento relacional, consultas especializadas e acesso a dados.

### 16.5 Camada de integração externa

Responsável por comunicação com Supabase, serviços de storage, notificações, gateways de cobrança e integrações futuras.

### 16.6 Camada assíncrona

Responsável por jobs internos, processamento de importações, reconciliações pesadas, notificações e tarefas em segundo plano.

### 16.7 Camada de segurança

Responsável por interpretação de identidade, contexto de autorização, escopo de acesso, validações de segurança e proteção de fluxos internos.

---

## 17. Relações principais entre os módulos

- O **módulo de identidade e acesso** é base para todos os demais.

- O **módulo de estrutura familiar** conversa diretamente com financeiro base, colaboração familiar, planejamento, patrimônio e tarefas.

- O **módulo financeiro base** fornece fatos transacionais para categorização, planejamento, colaboração familiar, notificações e administração.

- O **módulo de categorização** enriquece semanticamente o financeiro base e alimenta planejamento e análise.

- O **módulo de planejamento** consome dados do financeiro base, da categorização, da colaboração familiar e do patrimônio.

- O **módulo de patrimônio** conversa com identidade, estrutura familiar, planejamento e monetização, quando houver recursos premium relacionados.

- O **módulo de colaboração familiar** depende fortemente de estrutura familiar e financeiro base.

- O **módulo de tarefas e projetos familiares** depende de estrutura familiar e pode se integrar opcionalmente com planejamento e notificações.

- O **módulo documental** apoia financeiro base, patrimônio, tarefas e administração.

- O **módulo de notificações** recebe eventos de quase todos os demais módulos.

- O **módulo de monetização** influencia acesso funcional em toda a plataforma.

- O **módulo administrativo** consome informações operacionais de todos os módulos relevantes.

---

## 18. Princípios de implementação modular

- Cada módulo deve possuir nome claro e semântica estável.

- Cada módulo deve evitar depender internamente de detalhes desnecessários de outros módulos.

- A comunicação entre módulos deve ocorrer por contratos claros, serviços de aplicação ou eventos internos bem definidos.

- Entidades de um módulo não devem ser manipuladas arbitrariamente por outro módulo sem passar por suas regras.

- Regras de negócio devem ficar no backend e não migrar para cliente, banco ou automações dispersas.

- Shared code deve ser mínimo e cuidadosamente controlado para não destruir a modularidade.

---

## 19. Proposta inicial de organização lógica no código

Em termos conceituais, o backend deve se organizar com uma raiz principal de aplicação e submódulos bem definidos por domínio.

Uma estrutura conceitual coerente seria algo como:

```
identidade
familia
financeiro
classificacao
planejamento
patrimonio
colaboracao
tarefas
documentos
notificacoes
monetizacao
administracao
```

Cada módulo deve conter suas próprias classes de domínio, casos de uso, controladores, repositórios e objetos de contrato, respeitando um padrão interno consistente.

---

## 20. Critério de sucesso deste mapa modular

Este documento terá cumprido seu papel se ele permitir que o backend do Balanzo seja desenvolvido com clareza suficiente para responder às seguintes perguntas:

- qual módulo é responsável por cada parte do negócio
- onde determinada regra deve morar
- como os módulos conversam
- qual é a fronteira de cada domínio
- como evitar mistura de conceitos no backend

Se essas respostas permanecerem claras ao longo do desenvolvimento, a arquitetura modular terá sido bem definida.

---

## 21. Conclusão

O backend do Balanzo deve ser entendido como uma plataforma de domínio robusta, organizada por módulos de negócio e preparada para sustentar a complexidade própria de um sistema financeiro, patrimonial e familiar.

O mapa modular apresentado neste documento estabelece a base concreta de engenharia necessária para a implementação disciplinada do sistema.

Ele não substitui o modelo conceitual de dados nem os requisitos não funcionais, mas prepara o terreno para ambos.

A partir daqui, o backend passa a ter uma forma estrutural definida.

---

## Próximo passo do projeto

Agora que o mapa de módulos do backend foi definido, o próximo artefato recomendado é:

**Requisitos Não Funcionais do Balanzo**

Esse documento será responsável por definir como o sistema deve se comportar em termos de segurança, performance, disponibilidade, escalabilidade, privacidade, observabilidade, qualidade operacional e governança técnica.
