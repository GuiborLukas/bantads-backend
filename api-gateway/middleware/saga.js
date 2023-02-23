const httpProxy = require("express-http-proxy");

const autoCadastroSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST, {});
const alteracaoClienteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
const remocaoGerenteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST);
// const insercaoGerenteSAGAProxy = httpProxy(process.env.SAGA_SERVICE_HOST, {
const insercaoGerenteSAGAProxy = httpProxy(process.env.GERENTE_SERVICE_HOST, {
  // proxyReqPathResolver: function (req) {
  //   let path = req.url;
  //   console.log(`Antigo path: ${path}`);
  //   path = path.replace("/gerentes", "/incluirgerente");
  //   console.log(`Novo path: ${path}`);
  //   return path;
  // },
  userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
    // Busca o usuario criado para o gerente para retornar tudo junto
    console.log("Aqui dentro - " + proxyRes.statusCode);
    if (proxyRes.statusCode == 201) {
      console.log("Aqui dentro 2");
      var gerente = JSON.parse(proxyResData.toString("utf8"));
      const { email, nome, cpf, telefone } = gerente;
      const senha = "123456";
      httpProxy(`${process.env.AUTH_SERVICE_HOST}/usuario`, {
        proxyReqBodyDecorator: function (bodyContent, srcReq) {
          try {
            bodyContent = { nome, email, senha, perfil: "GERENTE" };
          } catch (e) {
            console.log(`ERRO! ${e}`);
          }
          return bodyContent;
        },
        proxyReqOptDecorator: function (proxyReqOpts, srcReq) {
          proxyReqOpts.method = "POST";
          proxyReqOpts.headers["Content-Type"] = "application/json";
          return proxyReqOpts;
        },
        userResDecorator: function (proxyRes, proxyResData, userReq, userRes) {
          try {
            const usuario = JSON.parse(proxyResData.toString("utf8"));

            const gerenteDTO = { email, nome, cpf, telefone, usuario };
            return JSON.stringify(gerenteDTO);
          } catch (e) {
            console.log(e);
            return userRes.status(500).json("ERRAO");
          }
        },
      })(userReq, userRes);

    }
    console.log(proxyResData);
    return proxyResData;
  },
});

module.exports = {
  autoCadastroSAGAProxy,
  insercaoGerenteSAGAProxy,
  remocaoGerenteSAGAProxy,
  alteracaoClienteSAGAProxy,
};
