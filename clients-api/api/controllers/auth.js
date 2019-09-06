const { AuthBusiness } = require('./../business');
const { ServerErrorHandler } = require('./../helpers');

function login(req, res) {
  const payload = req.swagger.params.auth.value;
  AuthBusiness.signIn(payload).then((result) => {
    if (result.statusCode) {
      res.status(result.statusCode);
      res.send(result.response);
    } else {
      res.status(202);
      res.send(result);
    }
  }).catch((err) => ServerErrorHandler(res, err));
}

module.exports = {
  login,
};
