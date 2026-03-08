# Estrutura de Repositório, Aplicações e Convenções do Projeto Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Documento de organização do repositório e convenções do projeto**

---

## 1. Finalidade deste documento

Este documento define como o projeto Balanzo deve ser organizado no repositório principal.

Seu objetivo é estabelecer uma estrutura clara para aplicações, serviços, infraestrutura, documentação e materiais de apoio ao desenvolvimento assistido por IA, reduzindo risco de desorganização conforme o projeto evolui.

Este material servirá como referência para:

- organização do monorepo
- separação entre aplicações da plataforma
- distribuição de documentação e prompts
- padronização estrutural para Cursor AI
- governança da base de código
- clareza para manutenção futura

---

## 2. Diretriz estrutural geral

O repositório principal do Balanzo deve nascer como um **monorepo organizado por contexto**.

A estrutura não deve misturar aplicações de usuários, aplicações administrativas, site institucional, backend, infraestrutura e documentação no mesmo nível sem critério.

A organização deve refletir a arquitetura já definida para o produto.

---

## 3. Estrutura macro do repositório

```
balanzo/
├── README.md
├── LICENSE
├── .gitignore
├── .editorconfig
├── .gitattributes
├── docs/
├── prompts/
├── apps/
├── services/
├── packages/
├── infra/
├── tools/
└── tmp/
```

---

## 4. Diretório docs

### 4.1 Objetivo

O diretório `docs/` deve armazenar a documentação oficial e versionada do projeto.

### 4.2 Estrutura recomendada

```
docs/
├── 00-governanca/
├── 01-produto/
├── 02-arquitetura/
├── 03-frontend/
├── 04-mobile/
├── 05-backend/
├── 06-devops/
├── 07-decisoes/
└── 08-prompts-base/
```

### 4.3 Função de cada subdiretório

- **00-governanca/** — organização geral, estrutura do repositório, roadmap, convenções
- **01-produto/** — documento mestre, requisitos funcionais, visão, escopo, prioridades
- **02-arquitetura/** — arquitetura técnica, mapa de módulos, RNF, modelo de dados
- **03-frontend/** — arquitetura dos fronts, design system, convenções de UI
- **04-mobile/** — arquitetura e escopo do app Flutter
- **05-backend/** — convenções de API, jobs, contratos, aprofundamentos de domínio
- **06-devops/** — ambientes, containerização, pipelines, observabilidade
- **07-decisoes/** — ADRs
- **08-prompts-base/** — prompts-mestre para Cursor AI

---

## 5. Diretório prompts

### 5.1 Objetivo

O diretório `prompts/` deve armazenar materiais operacionais voltados ao uso prático com Cursor AI.

**Diferença:** `docs/` = documentação oficial; `prompts/` = instruções práticas para execução assistida.

### 5.2 Estrutura recomendada

```
prompts/
└── cursor/
    ├── 00-contexto-geral.md
    ├── 01-regras-de-implementacao.md
    ├── 02-convencoes-de-codigo.md
    ├── 10-backend/
    ├── 20-web/
    ├── 30-admin/
    ├── 40-site/
    ├── 50-mobile/
    └── 60-infra/
```

### 5.3 Papel dos arquivos principais

- **00-contexto-geral.md** — ponto de entrada do Cursor AI
- **01-regras-de-implementacao.md** — comportamento ao implementar código
- **02-convencoes-de-codigo.md** — nomenclatura, organização, padrões

---

## 6. Diretório apps

### 6.1 Objetivo

Conter as aplicações cliente da plataforma.

### 6.2 Estrutura

```
apps/
├── web/     # Front principal do usuário (Next.js)
├── admin/   # Front administrativo (Next.js)
├── site/    # Site institucional (Next.js)
└── mobile/  # App Flutter
```

### 6.3 Observação

As três aplicações web são distintas. Devem compartilhar identidade visual e convenções, mas não devem ser acopladas de forma excessiva.

---

## 7. Diretório services

### 7.1 Objetivo

Conter serviços de backend e lógica server-side.

### 7.2 Estrutura

```
services/
└── backend/   # Backend principal em Java com Spring
```

---

## 8. Diretório packages

### 8.1 Objetivo

Pacotes compartilhados entre aplicações, de forma disciplinada.

### 8.2 Estrutura

```
packages/
├── shared-types/
├── shared-ui/
├── shared-config/
└── shared-utils/
```

### 8.3 Regra de uso

Nada deve ir para `packages/` apenas porque "talvez um dia seja reutilizado". O compartilhamento deve existir por necessidade real.

---

## 9. Diretório infra

### 9.1 Objetivo

Infraestrutura, automação, ambientes e suporte operacional.

### 9.2 Estrutura

```
infra/
├── docker/
├── nginx/
├── github-actions/
├── supabase/
├── scripts/
└── environments/
```

---

## 10. Diretório tools

Conter ferramentas auxiliares: geradores, scaffolding, helpers.

```
tools/
├── generators/
└── helpers/
```

---

## 11. Diretório tmp

Área temporária local. Conteúdo em regra ignorado no controle de versão.

---

## 12. Arquivos de raiz

- **README.md** — apresentação, estrutura, aplicações, documentação, status, próximos passos
- **.gitignore** — Java, Node.js, Flutter, builds, ambientes, sensíveis
- **.editorconfig** — espaçamento, final de linha, charset
- **.gitattributes** — tratamento de texto e binários
- **LICENSE** — licença definida conscientemente

---

## 13. Convenções de nomenclatura

### 13.1 Diretórios

Minúsculas, hífen quando necessário. Evitar `misc`, `other`, `general`, `stuff`, `new`, `temp2`.

### 13.2 Documentação

Nomes descritivos, minúsculas, separados por hífen.

Exemplos: `documento-mestre-do-balanzo.md`, `mapa-de-modulos-do-backend.md`

### 13.3 Versões documentais

Versão no conteúdo do arquivo, não no nome:

```
Versão 1.0
Última atualização: YYYY-MM-DD
Status: Em elaboração ou Aprovado
```

---

## 14. Convenções de governança documental

- Toda definição importante deve ser consolidada em documentação versionada
- Mudanças relevantes devem atualizar os documentos correspondentes
- Documento Mestre tem papel fundacional
- Demais documentos expandem, detalham ou operacionalizam, sem contradizer

---

## 15. Convenções para uso com Cursor AI

- Operar com contexto orientado por documentos específicos
- Arquivo de entrada: `prompts/cursor/00-contexto-geral.md`
- Cada atividade deve informar quais documentos considerar
- Não criar estruturas arbitrárias em desacordo com a documentação

---

## 16. Convenções para aplicações web

- web, admin e site devem permanecer aplicações separadas
- Identidade visual comum, autonomia funcional
- Reutilização disciplinada de componentes

---

## 17. Convenções para o backend

- Backend em Spring é o centro das regras de negócio
- Organização por módulos conforme mapa definido
- Integrações (Supabase, Stripe) encapsuladas

---

## 18. Convenções para infraestrutura

- Tudo preparado para containerização
- Ambientes distintos: desenvolvimento, homologação, produção
- Configuração e segredos fora do código

---

## 19. Estrutura mínima inicial recomendada

```
balanzo/
├── README.md
├── .gitignore
├── .editorconfig
├── .gitattributes
├── docs/
│   ├── 00-governanca/
│   ├── 01-produto/
│   └── 02-arquitetura/
├── prompts/
│   └── cursor/
├── apps/
│   ├── web/
│   ├── admin/
│   ├── site/
│   └── mobile/
├── services/
│   └── backend/
├── infra/
│   ├── docker/
│   ├── supabase/
│   └── scripts/
└── packages/
```

---

## 20. Critérios de sucesso

Esta estrutura terá cumprido seu papel se permitir responder com clareza:

- onde cada tipo de artefato deve ficar
- onde está a documentação oficial
- onde estão os prompts do Cursor AI
- onde ficam as aplicações e o backend
- onde ficam os materiais de infraestrutura
- como evitar mistura de conceitos no repositório

---

## 21. Conclusão

A organização do repositório deve ser tratada como parte da arquitetura do produto.

Separar documentação, prompts, aplicações, backend, infraestrutura e pacotes compartilhados gera previsibilidade, clareza e capacidade de evolução.

---

## Próximo passo do projeto

O próximo artefato recomendado é:

**Prompt Mestre do Projeto para Cursor AI**

Esse documento servirá como ponto de entrada oficial para o Cursor AI, explicando o contexto do Balanzo, sua arquitetura, suas aplicações, suas regras de implementação e os documentos obrigatórios que devem ser considerados antes de gerar código.
