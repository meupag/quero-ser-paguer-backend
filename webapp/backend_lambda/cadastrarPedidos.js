var crypto = require('crypto');

var AWS = require("aws-sdk");

AWS.config.update({ region: 'us-east-1' });

var dynamodb = new AWS.DynamoDB({ apiVersion: '2012-08-10' });

exports.handler = (event, context, callback) => {

    const requestBody = JSON.parse(event['body']);

    var itens = Object.values(requestBody.itens);

    var totalPedido = 0;

    return new Promise((resolve, reject) => {

        for (let i = 0; i < itens.length; i++) {

            totalPedido = + Number(itens[i].total);

            const itemPedido = {
                'id': { N: gerarId() },
                'id_pedido': { N: requestBody.id_pedido },
                'id_produto': { N: itens[i].id_produto },
                'quantidade': { N: itens[i].quantidade + '' },
                'preco': { N: itens[i].total + '' }
            }

            let paramsItem = {
                TableName: 'item_pedido',
                Item: itemPedido
            }

            dynamodb.putItem(paramsItem, function (err, data) {
                if (err)
                    console.log(err, err.stack);
            });
        }

        const pedido = {
            'id': { N: requestBody.id_pedido },
            'id_cliente': { S: requestBody.id_cliente },
            'valor': { S: totalPedido + '' }
        };

        const params = {
            TableName: 'pedido',
            Item: pedido
        };

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

function gerarId() {
    return Math.floor(Math.random() * 999) + "";
}