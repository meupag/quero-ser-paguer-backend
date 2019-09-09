var AWS = require("aws-sdk");

AWS.config.update({ region: 'us-east-1' });

var dynamodb = new AWS.DynamoDB({ apiVersion: '2012-08-10' });

exports.handler = async (event, context, callback) => {

    return new Promise((resolve, reject) => {

        dynamodb.scan({ TableName: 'produto' }, function (err, data) {

            if (err) console.log(err, err.stack);
            else {
                let resp = [];
                let items = data['Items'];

                for (let i = 0; i < items.length; i++) {

                    let obj = Object.values(items[i]);
                    let produto = {};
                    for (let j = 0; j < obj.length; j++) {

                        if (j == 0)
                            produto['id'] = Object.values(obj[j])[0];
                        if (j == 1)
                            produto['preco_sugerido'] = Object.values(obj[j])[0];
                        if (j == 2)
                            produto['nome'] = Object.values(obj[j])[0];
                    }
                    resp.push(produto);
                    resp.sort((a, b) => (a.id > b.id) ? 1 : -1);
                }
                var response = {
                    statusCode: 200,
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Headers': 'Content-Type',
                        'Access-Control-Allow-Methods': 'OPTIONS,POST,GET',
                        'Access-Control-Allow-Origin': '*'
                    },
                    body: JSON.stringify(resp ? resp : ""),
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
            'Content-Type': 'application/json',
            "Access-Control-Allow-Headers": 'Content-Type,X-Amz-Date,Authorization,X-Api-Key,x-requested-with',
            'Access-Control-Allow-Methods': 'GET,POST,OPTIONS',
            'Access-Control-Allow-Origin': '*',
        },
    });
}