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
        console.log(err);
    }
});



app.post('/createMinutes',(req,res)=>{
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
			var agenda= req.body.agenda;
			var points = req.body.points;	
			//fetching creator from users table
			connection.query('SELECT name FROM users WHERE users.id=?',[id],function(error,creator,fields){
			if (error)
			console.log(error);
			else{
				//pushing into minute table 
				connection.query('INSERT INTO minute VALUES(?,?,CURDATE(),CURTIME(),?,?)',[id,agenda,creator[0].name,points],function(err,results,fields){
				if (err)
				console.log(err);
				else{
					res.send({
					"code":"200",
					});
					console.log("Data Inserted");
				}
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


//Port Listening
app.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});
	