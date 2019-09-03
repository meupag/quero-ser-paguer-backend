module.exports = function (payload, listValidators) {
  const errorList = [];
  // eslint-disable-next-line no-restricted-syntax
  for (const validator of listValidators) {
    const validation = validator(payload);
    if (!validation.isValid) {
      errorList.push({ message: validation.message, field: [validation.field], type: 'BUSINESS_VALIDATION' });
    }
  }
  return errorList;
};
