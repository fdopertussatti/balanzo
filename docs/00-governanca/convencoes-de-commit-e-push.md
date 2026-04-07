# Convenções de commit e push

**Versão:** 1.2  
**Última atualização:** 2026-04-06  
**Status:** Aprovado

---

## Documentação sempre atualizada

Após cada entrega ou mudança relevante, manter `docs/` alinhada ao estado do projeto. Em especial, atualizar `o-que-falta-implementar.md` (resumo executivo, status dos módulos, prioridade).

Regra persistente: `.cursor/rules/docs-sempre-atualizados.mdc`.

---

## Padrão: Conventional Commits

O projeto adota o [Conventional Commits](https://www.conventionalcommits.org/), amplamente usado no mercado.

### Formato

```
<tipo>(<escopo>): <descrição>

[corpo opcional]

[rodapé opcional]
```

### Tipos

| Tipo     | Uso                                      |
|----------|-------------------------------------------|
| `feat`   | Nova funcionalidade                       |
| `fix`    | Correção de bug                           |
| `docs`   | Apenas documentação (sem mudança de código) |
| `style`  | Formatação, espaços, ponto e vírgula      |
| `refactor` | Refatoração sem mudar comportamento    |
| `perf`   | Melhoria de performance                   |
| `test`   | Adição ou ajuste de testes                |
| `chore`  | Tarefas de manutenção (build, deps, config) |
| `ci`     | Mudanças em CI/CD                         |

### Escopo (opcional)

Indica a área afetada: `web`, `admin`, `site`, `mobile`, `backend`, `infra`, `docs`, etc.

### Regras

1. **Descrição:** modo imperativo, minúsculas, sem ponto final  
   - ✅ `feat(web): add login form`  
   - ❌ `feat(web): Added login form.`

2. **Limite de 72 caracteres** na primeira linha

3. **Corpo:** quando necessário, explicar o quê e por quê

4. **Breaking changes:** incluir no rodapé  
   ```
   feat(api): change auth response format

   BREAKING CHANGE: token now returned in 'accessToken' instead of 'token'
   ```

### Exemplos

```
feat(web): add transaction list component
fix(backend): prevent duplicate category creation
docs: update architecture diagram
refactor(admin): extract user table to shared component
chore(deps): bump next.js to 14.2
ci: add deploy workflow for staging
```

---

## Convenções de push

1. **Frequência:** prefira commits pequenos e frequentes; evite empilhar muitas mudanças.

2. **Antes do push:** garantir que o código compila e os testes passam.

3. **Branch `main`:** branch principal. Em equipe, usar fluxo com Pull Requests.

4. **Branches de feature:** `feat/nome-da-feature` ou `fix/nome-do-fix`.

5. **Mensagens de push:** não exigidas; o histórico é baseado nos commits.

---

## Atalho no Cursor: mensagem `1`

Quando o usuário enviar **somente** `1` (texto trimado, sem outro pedido na mesma mensagem), o assistente deve tratar como instrução para **criar commit e enviar para `origin`**, seguindo este documento e a regra `.cursor/rules/commit-push-atalho-1.mdc`.

### Passos

1. **`git status`** — ver o que mudou e em quais caminhos.
2. **Staging** — `git add` nos arquivos que pertencem à mesma entrega lógica. Se houver alterações **não relacionadas** no working tree, preferir **dois commits** separados ou pedir confirmação ao usuário antes de misturar tudo em um único commit.
3. **Mensagem** — Conventional Commits: primeira linha ≤72 caracteres; escopo quando fizer sentido; corpo opcional com detalhes. **Descrição preferencialmente em inglês** (alinhado a `convencoes-de-linguagem.md` e ao código).
4. **Qualidade antes do push** — quando fizer sentido para o que mudou, rodar build e/ou testes; não enviar alterações que claramente quebram o projeto.
5. **`git push`** — para o remoto configurado (em geral `origin`) na branch atual.

### Casos especiais

| Situação | Ação |
|----------|------|
| Working tree limpo | Informar que não há nada a commitar; não criar commit vazio. |
| Push rejeitado ou divergência | Reportar a saída do Git; próximos passos podem ser `git pull --rebase` (ou fluxo acordado pelo time) antes de novo push. |
| Entrega com docs | Incluir atualizações em `docs/` no mesmo commit ou em commit `docs:` logo em seguida, conforme `docs-sempre-atualizados.mdc`. |

### Boas práticas (engenharia de software)

- Commits **pequenos e coesos** (uma intenção por commit quando possível).
- Mensagem que responda **o quê** e, no corpo quando necessário, **por quê**.
- **Não** incluir segredos (`.env`, chaves); respeitar `.gitignore`.
- Em branches compartilhadas, alinhar com **PR/review** antes de push para `main`, se for política do time.
