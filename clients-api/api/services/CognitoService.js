global.fetch = require('node-fetch');
const AmazonCognitoIdentity = require('amazon-cognito-identity-js');

global.navigator = () => null;

function register(client) {
  const userPool = new AmazonCognitoIdentity.CognitoUserPool(poolData);
  const { email, name, password } = client;
  const attributeList = [];

  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'phone_number', Value: '+5511975919064' }));
  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'email', Value: email }));
  attributeList.push(new AmazonCognitoIdentity.CognitoUserAttribute({ Name: 'name', Value: name }));
  userPool.signUp(email, password, attributeList, null, (result, err) => {
    console.log(result);
  });
}

function signUp(clientData) {
  return register(clientData);
}
module.exports = {
  signUp,
};
