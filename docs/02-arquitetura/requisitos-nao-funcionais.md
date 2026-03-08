# Requisitos Não Funcionais do Balanzo

**Versão:** 1.0  
**Última atualização:** 2025-03-07  
**Status:** Aprovado  
**Diretrizes de qualidade, segurança e operação da plataforma**

---

## 1. Finalidade deste documento

Este documento define os requisitos não funcionais do sistema Balanzo.

Enquanto os requisitos funcionais descrevem o que o sistema faz, os requisitos não funcionais descrevem como o sistema deve se comportar em termos de qualidade, desempenho, segurança, confiabilidade e operação.

Este documento orienta: arquitetura técnica, infraestrutura, implementação do backend, frontend, operações e evolução futura.

---

## 2. Princípios fundamentais de qualidade

- segurança por padrão
- privacidade por padrão
- alta confiabilidade
- boa performance
- manutenibilidade elevada
- observabilidade clara
- capacidade de evolução sem ruptura

---

## 3. Segurança

A segurança é requisito central, pois o sistema manipula dados financeiros e informações pessoais sensíveis.

### 3.1 Autenticação

Baseada em Supabase Auth.

- suporte a tokens
- validação de tokens no backend
- expiração controlada de sessões
- suporte a múltiplos dispositivos

### 3.2 Autorização

Implementada no backend. Considerar: identidade, participação em família, papel, propriedade do dado, escopo de visibilidade.

Nenhuma operação sensível deve depender exclusivamente de validações no frontend.

### 3.3 Proteção de APIs

- validação de tokens
- controle de acesso baseado em domínio
- proteção contra acesso indevido a dados de terceiros

### 3.4 Proteção de dados sensíveis

- não registrar dados sensíveis em logs
- proteger endpoints de exportação
- limitar acesso administrativo

### 3.5 Proteção contra ataques comuns

Proteção contra: injeção de SQL, força bruta, acesso não autorizado a APIs, exposição acidental de dados.

---

## 4. Privacidade

O sistema deve permitir compartilhamento seletivo dentro da família.

Requisitos: controle granular de visibilidade; dados privados por padrão; compartilhamento explícito e reversível; capacidade de ocultar detalhes específicos.

---

## 5. Performance

### 5.1 Tempo de resposta

- operações simples: menos de 200 ms
- consultas moderadas: menos de 500 ms
- operações complexas: menos de 1 s

### 5.2 Eficiência de consultas

- uso adequado de índices
- evitar N+1
- paginação
- limitação de volume retornado

### 5.3 Processamento assíncrono

Para: notificações, relatórios, integrações externas, webhooks.

---

## 6. Escalabilidade

Estratégia inicial: monolito modular, escala vertical moderada, escala horizontal futura quando necessário.

---

## 7. Disponibilidade

- disponibilidade mínima: 99,5%
- manutenção controlada
- monitoramento de serviços
- recuperação rápida em falhas

---

## 8. Integridade de dados

- uso de transações no banco
- validação consistente de regras de domínio
- prevenção de inconsistências financeiras

---

## 9. Observabilidade

### 9.1 Logs

Registrar: eventos importantes, erros, operações críticas. Sem dados sensíveis.

### 9.2 Monitoramento

Status do sistema, uso de recursos, falhas, latência.

### 9.3 Métricas

Uso da plataforma, crescimento de usuários, desempenho.

---

## 10. Manutenibilidade

- arquitetura modular
- código organizado
- documentação consistente
- padrões claros de implementação

---

## 11. Testabilidade

- testes unitários
- testes de integração
- testes de domínio

Módulos críticos com cobertura adequada.

---

## 12. Containerização

- Dockerfile para backend
- build reproduzível
- ambientes consistentes
- execução local e deploy automatizado

---

## 13. CI/CD

- automatizar build
- executar testes automaticamente
- validar integridade do código
- facilitar deploy

---

## 14. Configuração por ambiente

Ambientes: desenvolvimento, teste, produção.

Configurações via variáveis de ambiente.

---

## 15. Armazenamento de arquivos

Supabase Storage.

Requisitos: controle de acesso, organização por contexto, proteção contra acesso não autorizado.

---

## 16. Integrações externas

Integrações (Stripe, e-mail, notificação) encapsuladas.

Falhas externas não devem comprometer a estabilidade do sistema.

---

## 17. Suporte a múltiplas moedas

- moeda em cada conta
- moeda em cada transação
- conversão em relatórios analíticos

---

## 18. Compliance e boas práticas

- proteção de dados pessoais
- controle de acesso
- auditoria de operações sensíveis

---

## 19. Evolução futura

Suporte a: integrações bancárias, análise financeira avançada, automação de categorização, inteligência financeira.

---

## 20. Critérios de qualidade

Uma implementação é adequada quando: respeita segurança, preserva privacidade, apresenta desempenho aceitável, mantém consistência do domínio, permite evolução futura.

---

## 21. Conclusão

Este documento define os requisitos não funcionais que garantem a qualidade do Balanzo como plataforma SaaS.

---

## Próximo passo

Com a documentação consolidada, o próximo passo é a implementação. Ver `docs/00-governanca/analise-de-prontidao-para-implementacao.md`.
