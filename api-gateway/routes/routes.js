//importing modules
const express = require("express");
const { loginServiceProxy, logout } = require("../middleware/login");
const validaJWT = require("../middleware/auth");
const {
  autoCadastroSAGAProxy,
  alteracaoClienteSAGAProxy,
  remocaoGerenteSAGAProxy,
  insercaoGerenteSAGAProxy,
} = require("../middleware/saga");

const router = express.Router();

//Login - Proxy para Auth Service
router.post("/login", loginServiceProxy);

//Logout - Executa no Gateway
router.post("/logout", logout);


//=================
//Requisições de Cliente
//

//Auto-cadastro de clientes (POST - recurso clientes)
router.post("/clientes", validaJWT, autoCadastroSAGAProxy);

// - Get do cliente pelo ID
router.get("/clientes/:id", validaJWT, consultaClienteProxy);

//Alteração de clientes (PUT - recurso clientes)
router.put("/clientes/:id", validaJWT, alteracaoClienteSAGAProxy);

//Saldo (GET - conta)
router.get("contas/:id/saldo", validaJWT, consultaContaProxy);

//Deposito (POST - Contas Deposito - Alteração da conta)
router.post("/contas/:id/deposito", validaJWT, alteracaoContaProxy);

//Saque (POST - Contas Saque - Alteração da conta)
router.post("/contas/:id/saque", validaJWT, alteracaoContaProxy);

//Transferência  (POST - Contas Transferência - Alteração da conta)
router.post("/contas/:id/transferencia", validaJWT, transferenciaContaProxy);

//Extrato (GET - conta)
router.get("contas/:id/extrato", validaJWT, consultaExtratoProxy);

//=================
//Requisições do Gerente
//

//Contas pendentes (GET - conta)
router.get("gerentes/:id/contaspendentes", validaJWT, consultaContasPendentesProxy);

//Aprovar cliente
router.put("gerentes/:id/aprovarcliente", validaJWT, alteracaoClienteProxy);

//Rejeitar cliente
router.put("gerentes/:id/rejeitarcliente", validaJWT, alteracaoContaProxy);

// Consultar todos os clientes
router.get("/gerente/:id/todosmeusclientes", validaJWT, consultaClienteProxy);

// Consultar 5 melhores clientes todos os clientes
router.get("/gerente/:id/top5clientes", validaJWT, consultaClienteProxy);


//===============
// Requisições de Admin
//

//Inserção de gerente (POST - recurso gerentes)
router.post("/gerentes", validaJWT, insercaoGerenteSAGAProxy);

//Remoção do gerente (DELETE - recurso gerentes)
router.delete("/gerentes/:id", validaJWT, remocaoGerenteSAGAProxy);

//Listagem de Gerentes
router.get("/gerentes", validaJWT, listaGerentesProxy);

//Alteração de Gerente
router.put("/gerente/:id", validaJWT, atualizaGerenteProxy);

//Listagem de todos os clientes
router.get("/clientes", validaJWT, relatorioClientesAdminProxy);

module.exports = router;
