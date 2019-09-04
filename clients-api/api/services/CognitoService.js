global.fetch = require('node-fetch');
const AmazonCognitoIdentity = require('amazon-cognito-identity-js');

global.navigator = () => null;
// TODO: Passar para variaveis de ambiente
const poolData = {
  GroupName: 'common-client',
  UserPoolId: 'us-east-1_trDlK373M',
  ClientId: '1jreuo2lubpsk9su8dlerel4a',
};

function register(client) {
  const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
  const {
    email, name, password, username, phoneNumber,
  } = client;
  const attributeList = [];

  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'phone_number', Value: phoneNumber }));
  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'email', Value: email }));
  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'name', Value: name }));
  userPool.signUp(username, password, attributeList, null, (result) => {
    console.log(result);
  });
}

async function userExists(username) {
  const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
  const authenticationDetails = new AmazonCognitoIdentity.AuthenticationDetails({
    Username: username,
    Password: 'password',
  });
  const userData = {
    Username: username,
    Pool: userPool,
  };
  const cognitoUser = new AmazonCognitoIdentity.CognitoUser(userData);
  return cognitoUser.authenticateUser(authenticationDetails, {
    onSuccess() {
      return true;
    },
    onFailure(err) {
      return !(err.code === 'UserNotFoundException');
    },
  });
}

function signUp(clientData) {
  return register(clientData);
}
module.exports = {
  signUp,
  userExists,
};
