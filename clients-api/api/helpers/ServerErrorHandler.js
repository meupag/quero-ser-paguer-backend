module.exports = function (res, error) {
  res.status(500);
  const message = 'Unhandled request error';
  if (error.message) {
    res.json([{ message: error.message, type: 'REQUEST_ERROR' }]);
  } else if (process.env.DEBUG) {
    res.json([{ message: message.concat(`:${JSON.stringify(error)}`), type: 'UNHANDLED_REQUEST_ERROR' }]);
  } else {
    res.json([{ message, type: 'UNHANDLED_REQUEST_ERROR' }]);
  }
};
