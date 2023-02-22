const httpProxy = require("express-http-proxy");

const autoCadastroServiceProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const alteracaoClienteServiceProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const remocaoGerenteServiceProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const insercaoGerenteServiceProxy = httpProxy(process.env.SAGA_SERVICE_HOST);

module.exports = {
  autoCadastroServiceProxy,
  alteracaoClienteServiceProxy,
  remocaoGerenteServiceProxy,
  insercaoGerenteServiceProxy,
};
