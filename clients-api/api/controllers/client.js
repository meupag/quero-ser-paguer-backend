const { CPFValidator, PhoneValidator, CognitoUserExists } = require('./../validators');
const { ValidatorsRun } = require('./../helpers');
const { ClientBusiness } = require('./../business');
const { ServerErrorHandler } = require('./../helpers');

function createClient(req, res) {
  const payload = req.swagger.params.cliente.value;
  const validators = [CPFValidator, PhoneValidator, CognitoUserExists];
  const errosRequest = ValidatorsRun(payload, validators);

  if (errosRequest.length > 0) {
    res.status(403).json(errosRequest);
  } else {
    ClientBusiness.CreateClient(payload).then((response) => {
      res.status(200);
      res.json(response);
    }).catch((err) => {
      ServerErrorHandler(res, err);
    });
  }
}


module.exports = {
  createClient,
};
