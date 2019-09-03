const { CPFValidator } = require('./../validators');
const { ValidatorsRun } = require('./../helpers');
const { ClientBusiness } = require('./../business');
const { ServerErrorHandler } = require("./../helpers");

module.exports = {
    createClient: createClient
}
function createClient(req, res) {
    const payload = req.swagger.params.cliente.value;
    const validators = [CPFValidator];
    const errosRequest = ValidatorsRun(payload, validators);

    if (errosRequest.length > 0) {
        res.status(403).json(errosRequest);
    } else {
        ClientBusiness.CreateClient(payload).then(response => {
            res.status(200);
            res.json(response);
        }).catch(err => {
            ServerErrorHandler(res, err);
        });
    }

}
