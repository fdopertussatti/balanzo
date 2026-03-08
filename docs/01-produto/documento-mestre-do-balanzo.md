# Documento Mestre do Projeto — Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento fundacional do sistema**

---

## 1. Introdução

O Balanzo é uma plataforma digital destinada à organização, acompanhamento e planejamento financeiro pessoal, conjugal e familiar.

O sistema foi concebido para permitir que indivíduos e famílias gerenciem suas finanças, patrimônio, metas e responsabilidades financeiras dentro de um único ambiente digital integrado.

Além da gestão financeira tradicional, o sistema também incorpora mecanismos de colaboração familiar, organização de responsabilidades domésticas, planejamento de atividades e coordenação entre membros de um núcleo familiar.

A plataforma é concebida desde o início como um produto SaaS (Software as a Service), com capacidade de atender usuários individuais, casais e famílias.

---

## 2. Objetivo do sistema

O objetivo do Balanzo é fornecer um ambiente unificado que permita:

- organizar a vida financeira
- acompanhar receitas e despesas
- controlar contas e cartões
- planejar orçamento e metas
- monitorar patrimônio
- gerenciar responsabilidades financeiras familiares
- coordenar tarefas e projetos domésticos
- facilitar a colaboração entre membros da família

O sistema deve permitir que usuários tenham clareza sobre sua situação financeira atual, sua evolução ao longo do tempo e suas perspectivas futuras.

---

## 3. Escopo do produto

O Balanzo não é apenas um sistema de controle de despesas.

Ele é uma **plataforma de organização financeira e familiar**.

O escopo do sistema inclui:

- gestão financeira pessoal
- gestão financeira conjugal
- gestão financeira familiar
- planejamento financeiro
- gestão patrimonial
- organização de atividades familiares
- coordenação de responsabilidades domésticas
- educação financeira de dependentes

O sistema deve permitir que informações privadas coexistam com informações compartilhadas dentro de um mesmo ambiente.

---

## 4. Público-alvo

O sistema foi projetado para atender diferentes perfis de usuários.

- **Usuários individuais** que desejam organizar sua vida financeira.
- **Casais** que precisam consolidar finanças individuais e compartilhadas.
- **Famílias** que desejam gerenciar despesas domésticas e planejamento financeiro conjunto.
- **Famílias com filhos** que desejam organizar responsabilidades e educação financeira.
- **Usuários interessados** em acompanhar patrimônio e planejamento de longo prazo.

---

## 5. Princípios fundamentais do sistema

O Balanzo foi concebido com base em alguns princípios fundamentais.

1. Centralização organizada das informações.
2. Privacidade e compartilhamento seletivo.
3. Clareza analítica da situação financeira.
4. Colaboração entre membros da família.
5. Planejamento financeiro estruturado.
6. Flexibilidade sem perda de organização.
7. Escalabilidade do produto como serviço SaaS.

---

## 6. Estrutura conceitual da plataforma

A plataforma Balanzo organiza suas funcionalidades em quatro grandes dimensões:

1. **Gestão financeira**
2. **Planejamento financeiro**
3. **Gestão patrimonial**
4. **Coordenação familiar**

Essas dimensões coexistem dentro de um mesmo ambiente, permitindo que o sistema represente de forma mais fiel a realidade financeira das pessoas.

---

## 7. Gestão financeira

O sistema permite registrar e organizar:

- contas bancárias
- cartões de crédito
- transações financeiras
- transferências internas
- categorias de despesas
- classificação de receitas e gastos

Os registros financeiros podem ser inseridos manualmente ou importados de extratos.

O sistema deve permitir categorização, análise histórica e consolidação financeira.

---

## 8. Planejamento financeiro

Além de registrar o passado financeiro, o sistema também permite planejar o futuro.

Entre os recursos de planejamento estão:

- orçamento financeiro
- metas financeiras
- provisões de despesas futuras
- planejamento de compras importantes
- acompanhamento de hábitos financeiros

O sistema deve permitir revisão periódica da situação financeira e acompanhamento da evolução do planejamento.

---

## 9. Gestão patrimonial

O Balanzo também permite acompanhar patrimônio e obrigações financeiras.

Isso inclui:

- ativos financeiros
- investimentos
- bens patrimoniais
- dívidas
- financiamentos
- patrimônio líquido consolidado

O sistema deve permitir análise patrimonial ao longo do tempo.

---

## 10. Estrutura familiar

Um dos diferenciais do sistema é a capacidade de representar estruturas familiares.

O sistema permite que múltiplos usuários participem de um mesmo núcleo familiar.

Cada participante pode possuir:

- dados privados
- dados compartilhados
- responsabilidades específicas

O sistema permite coexistência entre:

- finanças individuais
- finanças do casal
- finanças do núcleo familiar

---

## 11. Compartilhamento seletivo

O sistema distingue três conceitos importantes:

1. **Propriedade do dado**
2. **Visibilidade do dado**
3. **Participação do dado nas análises**

Isso permite que:

- contas individuais permaneçam privadas
- despesas familiares sejam compartilhadas
- análises financeiras sejam consolidadas

Esse modelo reflete melhor a realidade das famílias.

---

## 12. Dependentes e educação financeira

O sistema permite incluir dependentes dentro do núcleo familiar.

Dependentes podem:

- participar apenas como centro de despesas
- ter carteira supervisionada
- receber mesada controlada
- participar com acesso limitado ao sistema

Isso permite acompanhar gastos relacionados a filhos e também apoiar educação financeira.

---

## 13. Tarefas e projetos familiares

O sistema também inclui um módulo de organização familiar.

Esse módulo permite registrar:

- tarefas domésticas
- responsabilidades familiares
- atividades recorrentes
- projetos familiares
- planejamento de eventos

As tarefas podem ser atribuídas a membros da família e acompanhadas ao longo do tempo.

---

## 14. Templates de organização

O sistema pode oferecer modelos pré-definidos para tarefas e projetos.

Esses modelos ajudam os usuários a estruturar atividades comuns como:

- planejamento de viagens
- organização financeira mensal
- preparação de eventos familiares
- planejamento doméstico

Os usuários também podem criar seus próprios templates.

---

## 15. Gamificação e incentivos

O sistema pode incluir mecanismos simples de incentivo para participação em tarefas familiares.

Isso é especialmente útil para dependentes.

Tarefas podem gerar:

- pontuação
- recompensas
- metas de participação

Esse mecanismo também pode apoiar educação financeira.

---

## 16. Modelo SaaS

O Balanzo será disponibilizado como um serviço online.

O sistema poderá possuir diferentes planos de assinatura.

Os planos podem limitar ou liberar funcionalidades específicas.

A gestão de planos, limites e assinaturas será parte da arquitetura do sistema.

---

## 17. Arquitetura geral da plataforma

A arquitetura do sistema será composta por múltiplos componentes:

- Backend principal responsável pelas regras de negócio
- Interface web principal para usuários
- Interface administrativa para operação da plataforma
- Site institucional e comercial
- Aplicativo móvel
- Infraestrutura de banco de dados e serviços auxiliares

---

## 18. Aplicações da plataforma

A plataforma será composta por múltiplas aplicações:

- Frontend principal do usuário
- Frontend administrativo
- Site institucional
- Aplicativo mobile
- Backend principal
- Infraestrutura de dados

Essa separação permite melhor organização do sistema.

---

## 19. Diretrizes arquiteturais

A arquitetura do sistema seguirá alguns princípios técnicos:

- Separação clara de responsabilidades
- Backend centralizado em domínio de negócio
- Aplicações cliente desacopladas
- Uso de containers para execução
- Infraestrutura preparada para CI/CD
- Uso de banco de dados relacional robusto

---

## 20. Evolução do produto

O Balanzo foi concebido como um sistema evolutivo.

A primeira versão do sistema deverá concentrar-se nos recursos essenciais.

Com o tempo, novas funcionalidades podem ser incorporadas.

Entre as evoluções possíveis estão:

- integrações bancárias automáticas
- análises financeiras avançadas
- planejamento financeiro automatizado
- recursos colaborativos ampliados

---

## 21. Papel deste documento

Este documento define a visão fundamental do Balanzo.

Ele serve como referência para:

- arquitetura do sistema
- desenvolvimento do software
- organização da documentação
- planejamento do produto
- integração com ferramentas de desenvolvimento assistido por IA

Todos os demais documentos técnicos do projeto devem partir desta base.

---

## Próximo passo do projeto

Com a documentação base consolidada, o próximo passo é a implementação:

- **Bootstrap do backend:** `prompts/cursor/10-backend/00-inicializar-backend.md`
- **Análise de prontidão:** `docs/00-governanca/analise-de-prontidao-para-implementacao.md`
