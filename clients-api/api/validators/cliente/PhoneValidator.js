
function validacaoPhoneNumber(telephone) {
  if (telephone == null) return false;
  if (telephone.length < 14) return false;
  if (!telephone.includes('+')) return false;

  return true;
}


function isValid(payload) {
  if (!validacaoPhoneNumber(payload.phoneNumber)) { return { message: 'Telefone inválido', isValid: false, field: 'Telefone' }; }
  return { message: 'Ok', isValid: true, field: 'cpf' };
}

module.exports = isValid;
