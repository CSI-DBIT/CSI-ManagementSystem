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
    user: 'root',
    password: 'oracle',
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
        res.send({
        "code":400,
        "failed":"error ocurred"
        });
	}
	
	else{
		if(results.length >0){
			//fetching from minute table
			connection.query('SELECT * FROM minute', function (error, results, fields) {
			res.send(results);
			if (error){
				res.send({
				"code":400,
				"failed":"error ocurred"
				});
			}
			});
		}
		else{
			res.send({
			"code":400,
			"failed":"ID does not exsit"
         });
		}
	}
    });
});


//port listening
app.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});