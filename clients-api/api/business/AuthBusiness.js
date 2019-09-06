const { CognitoService } = require('../services');

async function signIn(payload) {
  try {
    const token = await CognitoService.signIn(payload);
    return token;
  } catch (err) {
    let mensagemDeRetorno = 'Não foi possivel realizar o Login';
    if (err === 'UserNotConfirmedException') {
      mensagemDeRetorno = 'Efetue a verificação do email';
    } else if (err === 'PasswordResetRequiredException') {
      mensagemDeRetorno = 'Seu password foi expirado';
    } else if (err === 'NotAuthorizedException') {
      mensagemDeRetorno = 'Senha incorreta';
    } else if (err === 'UserNotFoundException') {
      mensagemDeRetorno = 'Usuário não encontrado';
    }
    return {
      statusCode: 400,
      response: [
        {
          message: mensagemDeRetorno,
          type: 'BUSINESS_VALIDATION',
        },
      ],
    };
  }
}

module.exports = {
  signIn,
};
