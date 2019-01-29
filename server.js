var express = require('express');
var app = express();
var bodyParser = require('body-parser');

var login = require('./routes/login');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.use('/login',login);

//port activation
app.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});
