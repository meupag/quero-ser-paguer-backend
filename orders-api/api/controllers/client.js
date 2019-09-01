const { cliente } = require('../models');
const uuidv4 = require('uuid/v4');
module.exports = {
    createClient
}
function createClient(req, res) {
    res.status(200);
    res.send();
}