var express = require('express');
var router = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

// MySQL Connection
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'pmauser',//process.env.database_user,
    password: 'Mushira_99',//process.env.database_password,
    database: 'CsiManagementSystem'
});

connection.connect(function(err) {
    if (!err) {
        console.log('Connected to MySql!\n');
    } else {
        console.log(err);
    }
});

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({
    extended: true
}));



router.post('/attendance/request',(req,res)=>{
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
router.post('/attendance/requestlist', (req, res) =>{
	connection.query('SELECT* FROM request', function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	{
		res.sendStatus(200);
		res.send(results);
	}
	});
});

//accept json array, move the record from request to final_list
router.post('/attendance/finallist', (req, res) =>{
	connection.query('INSERT INTO final_list SELECT RID, stud_id, Name, date, s1, s2, s3, s4, s5, s6, s7 FROM request WHERE RID = ?)',[req.body.accepted[0]], function (error, fields){
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	{
		res.sendStatus(200);
		// display the accepted requests
		connection.query('SELECT* FROM request', function (error, results, fields) {
			if (error){
				console.log(error)
				res.sendStatus(400);
			}	
			else
			{
				res.sendStatus(200);
				res.send(results);
			}
		});
	}
	});
});


router.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});
//module.exports = router;
