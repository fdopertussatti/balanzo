# ADR-004: Estratégia de autenticação

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Consolidado

---

## Decisão

- **Autenticação:** Supabase Auth (tokens JWT)
- **Autorização:** backend Spring (domínio)
- **Separação:** autenticação confirma identidade; autorização decide acesso

## Fontes canônicas

- `docs/02-arquitetura/requisitos-nao-funcionais.md` (seção 3)
- `docs/02-arquitetura/estrategia-de-autenticacao-e-autorizacao.md` (autorização familiar pendente de detalhamento)
