global.fetch = require('node-fetch');
const AmazonCognitoIdentity = require('amazon-cognito-identity-js');
const AWS = require('aws-sdk');

global.navigator = () => null;
const poolData = {
  UserPoolId: process.env.COGNITO_USER_POOL_ID,
  ClientId: process.env.COGNITO_CLIENT_ID,
};
AWS.config.update(
  {
    region: process.env.AWS_REGION,
    secretAccessKey: process.env.k3VbOevvdbXnXYmVGZnNf0D3pvNbRe0LJZ17OTQk,
    accessKeyId: process.env.AWS_ACCESS_ID,
  },
);

function register(client) {
  return new Promise((resolve, reject) => {
    const {
      email, nome, password, username, phoneNumber,
    } = client;
    const userToCreate = {
      ClientId: poolData.ClientId,
      Username: username,

      UserAttributes: [
        { Name: 'phone_number', Value: phoneNumber },
        { Name: 'email', Value: email },
        { Name: 'name', Value: nome },
      ],
      Password: password,
    };
    const cognitoidentityserviceprovider = new AWS.CognitoIdentityServiceProvider();
    cognitoidentityserviceprovider.signUp(userToCreate, (err, data) => {
      if (err)reject(err);
      resolve(data);
    });
  });
}

async function userExists(username) {
  return new Promise((resolve, reject) => {
    const params = {
      UserPoolId: poolData.UserPoolId,
      Username: username,
    };
    const cognitoidentityserviceprovider = new AWS.CognitoIdentityServiceProvider();
    cognitoidentityserviceprovider.adminGetUser(params, (err, data) => {
      if (err) {
        // eslint-disable-next-line no-unused-expressions
        err.code === 'UserNotFoundException' ? resolve(false) : reject(err);
      } else resolve(data);
    });
  });
}

async function signIn({ username, password }) {
  return new Promise((resolve, reject) => {
    const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
    const authenticationDetails = new AmazonCognitoIdentity.AuthenticationDetails({
      Username: username,
      Password: password,
    });
    const userData = {
      Username: username,
      Pool: userPool,
    };
    const cognitoUser = new AmazonCognitoIdentity.CognitoUser(userData);
    cognitoUser.authenticateUser(authenticationDetails, {
      onSuccess(token) {
        resolve(token);
      },
      onFailure(err) {
        reject(err.code);
      },
    });
  });
}

function deleteUser({ username }) {
  return new Promise((resolve, reject) => {
    const params = {
      UserPoolId: poolData.UserPoolId,
      Username: username,
    };
    const cognitoidentityserviceprovider = new AWS.CognitoIdentityServiceProvider();

    cognitoidentityserviceprovider.adminDeleteUser(params, (err, data) => {
      if (err) reject(err);
      else resolve(data);
    });
  });
}

function signUp(clientData) {
  return register(clientData);
}

module.exports = {
  signUp,
  userExists,
  signIn,
  deleteUser,
};
