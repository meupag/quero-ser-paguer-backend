global.fetch = require('node-fetch');
const AmazonCognitoIdentity = require('amazon-cognito-identity-js');

global.navigator = () => null;
const poolData = {
  UserPoolId: process.env.COGNITO_USER_POOL_ID,
  ClientId: process.env.COGNITO_CLIENT_ID,
};

function register(client) {
  return new Promise((resolve, reject) => {
    const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
    const {
      email, name, password, username, phoneNumber,
    } = client;
    const attributeList = [];

    attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'phone_number', Value: phoneNumber }));
    attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'email', Value: email }));
    attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'name', Value: name }));
    userPool.signUp(username, password, attributeList, null, (result) => {
      if (result) reject(result);
      resolve();
    });
  });
}

async function userExists(username) {
  return new Promise((resolve) => {
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
    cognitoUser.authenticateUser(authenticationDetails, {
      onSuccess() {
        resolve(true);
      },
      onFailure(err) {
        resolve(!(err.code === 'UserNotFoundException'));
      },
    });
  });
}

function signUp(clientData) {
  return register(clientData);
}
module.exports = {
  signUp,
  userExists,
};
