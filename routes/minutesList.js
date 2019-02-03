var express = require('express');
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));


// MySQL Connection
var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: process.env.database_user,
    password: process.env.database_password,
    database: 'CsiManagementSystem'
});
connection.connect(function(err) {
    if (!err) {
        console.log('Connected to MySql!\n');
    } else {
        console.log('Not connected to MySql.\n');
    }
});



app.post('/minutesList', (req, res) =>{
	var id = req.body.id;
	//checking users exists?
	connection.query('SELECT * FROM users WHERE id = ?',[id], function (error, results, fields){
    if  (error){
        res.sendStatus(404);
	}
	
	else{
		if(results.length >0){
			//fetching from minute table
			connection.query('SELECT * FROM minute', function (error, results, fields) {
			res.send(results);
			if (error){
				res.sendStatus(400);
			}
			});
		}
		else{
			res.sendStatus(400);
		}
	}
    });
});


//port listenings
app.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});
