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
       console.log('Connected to MySql!Technical.js\n');
    } else {
        console.log(err);
    }
});

router.get('/viewListEvents', (req, res) =>{
	//fetching from events table
	connection.query('SELECT eid,name,p_date,status FROM events WHERE status IN (2,3)', function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
		
	res.send(results);
	});
});

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
*/

router.post('/viewEvents', (req, res) =>{
	var eid = req.body.eid;
	//fetching from events table
	connection.query('SELECT e.name, e.theme, e.event_date, e.speaker, e.venue, e.reg_fee_c, e.reg_fee_nc, e.prize, convert(e.description using utf8)as description, t.qs_set, t.internet, t.comment, t.software_install FROM events e inner join technical t on e.eid=t.eid WHERE t.eid=?',[eid], function (error, results, fields) {
	if (error){
		console.log(error)
		res.sendStatus(400);
	}
	else
	{	
		/*var json=JSON.parse(results[0].others_budget)
		delete results[0]['others_budget']
		var keys = [];
		keys=Object.keys(json);
		for(var i=0;i<keys.length;i++)
		{
		results[0][keys[i]] = json[keys[i]];
		}
		console.log(results)*/
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

router.post('/editEvents',(req,res)=>{
	var eid = req.body.eid;
	var name = req.body.name;
	var theme = req.body.theme;
	var description = req.body.description;		
	var reg_fee_c = req.body.fee_c;
	var reg_fee_nc = req.body.fee_nc;
	var venue = req.body.venue;
	var edate = req.body.edate;
	var speaker = req.body.speaker;
	var prize = req.body.prize;
	var qs_set = req.body.qs_set;	
	var internet = req.body.internet;	
	var comment = req.body.comment;
	var software_install = req.body.software_install;	
	/*var creative_budget = req.body.cb;
	var publicity_budget = req.body.pb;
	var guest_budget = req.body.gb;
	var others_budget = JSON.stringify(req.body.ob);*/
	//console.log(others_budget);
	//modifying events table 
	connection.query('UPDATE events e INNER JOIN technical t ON e.eid = t.eid SET e.status=3, e.name=?, e.theme=?, e.description=?, e.reg_fee_c=?, e.reg_fee_nc=?, e.venue=?, e.event_date=?, e.speaker=?, e.prize=?, t.qs_set=?, t.internet=?,t.comment=?, t.software_install=? WHERE e.eid=?',[name,  theme, description, reg_fee_c, reg_fee_nc, venue, edate, speaker, prize,qs_set, internet,comment,software_install, eid],function(err,results,fields){
	
	if(err){
			console.log(err);			
			res.sendStatus(400);
		}
	else{
				console.log("Data Modified");
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
