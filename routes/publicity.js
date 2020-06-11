var express = require('express');
var router = express.Router();
//<<<<<<< HEAD
var mysql = require('mysql');
var multer = require('multer');

// MySQL Connection
var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
	user: process.env.user,
	password: process.env.password,
    database: 'csiApp'
});
connection.connect(function(err) {
    if (!err) {
       console.log('Connected to MySql! publicty.js\n');
    } else {
        console.log(err);
    }
}); 
//=======

// MySQL Connection
var mysql=require('mysql');
var connection=mysql.createConnection({
	host:'localhost',
	user: process.env.user,
	password: process.env.password,
	database:'csiApp'
});
connection.connect(function(err){
	if(err){
		console.log('Not Connected to MySql!');
	}
	else{
		console.log("Connected To Mysql!");
	}
});
//>>>>>>> 21b52b3e6882614b8dc2990b4cdd0cee38c368cb

router.get('/listEvent',(req,res)=>{
	connection.query('SELECT name,eid FROM events WHERE status=2', function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
		res.status(200).send(results);
	});
});

router.post('/viewEvent',(req,res)=>{
	var eid=req.body.eid;

	connection.query('SELECT e.name, e.theme, e.event_date, e.speaker, e.venue, e.reg_fee_c, e.reg_fee_nc, e.prize, convert(e.description using utf8)as description, e.creative_budget, e.publicity_budget, e.guest_budget, p.desk, p.in_class, p.target, convert(p.comment using utf8)as comment, p.collected, p.spent FROM events e inner join publicity p on e.eid=p.eid WHERE p.eid=?',[eid],function(err,result){
		if(err){
			console.log(err);
			res.sendStatus(400);
		}
		else{
			console.log("Succesfully Listed");
			res.status(200).send(result[0]);
		}
	});
});
/*{
	"eid":"1WMpK"
}*/

/*res
{
    "name": "GOC",
    "theme": "coding",
    "event_date": "2019-09-24T18:30:00.000Z",
    "speaker": "Venkat Raman",
    "venue": "IT LAB-2",
    "reg_fee_c": 50,
    "reg_fee_nc": null,
    "prize": 1200,
    "description": "Hello World",
    "creative_budget": 50,
    "publicity_budget": 50,
    "guest_budget": 25,
    "desk": 1,
    "in_class": 0,
    "target": "50",
    "comment": "hello",
    "collected": 100,
    "spent": 100
}*/

router.post('/editPublicity',(req,res)=>{
	var collected=req.body.collected;
	var desk=req.body.desk;
	var in_class=req.body.in_class;
	var target=req.body.target;
	var comment=req.body.comment;
	var eid=req.body.eid;
	var spent=req.body.spent;

	//pushing into publicty table 
	connection.query('UPDATE publicity SET desk=?, in_class=?,target=?,comment=?, collected =?,spent =? WHERE eid=?',[desk,in_class,target,comment,collected,spent,eid],function(error,fields){
	if (error){
			console.log("Error");
			res.sendStatus(400);
		}
	else
	{
		console.log("Updated!");
		res.sendStatus(200);
	}
	});
});
/*{
"eid":"1WMpK",
"desk":"1",
"in_class":"0", 
"target":"30", 
"comment":"Need more details",
"collected":"200",
"spent":"100"
}*/
/*
router.post('/req',(req,res)=>{
	var eid=req.body.eid;
	var desk=req.body.desk;
	var in_class=req.body.in_class;
	var target=req.body.target;
	var comment=req.body.comment;
	var collected=req.body.collected;
	var spent=req.body.spent;

	//pushing into publicty table 
	connection.query('INSERT INTO publicity(eid,desk,in_class,target,comment,collected,spent) VALUES(?,?,?,?,?,?,?)',[eid,desk,in_class,target,comment],function(error,results,fields){
		if(error){	
			console.log(error);		
			res.sendStatus(400);
		}
		else{
			res.sendStatus(200);
		}
	});
});*/

/*{
	"eid":"TYgBt","desk":"1","in_class":"0", "target":"30", "comment":"Need more details"
}*/


// router.post('/addMember',(req,res)=>{
// 	var name = req.body.name;
// 	var eid = req.body.eid;
// 	var id = req.body.id;
// 	var work = req.body.work;
// 	connection.query('INSERT INTO Members values(?,?,?,?)',[name, id, work, eid],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		console.log("Member Added")
// 		res.sendStatus(200)
// 	}
// 	});
// 	});	
/*{
	"id":"2017134980","name":"Mushira","work":"Install python, npm etc in the system", "eid":"6"

}*/

// router.post('/viewMember',(req,res)=>{
// 	var id = req.body.id;
// connection.query('SELECT name, id, CONVERT(work USING utf8) as work FROM Members WHERE id=?',[id],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		res.status(200).send(results);
// 	}
// 	});
// 	});

// /*{
// 	"id":"2017134980"

// }*/

// router.post('/deleteMember',(req,res)=>{
// 	//var id = req.body.id;
// 	for (var i = 0; i < req.body.members.length; i++)
// {
// 	var id = req.body.members[i]
// 		connection.query('delete FROM Members WHERE id=?',[id],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		console.log("Member deleted")
// 	}
// 	});
// }
// res.sendStatus(200)
// });

// /*{
// 	"members":[
//       "2017134980",
//       "2017134956"
//    ]
// }*/
		
// router.post('/editMember',(req,res)=>{
// 	var name = req.body.name;
// 	var id = req.body.id;
// 	var work = req.body.work;
// 	connection.query('UPDATE Members set name=?, work=? WHERE id=?',[name, work, id],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		console.log("Member Edited!")
// 		res.sendStatus(200)
// 	}
// 	});
// 	});

// {
// 	"id":"2017134980","name":"Mushira","work":"Install python, npm etc in the system"

// }

// router.get('/ListMembers', (req, res) =>{
// 	//fetching from events table
// 	connection.query('SELECT id, name, CONVERT(work USING utf8) as work FROM Members', function (error, results, fields) {
// 	if (error){
// 		console.log(error)
// 		res.sendStatus(400);
// 	}
// 	else
// 		res.status(200).send(results);	
// 	});
// });

// router.post('/insertBudget', (req, res) =>{
// 	var eid = req.body.eid;
// 	var collected = req.body.collected;
// 	var refunded = req.body.refunded;
// 	var spent = req.body.spent;
// 	var balance = req.body.balance;
// 	connection.query('INSERT INTO budget values(?,?,?,?,?)', [eid, collected, refunded, spent, balance], function (error, results, fields) {
// 	if (error){
// 		console.log(error)
// 		res.sendStatus(400);
// 	}
// 	else
// 	{
// 		console.log("Budget Inserted")
// 		res.sendStatus(200);	
// 	}
// 	});
// });
// /*{
// 	"eid":"6","collected":"2000","refunded":"500", "spent":"800", "balance":"700"
// }*/


// router.post('/viewBudget',(req,res)=>{
// 	var eid = req.body.eid;
// connection.query('SELECT * FROM budget WHERE eid=?',[eid],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		res.status(200).send(results);
// 	}
// 	});
// 	});
// /*{
// 	"eid":"2017134980"

// }*/


// //will appear along with pr details
// router.post('/Venue',(req,res)=>{
// 	var eid = req.body.eid;
// 	var v_ready=req.body.v_ready
// 	var v_comm=req.body.v_comm
// 	//venue_ready will hve chckbox
// connection.query('UPDATE events set venue_status=?, venue_comment=? WHERE eid=?',[eid],function(error,results,fields){
// 	if (error){
// 		console.log(error);
// 		res.sendStatus(400);
// 	}
// 	else{
// 		res.sendStatus(200);
// 	}
// 	});
// 	});
// /*{
// 	"eid":"6","v_ready":"0","v_comm":"Needs chairs"
// }*/

module.exports = router;
