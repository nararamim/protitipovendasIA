# Feature: adicionar estado civil à entidade cliente

## Objetivo
Adicionar o atributo **estado civil** à entidade de cliente para permitir o registro, persistência, consulta e atualização dessa informação de forma padronizada em todo o sistema.

## Contexto
Atualmente a entidade `Customer` contempla identificação, dados pessoais básicos, documento e endereço. A ausência de estado civil limita regras futuras de negócio, segmentação de clientes e completude cadastral.

A feature deve manter compatibilidade com o padrão arquitetural existente (controller > service > domain > repository) e com as validações já adotadas para entrada de dados.

## Regras de negócio
1. O cliente passa a possuir o campo obrigatório `estadoCivil`.
2. O valor deve ser representado por um conjunto fechado de opções.
3. Valores aceitos:
   - `SOLTEIRO`
   - `CASADO`
   - `DIVORCIADO`
   - `VIUVO`
   - `UNIAO_ESTAVEL`
4. Não devem ser aceitos valores nulos, em branco ou fora da lista permitida.
5. A regra deve valer para criação e atualização de cliente.
6. O retorno da API deve expor o estado civil no payload de consulta.

## Requisitos funcionais
1. Incluir `estadoCivil` no domínio de cliente.
2. Incluir `estadoCivil` nos contratos de entrada (request DTO) e saída (response DTO) da API de clientes.
3. Validar `estadoCivil` na camada de entrada e na camada de domínio.
4. Persistir `estadoCivil` no banco de dados.
5. Permitir filtro por estado civil na consulta de clientes (opcional na API de listagem).
6. Atualizar documentação de API (quando existir) com o novo campo e valores permitidos.

## Requisitos não funcionais
1. Manter compatibilidade com os padrões atuais de validação e tratamento de erro da aplicação.
2. Preservar simplicidade de implementação (sem overengineering).
3. Garantir cobertura de testes unitários para as novas regras de estado civil.
4. Não degradar os tempos atuais de resposta de forma perceptível para operações de CRUD.

## Critérios de aceitação
1. Ao criar cliente com `estadoCivil` válido, o cadastro é concluído com sucesso.
2. Ao criar cliente sem `estadoCivil`, a API retorna erro de validação com mensagem clara.
3. Ao criar/atualizar cliente com `estadoCivil` inválido, a API retorna erro de validação (4xx).
4. Ao consultar um cliente existente, o campo `estadoCivil` é retornado corretamente.
5. Ao atualizar `estadoCivil` com valor válido, o novo valor é persistido e retornado na consulta.
6. Testes unitários cobrem cenários válidos e inválidos para domínio, service e controller.
7. Testes de repositório validam persistência e leitura do campo `estadoCivil`.

## Casos de erro
1. `estadoCivil` ausente no payload de criação.
2. `estadoCivil` com string vazia ou somente espaços.
3. `estadoCivil` com valor não previsto na enumeração.
4. `estadoCivil` informado com variação de formato não suportada (ex.: valor livre).
5. Tentativa de atualização de cliente inexistente (retornar 404 conforme padrão já definido).

## Fora de escopo
1. Alterar regras de CPF/CNPJ.
2. Alterar regras de endereço.
3. Criar novos fluxos além de CRUD de clientes.
4. Implementar migrações de dados legados fora da inclusão do novo campo.
