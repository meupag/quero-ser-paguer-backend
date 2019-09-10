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
    }).catch((err) => ServerErrorHandler(res, err));
}

function getClientById(req, res) {
  const id = req.swagger.params.uuid.value;
  ClientBusiness.GetClientById(id)
    .then((client) => {
      if (client) {
        res.status(200);
        res.json(client);
      } else {
        res.status(204);
        res.json({});
      }
    })
    .catch((err) => ServerErrorHandler(res, err));
}

function listClients(req, res) {
  const limit = req.swagger.params.limit.value;
  const offset = req.swagger.params.offset.value;

  ClientBusiness.ListClients({ limit, offset })
    .then((result) => {
      res.status(200);
      res.json(result);
    })
    .catch((err) => ServerErrorHandler(res, err));
}

function deleteClient(req, res) {
  const id = req.swagger.params.uuid.value;
  ClientBusiness.DeleteClient({ idClient: id })
    .then(() => {
      res.status(200);
      res.send();
    })
    .catch((err) => ServerErrorHandler(res, err));
}

function updateClient(req, res) {
  const clientid = req.swagger.params.uuid.value;
  const payload = req.swagger.params.client.value;
  const validators = [PhoneValidator];
  ValidatorsRun(payload, validators)
    .then((errosRequest) => {
      if (errosRequest.length === 0) {
        ClientBusiness.UpdateClient(clientid, payload).then((response) => {
          res.status(200);
          res.send();
        }).catch((err) => {
          ServerErrorHandler(res, err);
        });
      } else {
        res.status(403).json(errosRequest);
      }
    }).catch((err) => ServerErrorHandler(res, err));
}
module.exports = {
  createClient,
  getClientById,
  listClients,
  deleteClient,
  updateClient,
};
