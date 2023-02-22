//importing modules
const express = require("express");
const { loginServiceProxy, logout } = require("../middleware/login");
const validaJWT = require("../middleware/auth");
const {
  autoCadastroServiceProxy,
  alteracaoClienteServiceProxy,
  remocaoGerenteServiceProxy,
  insercaoGerenteServiceProxy,
} = require("../middleware/saga");

const router = express.Router();

//Login - Proxy para Auth Service
router.post("/login", loginServiceProxy);

//Logout - Executa no Gateway
router.post("/logout", logout);

//=================
//Requisições que vão para o Orquestrador SAGA
//
//Auto-cadastro de clientes (POST - recurso clientes)
router.post("/clientes", validaJWT, autoCadastroServiceProxy);

//Alteração de clientes (PUT - recurso clientes)
router.post("/clientes/:id", validaJWT, alteracaoClienteServiceProxy);

//Remoção do gerente (DELETE - recurso gerentes)
router.delete("/gerentes/:id", validaJWT, remocaoGerenteServiceProxy);

//Inserção de gerente (POST - recurso gerentes)
router.post("/gerentes", validaJWT, insercaoGerenteServiceProxy);

//=================
//Requisições para Serviço Cliente
//
//
// router.get("/clientes/:id");

// //Inserção do gerente pelo Admin
// router.post("/gerente", validaJWT, insereGerenteProxy);

// //Remoção do gerente pelo Admin
// router.delete("/gerente/:id", validaJWT, removeGerenteProxy);

// //Listagem de Gerentes pelo Admin
// router.get("/gerente", validaJWT, listaTodosGerentesProxy);

// //Listagem de Gerentes Totalizados pelo Admin
// router.get("/gerente", validaJWT, listaGerentesAgrupadosProxy);

// //Alteração de Gerente pelo Admin
// router.put("/gerente/:id", validaJWT, atualizaGerenteProxy);

// //Listagem de todos os clientes pelo Admin
// router.get("/cliente", validaJWT, relatorioClientesAdminProxy);

module.exports = router;
