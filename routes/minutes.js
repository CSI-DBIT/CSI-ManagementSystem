var express = require('express');
var router = express.Router();
var mysql = require('mysql');


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
        //console.log('Connected to MySql!\n');
    } else {
        console.log(err);
    }
});



router.post('/create',(req,res)=>{
	var id = req.body.id;
	var agenda= req.body.agenda;
	var points = req.body.points;	
	console.log(id);
	//fetching creator from users table
	connection.query('SELECT name FROM profile WHERE profile.id=?',[id],function(error,creator,fields){
	if (error){
		console.log(error);
		res.sendStatus(400);
	}
	else{
		//pushing into minute table 
		connection.query('INSERT INTO minute VALUES(?,?,CURDATE(),CURTIME(),?,?)',[id,agenda,creator[0].name,points],function(err,results,fields){
		if (err){
			console.log(err);			
			res.sendStatus(400);
		}
		else{
				res.sendStatus(200);
				console.log("Data Inserted");
			}
		});
	}
	});
});

router.post('/list', (req, res) =>{
	var id = req.body.id;
	//fetching from minute table
	connection.query('SELECT id,agenda,da_te,ti_me,creator,CONVERT(minute USING utf8) as minute FROM minute', function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	res.send(results);
	});
});


module.exports = router;
