# Modelo Conceitual de Dados do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento de modelagem conceitual de dados**

---

## 1. Finalidade deste documento

Este documento define o modelo conceitual de dados do Balanzo.

Seu objetivo é estabelecer as principais entidades do sistema, seus relacionamentos centrais, seus contextos de uso e suas fronteiras conceituais, servindo como ponte entre a visão do produto, a arquitetura do backend e a futura modelagem lógica e física do banco de dados.

Este material não representa ainda o schema relacional definitivo. Ele representa a estrutura conceitual do domínio.

Ele servirá como referência para:

- modelagem lógica do banco de dados
- definição de entidades de domínio no backend
- organização de contratos entre módulos
- planejamento de APIs
- orientação para implementação no Cursor AI
- preservação de coerência entre produto e persistência

---

## 2. Princípios de modelagem adotados

O modelo conceitual de dados do Balanzo deve seguir alguns princípios fundamentais.

1. **Múltiplos contextos:** O sistema não representa apenas dados de um usuário isolado. Ele representa indivíduos, famílias, dependentes, contextos compartilhados e visões analíticas múltiplas.

2. **Propriedade, visibilidade e participação:** Propriedade do dado, visibilidade do dado e participação analítica do dado são conceitos distintos e devem ser modelados separadamente quando necessário.

3. **Preservação de histórico:** Mudanças de classificação, composição familiar, contexto de compartilhamento, metas e responsabilidades não podem destruir a interpretação histórica.

4. **Fronteiras de domínio:** O sistema possui múltiplos domínios integrados. Financeiro, patrimonial, familiar, tarefas, monetização e administração coexistem, mas devem manter fronteiras claras.

5. **Evolução incremental:** O modelo deve permitir evolução incremental. A primeira versão precisa ser robusta, mas não pode ser desenhada de forma rígida a ponto de dificultar crescimento.

---

## 3. Macrodomínios de dados do sistema

O modelo do Balanzo pode ser entendido em oito macrodomínios:

1. Identidade e conta da plataforma
2. Estrutura familiar e compartilhamento
3. Estrutura financeira transacional
4. Planejamento financeiro
5. Patrimônio e investimentos
6. Colaboração familiar e compensações
7. Tarefas, projetos e templates
8. Monetização, documentos, notificações e administração

Cada macrodomínio contém suas próprias entidades, mas se relaciona com os demais por chaves conceituais bem definidas.

---

## 4. Núcleo de identidade da plataforma

### 4.1 Entidade Usuário

A entidade Usuário representa a pessoa autenticada no sistema como identidade principal da plataforma.

Ela deve existir como entidade de domínio própria, mesmo que a autenticação seja fornecida por Supabase Auth.

**Atributos conceituais esperados:**

- identificador interno
- identificador externo do provedor de autenticação
- nome principal
- e-mail principal
- status da conta
- data de criação
- data de atualização
- estado de ativação
- marcadores de exclusão ou encerramento

### 4.2 Entidade Perfil do Usuário

Representa informações pessoais e operacionais para personalizar a experiência.

**Atributos conceituais esperados:**

- idioma preferencial
- moeda principal
- país ou contexto principal
- fuso horário
- nível de detalhamento da experiência
- preferências gerais de uso

### 4.3 Entidade Preferência de Usuário

Representa configurações operacionais mais granulares.

Pode incluir: preferências de notificação, visualização, regras padrão de categorização, configurações de comportamento recorrente, preferências de exibição analítica.

---

## 5. Núcleo de estrutura familiar e compartilhamento

### 5.1 Entidade Núcleo Familiar

Representa o agrupamento principal de colaboração entre múltiplos participantes.

**Atributos conceituais esperados:**

- identificador do núcleo
- nome do núcleo
- status do núcleo
- data de criação
- data de encerramento, se existir
- configurações gerais do núcleo

### 5.2 Entidade Membro do Núcleo

Representa a participação de um usuário em um núcleo familiar específico.

**Atributos conceituais esperados:**

- identificador da participação
- referência ao usuário
- referência ao núcleo
- papel do membro
- status da participação
- data de entrada
- data de saída, se existir
- marcadores de acesso restrito

### 5.3 Entidade Papel do Membro

Representa o papel funcional dentro do núcleo. Exemplos: responsável principal, coadministrador, membro adulto, dependente supervisionado, participante com acesso limitado.

### 5.4 Entidade Convite para Núcleo

Representa o processo de ingresso de novos participantes.

Deve permitir rastrear: quem convidou, quem foi convidado, qual núcleo, qual papel proposto, status do convite, datas relevantes.

### 5.5 Entidade Dependente

Representa pessoa vinculada ao núcleo que pode ou não possuir acesso próprio à plataforma.

**Atributos conceituais esperados:**

- nome do dependente
- vínculo com o núcleo
- tipo de dependente
- grau de autonomia
- usuário vinculado, se existir
- configurações de supervisão

### 5.6 Entidade Regra de Visibilidade Familiar

Representa configurações de compartilhamento dentro do núcleo.

Pode expressar: quem pode visualizar, editar, colaborar; quem apenas recebe efeito analítico; qual escopo de dado a regra alcança.

---

## 6. Núcleo de estrutura financeira transacional

### 6.1 Entidade Conta Financeira

Representa uma fonte de saldo ou movimentação financeira.

**Atributos conceituais esperados:**

- nome da conta
- tipo da conta
- instituição financeira
- moeda da conta
- titular principal
- status
- saldo inicial
- data de início
- configuração de compartilhamento
- contexto analítico padrão

### 6.2 Entidade Cartão

Representa cartão de crédito ou estrutura equivalente.

**Atributos conceituais esperados:**

- nome do cartão
- emissor
- moeda
- limite
- data de fechamento
- data de vencimento
- conta pagadora associada
- titular principal
- status
- configuração de visibilidade

### 6.3 Entidade Fatura

Representa um ciclo consolidado de gastos do cartão.

Permite: relacionar o cartão, definir período de fechamento, consolidar valor aberto, registrar status de pagamento, rastrear pagamento parcial ou integral.

### 6.4 Entidade Transação

Entidade central do modelo. Representa o evento financeiro fundamental.

**Atributos conceituais esperados:**

- tipo de transação
- data da transação
- data de competência, se aplicável
- valor original
- moeda original
- valor consolidado, quando aplicável
- descrição
- conta de origem
- conta de destino, se aplicável
- cartão associado, se aplicável
- categoria, subcategoria, tags
- contexto analítico
- proprietário do registro
- escopo de visibilidade
- origem do dado
- status da transação
- indicação de recorrência
- indicação de parcelamento
- referência documental, se houver

### 6.5 Entidade Transferência Interna

Representa movimentação entre duas contas do mesmo contexto.

### 6.6 Entidade Importação Financeira

Representa o processo de entrada de dados por arquivo ou fonte externa.

### 6.7 Entidade Item Importado

Representa cada linha ou evento identificado em uma importação.

### 6.8 Entidade Reconciliação

Representa o vínculo entre registros potencialmente equivalentes vindos de fontes distintas.

---

## 7. Núcleo de classificação financeira

### 7.1 Entidade Categoria

Representa a classificação principal de receitas e despesas.

### 7.2 Entidade Subcategoria

Representa refinamento da categoria.

### 7.3 Entidade Tag

Representa marcação livre adicional para organização e filtros.

### 7.4 Entidade Regra de Classificação

Representa automações que sugerem ou aplicam classificação com base em padrões.

### 7.5 Entidade Centro de Responsabilidade

Representa para quem ou qual contexto econômico uma receita ou despesa deve ser atribuída.

Exemplos: uso pessoal, casa, casal, filho específico, saúde familiar, educação, transporte da família.

### 7.6 Entidade Contexto Analítico

Representa a camada de leitura do dado. Distingue individual, compartilhado, familiar, dependente ou consolidado.

---

## 8. Núcleo de planejamento financeiro

### 8.1 Entidade Orçamento

Representa um plano financeiro para determinado período e contexto.

**Atributos conceituais esperados:**

- nome do orçamento
- período de vigência
- contexto de aplicação
- tipo de orçamento
- status
- responsável
- regras de revisão

### 8.2 Entidade Item de Orçamento

Representa limites ou previsões específicas dentro do orçamento.

### 8.3 Entidade Meta Financeira

Representa objetivo financeiro a ser alcançado.

**Atributos conceituais esperados:**

- nome da meta
- valor alvo
- prazo
- prioridade
- contexto da meta
- status
- participantes da meta
- critérios de consolidação

### 8.4 Entidade Participante de Meta

Representa quem participa da meta e com qual papel.

### 8.5 Entidade Aporte de Meta

Representa contribuições realizadas para evolução de uma meta.

### 8.6 Entidade Provisão

Representa reserva planejada para despesa futura.

### 8.7 Entidade Ciclo de Revisão

Representa processo de revisão periódica das finanças.

### 8.8 Entidade Indicador de Saúde Financeira

Representa elementos de diagnóstico da situação financeira.

### 8.9 Entidade Objetivo Operacional

Representa metas de comportamento ou organização, não estritamente monetárias.

---

## 9. Núcleo patrimonial e de investimentos

### 9.1 Entidade Ativo

Representa bem ou posição com valor econômico.

**Atributos conceituais esperados:**

- nome do ativo
- tipo
- valor de aquisição
- valor atual
- moeda
- titularidade formal
- contexto patrimonial analítico
- data de aquisição
- status

### 9.2 Entidade Classe de Ativo

Representa agrupamentos analíticos dos ativos.

### 9.3 Entidade Carteira de Investimentos

Representa agrupamento de posições financeiras.

### 9.4 Entidade Posição de Investimento

Representa participação específica em ativo financeiro.

### 9.5 Entidade Passivo

Representa obrigação patrimonial ampla.

### 9.6 Entidade Dívida

Representa obrigação financeira estruturada com saldo devedor, parcelas e cronograma.

### 9.7 Entidade Parcela de Dívida

Representa as parcelas individuais de uma obrigação.

### 9.8 Entidade Snapshot Patrimonial

Representa fotografia consolidada do patrimônio em determinado momento.

---

## 10. Núcleo de colaboração familiar e compensações

### 10.1 Entidade Despesa Compartilhada

Representa gasto cuja responsabilidade econômica é distribuída entre participantes.

### 10.2 Entidade Rateio

Representa a divisão de responsabilidade econômica de uma despesa ou obrigação.

### 10.3 Entidade Quota de Participação

Representa quanto cada participante assume em determinado rateio.

### 10.4 Entidade Reembolso

Representa compensação devida entre participantes.

### 10.5 Entidade Compensação Interna

Representa ajustes entre membros, acertos de saldo.

### 10.6 Entidade Carteira Supervisionada

Representa estrutura controlada para dependentes.

### 10.7 Entidade Mesada

Representa repasse programado para dependente.

### 10.8 Entidade Responsabilidade Econômica

Representa a atribuição analítica do impacto financeiro.

---

## 11. Núcleo de tarefas, projetos e templates

### 11.1 Entidade Tarefa

Representa unidade básica de atividade familiar.

**Atributos conceituais esperados:**

- título
- descrição
- status
- prioridade
- responsável
- participantes
- prazo
- contexto familiar
- vínculo opcional com elementos financeiros
- pontuação associada, se houver

### 11.2 a 11.9 Outras entidades

- Grupo de Tarefas
- Projeto Familiar
- Participação em Tarefa
- Template de Tarefa
- Template de Projeto
- Pontuação
- Recompensa
- Histórico de Atividade

---

## 12. Núcleo documental

- **Documento:** registro lógico de arquivo
- **Anexo:** vínculo documental com entidade do domínio
- **Tipo de Documento:** classificação do papel do arquivo
- **Referência Documental:** associação entre documento e entidade principal

---

## 13. Núcleo de notificações e eventos

- Evento do Sistema
- Notificação
- Canal de Notificação
- Preferência de Notificação
- Entrega de Notificação
- Regra de Alerta

---

## 14. Núcleo de monetização e assinatura

- Plano
- Recurso de Plano
- Limite de Plano
- Assinatura
- Histórico de Assinatura
- Elegibilidade Comercial

**Integração futura com Stripe:** a camada conceitual de assinatura deve estar preparada para integração com Stripe sem contaminar o modelo central.

---

## 15. Núcleo administrativo e de auditoria

- Registro de Auditoria
- Evento Administrativo
- Incidente Operacional
- Atendimento Interno
- Ação Administrativa

---

## 16. Conceitos transversais do modelo

### 16.1 Propriedade do dado

Muitas entidades precisarão indicar quem é o titular principal do registro.

### 16.2 Visibilidade do dado

Muitas entidades precisarão suportar regras de visibilidade.

### 16.3 Contexto analítico

Individual, conjugal, familiar, dependente ou consolidado.

### 16.4 Temporalidade

Vários relacionamentos precisam suportar vigência temporal: membros do núcleo, regras de compartilhamento, classificações, responsabilidades, estruturas patrimoniais.

### 16.5 Moeda original e valor consolidado

Entidades financeiras e patrimoniais devem preservar moeda original e permitir valor consolidado ou convertido.

---

## 17. Relacionamentos centrais do sistema

- Usuário → Perfil, Preferências
- Usuário → Membro do Núcleo → Núcleo Familiar
- Núcleo Familiar → membros, dependentes, convites, regras de visibilidade
- Usuário e Núcleo → Conta, Cartão, Orçamento, Meta, Ativo, Tarefa, Assinatura
- Conta → Transação
- Cartão → Fatura, Transação
- Transação → Categoria, Subcategoria, Tag, Contexto Analítico, Centro de Responsabilidade, Documento
- Meta → Participante de Meta, Aporte de Meta
- Dívida → Parcela de Dívida
- Despesa Compartilhada → Rateio, Reembolso, Responsabilidade Econômica
- Projeto Familiar → Grupo de Tarefas, Tarefa, Template, Histórico
- Documento → Referência Documental com múltiplas entidades
- Assinatura → Plano, Elegibilidade Comercial
- Registro de Auditoria → qualquer entidade relevante

---

## 18. Critérios de qualidade deste modelo conceitual

Este modelo terá cumprido seu papel se permitir responder com clareza:

- quais são as entidades principais do sistema
- como os grandes domínios se organizam
- quais conceitos precisam existir antes da modelagem relacional
- como o sistema representa individual, casal e família
- como preservar contexto histórico e analítico
- como evitar simplificações perigosas no banco de dados

---

## 19. Limites deste documento

Este documento **não define ainda:**

- nomes finais de tabelas
- tipos físicos de colunas
- índices concretos
- estratégia exata de normalização
- detalhes de migrations
- payloads de API
- DTOs de aplicação

Esses elementos pertencem à modelagem lógica, física e ao desenho de implementação.

---

## 20. Conclusão

O modelo conceitual de dados do Balanzo estabelece a fundação semântica necessária para transformar a visão do produto em uma estrutura consistente de persistência e domínio.

Ele consolida a forma como o sistema entende identidade, família, finanças, patrimônio, colaboração, tarefas, monetização e administração.

---

## Próximo passo do projeto

O próximo artefato recomendado é:

**Estrutura de Repositório, Aplicações e Convenções do Projeto Balanzo**

Esse documento definirá como organizar o monorepo, os diretórios das aplicações, a separação entre web, admin, site, mobile, backend, infraestrutura, documentação e prompts para Cursor AI, além das convenções gerais de versionamento e organização técnica.
