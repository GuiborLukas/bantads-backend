
// Esse to mexendo ainda
const httpProxy = require("express-http-proxy");

const relatorioClientesProxy = httpProxy(process.env.CLIENTE_SERVICE_HOST, {
  userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
    // modify the response data before sending it back to the client
    const clientesData = JSON.parse(proxyResData.toString("utf8"));

    // make a call to the managers service to get the managers data
    httpProxy(process.env.GERENTE_SERVICE_HOST, {
      proxyReqOptDecorator: function (proxyReqOpts, originalReq) {
        // add any additional headers or options to the request
        return proxyReqOpts;
      },
      userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
        // modify the response data before sending it back to the client
        const managersData = JSON.parse(proxyResData.toString("utf8"));

        // combine the clients and managers data and return it to the client
        const combinedData = {
          clients: clientsData,
          managers: managersData,
        };

        return JSON.stringify(combinedData);
      },
    })(userReq, userRes);

    return proxyResData;
  },
});
