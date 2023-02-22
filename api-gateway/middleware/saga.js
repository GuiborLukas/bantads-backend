const httpProxy = require("express-http-proxy");

const autoCadastroSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST, {});
const alteracaoClienteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const remocaoGerenteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const insercaoGerenteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST);

module.exports = {
  autoCadastroSAGAProxy,
  insercaoGerenteSAGAProxy,
  remocaoGerenteSAGAProxy,
  alteracaoClienteSAGAProxy,
};
