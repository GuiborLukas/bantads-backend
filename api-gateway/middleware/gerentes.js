const httpProxy = require("express-http-proxy");

const consultaContasPendentesProxy = httpProxy(
  process.env.CONTAS_SERVICE_HOST,
  {
    proxyReqPathResolver: function (req) {
      let { id } = req.params;

      path = `?id_gerente=${id}&status=pendente`;
      console.log(path);
      return path;
    },
  }
);

module.exports = {
  consultaContasPendentesProxy,
};
