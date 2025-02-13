# PDV (Em desenvolvimento)

## Descrição

 Esse projeto consiste em um sistema de gerenciamento de pedidos e inventário para uma loja. Ele permite o cadastro de produtos, clientes, usuários, pedidos e pagamentos, além de gerar relatórios de faturamento e inventário.

## Funcionalidades

- Cadastro de produtos, clientes e usuários
- Gerenciamento de pedidos e pagamentos
- Geração de relatórios de faturamento e inventário
- Envio de emails de confirmação de pedidos e relatórios

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security**
- **Lombok**
- **Jakarta Persistence API**
- **JWT (JSON Web Token)**
- **H2 Database (para testes)**
- **MySQL (para produção)**
- **Maven**

## Estrutura do Projeto

- `src/main/java/com/agrestina/domain`: Contém as entidades do domínio.
- `src/main/java/com/agrestina/dto`: Contém os Data Transfer Objects (DTOs).
- `src/main/java/com/agrestina/repository`: Contém os repositórios JPA.
- `src/main/java/com/agrestina/service`: Contém os serviços de negócio.
- `src/main/java/com/agrestina/infra`: Contém configurações de segurança e CORS.
- `src/main/java/com/agrestina/mail`: Contém os serviços de envio de email.
- `src/main/java/com/agrestina/exeption`: Contém as classes de tratamento de exceções.

## Endpoints

### Autenticação

- **POST /auth/login**: Autentica um usuário e retorna um token JWT.
- **POST /auth/register**: Registra um novo usuário.

### Clientes

- **POST /clients/register**: Cadastra um novo cliente.
- **DELETE /clients/delete/{name}**: Desativa um cliente.
- **PATCH /clients/activate/{name}**: Ativa um cliente.
- **GET /clients/active**: Lista todos os clientes ativos.
- **GET /clients/inactive**: Lista todos os clientes inativos.
- **GET /clients/find/id/{id}**: Busca um cliente pelo ID.
- **GET /clients/find/name/{name}**: Busca um cliente pelo nome.
- **GET /clients/all**: Busca todos os clientes.

### Produtos

- **POST /products/register**: Cadastra um novo produto.
- **DELETE /products/delete/{id}**: Desativa um produto.
- **PUT /products/activate/{id}**: Ativa um produto.
- **GET /products/active**: Lista todos os produtos ativos.
- **GET /products/inactive**: Lista todos os produtos inativos.
- **GET /products/find/id/{id}**: Busca um produto pelo ID.
- **GET /products/name/{name}**: Busca um produto pelo nome.
- **GET /products/all**: Busca todos os produtos.

### Pedidos pendentes ou em andamento.

- **POST /orders/register**: Registra um novo pedido.
- **PUT /orders/finalize/{pendingOrderID}**: Finaliza um pedido pendente ou em andamento.
- **GET /orders/all/pending-orders**: Lista todos os pedidos em andamento ou pendentes.
- **GET /orders/id/{id}**: Busca um pedido pelo ID.
- **GET /orders/client/id/{clientId}**: Busca pedidos por ID do cliente.
- **GET /orders/client/name/{clientName}**: Busca pedidos por nome do cliente.
- **GET /orders/seller/id/{sellerId}**: Busca pedidos por ID do vendedor.
- **GET /orders/seller/name/{sellerName}**: Busca pedidos por nome do vendedor.
- **GET /orders/date/{date}**: Busca pedidos por data.

### Pedidos concluidos.

- **GET /finishedOrders/all**: Lista todos os pedidos concluidos.
- **GET /finishedOrders/id/{id}**: Busca um pedido concluido pelo ID.
- **GET /finishedOrders/client/id/{clientId}**: Busca pedidos concluidos por ID do cliente.
- **GET /finishedOrders/client/name/{clientName}**: Busca pedidos concluidos por nome do cliente.
- **GET /finishedOrders/seller/id/{sellerId}**: Busca pedidos concluidos por ID do vendedor.
- **GET /finishedOrders/seller/name/{sellerName}**: Busca pedidos concluidos por nome do vendedor.
- **GET /finishedOrders/date/{date}**: Busca pedidos concluidos por data.
  
### Pagamentos

- **POST /payments/order/{orderId}**: Processa um pagamento.
- **GET /payments/all**: Lista todos os pagamentos.
- **GET /payments/id/{id}**: Busca um pagamento pelo ID.
- **GET /payments/orderId/{orderId}**: Busca pagamentos por ID do pedido.
- **GET /payments/clientId/{clientId}**: Busca pagamentos por ID do cliente.
- **GET /payments/clientName/{clientName}**: Busca pagamentos por nome do cliente.
- **GET /payments/date/{date}**: Busca pagamentos por data.
- **GET /payments/method/{method}**: Busca pagamentos por método.

### Relatórios

- **GET /reports/inventory**: Gera um relatório de produtos faltando no inventário.
- **GET /reports/billing/category/{startDate}/{endDate}**: Gera um relatório de faturamento por categoria.
- **GET /reports/billing/products/{startDate}/{endDate}**: Gera um relatório de faturamento por produto.
- **GET /reports/email**: Envia os relatorios por email.

## Configuração

### Banco de Dados

- **H2 Database**: Configuração para testes.
- **MySQL**: Configuração para produção.

### Segurança

- **JWT (JSON Web Token)**: Utilizado para autenticação e autorização.
- **Spring Security**: Configuração de segurança.

### Envio de Emails

- **Spring Mail**: Utilizado para envio de emails de confirmação de pedidos e relatórios.

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/kaiqueromero/agrestina.git
