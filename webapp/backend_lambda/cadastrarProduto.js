var crypto = require('crypto');

var AWS = require("aws-sdk");

AWS.config.update({ region: 'us-east-1' });

var dynamodb = new AWS.DynamoDB({ apiVersion: '2012-08-10' });

exports.handler = (event, context, callback) => {

    const requestBody = JSON.parse(event['body']);

    var item = {
        'id': { N: requestBody.id },
        'nome': { S: requestBody.nome },
        'preco_sugerido': { S: requestBody.preco_sugerido }
    };

    var params = {
        TableName: 'produto',
        Item: item
    };

    return new Promise((resolve, reject) => {

        dynamodb.putItem(params, function (err, data) {

            if (err)
                console.log(err, err.stack);
            else {
                var response = {
                    statusCode: 200,
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Headers': 'Content-Type',
                        'Access-Control-Allow-Methods': 'OPTIONS,POST,GET',
                        'Access-Control-Allow-Origin': '*'
                    },
                    body: JSON.stringify({ "message": "Success!" }),
                    isBase64Encoded: false
                };
                callback(null, response);
            }
        });

    });

};

function errorResponse(errorMessage, awsRequestId, callback) {
    callback(null, {
        statusCode: 500,
        body: JSON.stringify({
            Error: errorMessage,
            Reference: awsRequestId,
        }),
        headers: {
            'Access-Control-Allow-Origin': '*',
        },
    });
}