# Convenções de commit e push

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado

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
