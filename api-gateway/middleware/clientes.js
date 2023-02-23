
// Esse to mexendo ainda
const httpProxy = require("express-http-proxy");

const relatorioClientesProxy = httpProxy(process.env.CLIENTE_SERVICE_HOST, {
  userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
    const clientes = JSON.parse(proxyResData.toString("utf8"));

    httpProxy(process.env.GERENTE_SERVICE_HOST, {
      proxyReqOptDecorator: function (proxyReqOpts, originalReq) {
        return proxyReqOpts;
      },
      userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
        const gerentes = JSON.parse(proxyResData.toString("utf8"));

        const resultado = {
          clientes: clientes,
          gerentes: gerentes,
        };

        return JSON.stringify(resultado);
      },
    })(userReq, userRes);

    return proxyResData;
  },
});
