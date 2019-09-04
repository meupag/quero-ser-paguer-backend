module.exports = async function (payload, listValidators) {
  const errorList = [];
  // eslint-disable-next-line no-restricted-syntax
  for (const validator of listValidators) {
    // eslint-disable-next-line no-await-in-loop
    const validation = await validator(payload);
    if (!validation.isValid) {
      errorList.push({ message: validation.message, field: [validation.field], type: 'BUSINESS_VALIDATION' });
    }
  }
  return errorList;
};
