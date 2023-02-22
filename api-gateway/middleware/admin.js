const httpProxy = require("express-http-proxy");

const insereGerenteProxy = httpProxy(
  `${process.env.HOST}:${process.env.MANAGER_SERVICE_PORT}`,
  {}
);

const createUserProxy = httpProxy(
  `${process.env.HOST}:${process.env.AUTH_SERVICE_PORT}`
  // {
  //   proxyReqOptDecorator: userHeadersDecorator,
  // }
);

module.exports = createUserProxy;
