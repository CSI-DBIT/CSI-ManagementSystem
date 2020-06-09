var express = require('express');
var router = express.Router();
var randomstring = require('randomstring');
var dotenv = require('dotenv');
dotenv.config();

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
    	console.log('Connected to MySql!');
    } else {
        console.log("Not Connected To Mysql!");
    }
});

//Email
var nodemailer=require('nodemailer');
var transporter=nodemailer.createTransport({
	service:'gmail',
	auth:{
		user:process.env.username,
		pass: process.env.pass
	}
});

router.post('/createproposal',(req,res)=>{
	var name = req.body.name;
	var theme = req.body.theme;
	var speaker=req.body.speaker;
	var venue=req.body.venue;
	var reg_fee_c=req.body.reg_fee_c;
	var reg_fee_p=req.body.reg_fee_p;
	var prize=req.body.prize;
	var description = req.body.description;	
	var agenda = req.body.agenda;
	var date = req.body.date;
	var creative_budget = req.body.cb;
	var publicity_budget = req.body.pb;
	var guest_budget = req.body.gb;
	var event_date=req.body.e_date;
	var others_budget = JSON.stringify(req.body.ob);

	//pushing into events table 
	connection.query('INSERT INTO events(eid,name,theme,description,event_date,M_agenda,M_date,creative_budget,publicity_budget,guest_budget,others_budget,p_date,speaker,venue,reg_fee_c,reg_fee_nc,prize) VALUES(?,?,?,?,?,?,?,?,?,?,?,CURDATE(),?,?,?,?,?)',[randomstring.generate(5),name,theme,description,event_date,agenda,date,creative_budget,publicity_budget, guest_budget, others_budget,speaker,venue,reg_fee_c,reg_fee_p,prize],function(error,results,fields){
		if(error){	
			console.log(error);		
			res.sendStatus(400);
		}
		else{
			res.sendStatus(200);
		}
	});
});
	
router.post('/viewagenda',(req,res)=>{
	var date = req.body.date;

	//search for agenda
	connection.query('SELECT agenda FROM minute WHERE minute.da_te=?',[date],function(error,results,fields){
		if (error){
			res.sendStatus(400);
		}
		else{
			for(var i=0;i<results.length;i++){
				results[i]=results[i].agenda;
			}
			res.status(200).send({"agenda":results});
		}
	});
});
router.post('/status',(req,res)=>{
	var eid = req.body.eid;
	var status = req.body.status;
	var comment=req.body.comment;

	//modify status
	connection.query('UPDATE events SET status=?,comment=? WHERE eid=?',[status,comment,eid],function(error){
		if (error){
			res.sendStatus(400);
		}
		else{
			if(status==2){
				connection.query("SELECT email FROM profile WHERE (role='PR Head' or role='Technical Head' or role='Creative Head')",function(err,result){
					if(err)
					console.log("Proposal Email Extraction Error");
					else{
						// console.log(result[i].email);
						for(var i=0;i<3;i++){
							var mailOptions={
								from:'csi.managementapp@gmail.com',
								to: result[i].email,
								subject:'CSI-App Event',
								text:"Hello There!!!!! An event has been created pls fill your respective details"
							}
							transporter.sendMail(mailOptions,function(error,info){
								if(error){
									console.log("Email Error");
									// console.log(error);
									// res.sendStatus(400);
								}
								else{
								console.log('Email sent:'+ info.response);
								// res.sendStatus(200);
								}
							});
						}
						connection.query("INSERT INTO creative(eid,name,theme,description,event_date,speaker,venue,prize,reg_fee_c,reg_fee_nc,creative_budget,publicity_budget,guest_budget,status) SELECT eid,name,theme,description,event_date,speaker,venue,prize,reg_fee_c,reg_fee_nc,creative_budget,publicity_budget,guest_budget,status FROM events WHERE eid=?",[eid],function(error){
							if(error){
								console.log("Creative Insert Table Error");
								res.sendStatus(400);
							}
							else
								res.sendStatus(200);
						});
					}	
				});
					
			}
			else{
				res.sendStatus(200)
			}
		}
	});
});
router.post('/viewproposal', (req, res) =>{
	var eid = req.body.eid;

	//fetching points from minutes table
	connection.query('SELECT name, theme, CONVERT(description USING utf8) as  description,event_date,creative_budget, publicity_budget, guest_budget,CONVERT(comment USING utf8) as  comment from events where eid=?',[eid],function(error,results,fields){
		if (error){
			res.sendStatus(400);
		}
		else{
			res.status(200).send(results[0]);	
		}
	});
});


router.get('/viewlistproposal', (req, res) =>{
	//fetching from events table
	connection.query('SELECT eid,name,theme,status,p_date FROM events order by p_date DESC', function (error, results, fields) {
	if (error){
		res.sendStatus(400);
	}
	else
		res.status(200).send(results);	
	});
});

router.post('/editproposal',(req,res)=>{
	var eid = req.body.eid;
	var name = req.body.name;
	var theme = req.body.theme;
	var description = req.body.description;	
	var date = req.body.date;
	var creative_budget = req.body.cb;
	var publicity_budget = req.body.pb;
	var guest_budget = req.body.gb;

	//pushing into events table 
	connection.query('UPDATE events SET name=?,theme=?,description=?,event_date=?,creative_budget=?,publicity_budget=?,guest_budget=?,status=0 WHERE eid=?',[name,  theme, description, date, creative_budget, publicity_budget, guest_budget, eid],function(error,results,fields){
		if(error){
			console.log(error);			
			res.sendStatus(400);
		}
		else{
			res.sendStatus(200);
			console.log("Data Modified");
		}
	});
});
module.exports = router;


