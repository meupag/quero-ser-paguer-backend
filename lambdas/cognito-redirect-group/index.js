exports.handler = async function afterConfirmationTrigger(event, context, callback) {
  const AWS = require('aws-sdk');
  const cognitoISP = new AWS.CognitoIdentityServiceProvider({
    apiVersion: '2019-09-04'
  });

  const params = {
    GroupName: 'common-client',
    UserPoolId: event.userPoolId,
    Username: event.userName
  };

  try {
    await cognitoISP.adminAddUserToGroup(params).promise();
} catch (error) {
}
  console.log("Executed.");

  context.succeed(event);
};
