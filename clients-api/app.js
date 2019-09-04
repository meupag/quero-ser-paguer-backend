
const SwaggerExpress = require('swagger-express-mw');
const app = require('express')();
const swaggerUi = require('swagger-ui-express');
const YAML = require('yamljs');
const { ServerErrorHandler } = require('./api/helpers');

const swaggerDocument = YAML.load('./api/swagger/swagger.yaml');

module.exports = app; // for testing

const config = {
  appRoot: __dirname, // required config
};

function errorHandler(err, req, res, next) {
  const { results } = err;
  if (!results || results === undefined) {
    ServerErrorHandler(err);
  }
  const errorList = results.errors.map((item) => ({
    message: item.message,
    fields: item.path,
    type: item.code,
  }));
  res.status(403).json(errorList);
}

app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

SwaggerExpress.create(config, (err, swaggerExpress) => {
  if (err) { throw err; }
  swaggerExpress.register(app);

  const port = process.env.PORT || 10010;
  app.listen(port);
  app.use(errorHandler);

  if (swaggerExpress.runner.swagger.paths['/hello']) {
    console.log(`try this:\ncurl http://127.0.0.1:${port}/hello?name=Scott`);
  }
});
