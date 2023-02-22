# BANTADS BACKEND
## Execução
- Na raiz do projeto execute

`docker-compose -f .\.bantads_docker\docker-compose.yml up`

Isso vai instanciar todos os containers, tanto dos banco de dados, quanto dos serviços e do broker de mensagens.

## Diretórios
  - .bantads_docker
    - Dockerfile PostgreSQL
    - mongo-init.js, para instanciação do database de usuários
    - docker-compose.yml, para criaçãos dos containers
  - api-gateway
    - arquivos do API Gateway em node
  - auth-ws
  - cliente-ws
  - conta-ws
  - gerente-ws
    - MS em Spring Boot relacionados ao sistema BANTADS





