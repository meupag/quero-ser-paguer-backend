const { CPFValidator, PhoneValidator, CognitoUserExists } = require('./../validators');
const { ValidatorsRun } = require('./../helpers');
const { ClientBusiness } = require('./../business');
const { ServerErrorHandler } = require('./../helpers');

function createClient(req, res) {
  const payload = req.swagger.params.cliente.value;
  const validators = [CPFValidator, PhoneValidator, CognitoUserExists];
  ValidatorsRun(payload, validators)
    .then((errosRequest) => {
      if (errosRequest.length === 0) {
        ClientBusiness.CreateClient(payload).then((response) => {
          res.status(200);
          res.json(response);
        }).catch((err) => {
          ServerErrorHandler(res, err);
        });
      } else {
        res.status(403).json(errosRequest);
      }
    });
}


module.exports = {
  createClient,
};
