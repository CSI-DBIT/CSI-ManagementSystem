var express = require('express');
var router = express.Router();
var mysql = require('mysql');

// MySQL Connection
const connection = mysql.createConnection({
    host: 'localhost',
    user: process.env.database_user,
    password: process.env.database_password,
    database: 'CsiManagementSystem'
});

connection.connect(function(err) {
    if (!err) {
        console.log('Connected to MySql!\n');
    } else {
        console.log(err);
    }
});



router.post('/request',(req,res)=>{
	var id = req.body.id;
	var date = req.body.date;
	var s1 = req.body.s1;
	var s2 = req.body.s2;
	var s3 = req.body.s3;
	var s4 = req.body.s4;
	var s5 = req.body.s5;
	var s6 = req.body.s6;
	var s7 = req.body.s7;
	var reason = req.body.reason
	console.log(id);
	//fetching name from users table
	connection.query('SELECT name FROM profile WHERE profile.id=?',[id],function(error,name,fields){
	if (error){
		console.log(error);
		res.sendStatus(400);
	}
	else{
		//pushing into request table
		connection.query('INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?,?,?)',[Math.random().toString(36).replace('0.',''),id,name[0].name,date,s1,s2,s3,s4,s5,s6,s7,reason],function(err,results,fields){
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

//display all the requests:
router.post('/requestlist', (req, res) =>{
	connection.query('SELECT * FROM request', function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	{
    res.send(results).sendStatus(200);
	}
	});
});

//accept json array, move the record from request to final_list
router.post('/finallist', (req, res) =>{
  console.log(req.body.accepted[0])
	connection.query('INSERT INTO final_list SELECT id, stud_id, name, date, s1, s2, s3, s4, s5, s6, s7, reason FROM request WHERE id = ?',[req.body.accepted[0]], function (error, fields){
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	{
    res.sendStatus(200);
	}
	});
});

module.exports = router;
