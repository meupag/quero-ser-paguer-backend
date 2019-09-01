'use strict';

var SwaggerExpress = require('swagger-express-mw');
var app = require('express')();
const swaggerUi = require('swagger-ui-express');
const YAML = require('yamljs');

const swaggerDocument = YAML.load('./api/swagger/swagger.yaml');

module.exports = app; // for testing

var config = {
  appRoot: __dirname // required config
};

app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

SwaggerExpress.create(config, function(err, swaggerExpress) {
  if (err) { throw err; }
  swaggerExpress.register(app);

  var port = process.env.PORT || 10010;
  app.listen(port);

  if (swaggerExpress.runner.swagger.paths['/hello']) {
    console.log('try this:\ncurl http://127.0.0.1:' + port + '/hello?name=Scott');
  }
});
