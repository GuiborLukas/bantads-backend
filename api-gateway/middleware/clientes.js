const httpProxy = require('express-http-proxy');

const relatorioClientesProxy = httpProxy(process.env.CLIENTE_SERVICE_HOST, {
  
});