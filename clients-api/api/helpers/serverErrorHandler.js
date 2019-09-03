module.exports = function(res, error) {
    res.status(500);
    let message = "Unhandled request error";
    if(process.env.DEBUG){
        res.json({message: message.concat(":" + JSON.stringify(error))});
    }

    res.json({message: message});
}