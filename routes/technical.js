var express = require('express');
var router = express.Router();
var mysql = require('mysql');


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
       console.log('Connected to MySql!Technical.js');
    } else {
        console.log('Not Connected to MySql!Technical.js');
    }
});

// router.get('/viewListEvents', (req, res) =>{
// 	//fetching from events table
// 	connection.query('SELECT eid,name,p_date,status FROM events WHERE status IN (2,3)', function (error, results, fields) {
// 	if (error){
// 		console.log(error)
// 		res.sendStatus(400);
// 	}
// 	else
		
// 	res.send(results);
// 	});
// });

/*
Response -
[
    {
        "eid": "1QH7E",
        "name": "testing",
        "p_date": "2019-10-20T18:30:00.000Z",
        "status": 2
    },
    {
        "eid": "1WMpK",
        "name": "GOC",
        "p_date": "2019-10-20T18:30:00.000Z",
        "status": 2
    },
    {
        "eid": "Ejjw8",
        "name": "Joomla Workshop",
        "p_date": "2019-10-20T18:30:00.000Z",
        "status": 2
    },
    {
        "eid": "tyJQF",
        "name": "swat the bug",
        "p_date": "2020-03-01T18:30:00.000Z",
        "status": 2
    }
]
*//*var json=JSON.parse(results[0].others_budget)
			delete results[0]['others_budget']
			var keys = [];
			keys=Object.keys(json);
			for(var i=0;i<keys.length;i++)
			{
			results[0][keys[i]] = json[keys[i]];
			}
			console.log(results)*/
//Viewing Events
router.post('/viewEvents', (req, res) =>{
	var eid = req.body.eid;

	connection.query('SELECT e.name, e.theme, e.event_date, e.speaker, e.venue, e.reg_fee_c, e.reg_fee_nc, e.prize, convert(e.description using utf8)as description, t.qs_set, t.internet, t.comment, t.software_install FROM events e inner join technical t on e.eid=t.eid WHERE t.eid=?',[eid], function (error, results) {
		if (error){
			console.log("Failed To view Technical events");
			res.sendStatus(400);
		}
		else
		{	
			console.log("Sucessfully viewed Technical events");
			res.status(200).send(results[0]);	
		}
	});
});

/*
Req - 
{
    "eid":"1WMpK"
}

Res - 
[
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
        "qs_set": 1,
        "internet": 0,
        "software_install": 1
    }
]

*/
//Edit Technical
router.post('/editEvents',(req,res)=>{
	var eid = req.body.eid;
	var qs_set = req.body.qs_set;	
	var internet = req.body.internet;	
	var comment = req.body.comment;
	var software_install = req.body.software_install;	


	connection.query('UPDATE technical SET qs_set=?, internet=?, software_install=?,comment=?,status=3 WHERE eid=?',[qs_set, internet,software_install,comment,eid],function(err){
		if(err){
				console.log("Failed to update Technical Event ");			
				res.sendStatus(400);
			}
		else{
					console.log("Sucessfully updated technical event");
					res.sendStatus(200);
			}
		});
});
	
	
/*
{
        "eid":"1WMpK",
		"name": "GOC",
        "theme": "coding",
        "event_date": "2019-09-24T18:30:00.000Z",
        "speaker": "Venkat Raman",
        "venue": "IT LAB-2",
        "reg_fee_c": 50,
        "reg_fee_nc": null,
        "prize": 1200,
        "description": "Hello World",
        "qs_set": 1,
        "internet": 0,
        "software_install": 1
    }
*/


module.exports = router;

/*{
	"eid":"1",
	"name":"workshop",
	"theme":"python",
	"description":"hello, hii, blah",
	"fee":"450",
	"venue":"Kurla",
	"edate":"2019-06-09",
	"time":"12:00:00",
	"speaker":"Ms. Mushira Shaikh",
	"agenda":"csi",
	"date":"2019-02-28",
	"cb":"1000",
	"pb":"800",
	"gb":"900",
	"ob": {"sdf":"100"}
}
*/
