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
    	console.log('Connected to MySql!Proposal.js');
    } else {
        console.log("Not Connected To Mysql!Proposal.js");
    }
});

//Email Connection
var nodemailer=require('nodemailer');
var transporter=nodemailer.createTransport({
	service:'gmail',
	auth:{
		user:process.env.username,
		pass: process.env.pass
	}
});

//Creating propsal
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

	connection.query('INSERT INTO events(eid,name,theme,description,event_date,M_agenda,M_date,creative_budget,publicity_budget,guest_budget,others_budget,p_date,speaker,venue,reg_fee_c,reg_fee_nc,prize) VALUES(?,?,?,?,?,?,?,?,?,?,?,CURDATE(),?,?,?,?,?)',[randomstring.generate(5),name,theme,description,event_date,agenda,date,creative_budget,publicity_budget, guest_budget, others_budget,speaker,venue,reg_fee_c,reg_fee_p,prize],function(error){
		if(error){	
			console.log("Fail to insert into events table");		
			res.sendStatus(400);
		}
		else{
			console.log("Succesfully inserted into events table");
			res.sendStatus(200);
		}
	});
});

//search for agenda
router.post('/viewagenda',(req,res)=>{
	var date = req.body.date;

	connection.query('SELECT agenda FROM minute WHERE minute.da_te=?',[date],function(error,results){
		if (error){
			console.log("Fail to view agenda");
			res.sendStatus(400);
		}
		else{
			for(var i=0;i<results.length;i++){
				results[i]=results[i].agenda;
			}
			console.log("Successfully viewed agenda");
			res.status(200).send({"agenda":results});
		}
	});
});

//modify status
router.post('/status',(req,res)=>{
	var eid = req.body.eid;
	var status = req.body.status;
	var comment=req.body.comment;

	connection.query('UPDATE events SET status=?,comment=? WHERE eid=?',[status,comment,eid],function(error){
		if (error){
			console.log("Fail to update status");
			res.sendStatus(400);
		}
		else{
			//Mail to Creative-head/PR-head/Tech-head
			if(status==2){
				connection.query("SELECT email FROM profile WHERE (role='PR Head' or role='Technical Head' or role='Creative Head')",function(err,result){
					if(err)
					console.log("Proposal Email Extraction Error");
					else{
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
									// res.sendStatus(400);
								}
								else
								console.log('Email sent:'+ info.response);
							});
						}

						//Creating events into creative/publicity/technical table
						connection.query("INSERT INTO creative(eid) VALUES(?)",[eid],function(error){
							if(error){
								console.log("Fail To Insert Into Creative Table");
								res.sendStatus(400);
							}
							else{
								console.log("Succesfully Inserted Into Creative Table");
								connection.query("INSERT INTO publicity(eid) VALUES(?)",[eid],function(error){
									if(error){
										console.log("Fail To Insert Into Publicity Table");
										res.sendStatus(400);
									}
									else{
										console.log("Succesfully Inserted Into Publicity Table");
										connection.query("INSERT INTO technical(eid) VALUES(?)",[eid],function(error){
											if(error){
												console.log("Fail To Insert Into Technical Table");
												res.sendStatus(400);
											}
											else{
												console.log("Succesfully Inserted Into Technical Table");
												res.sendStatus(200);
											}
										
										});
									}
								});
							}
						});
					}	
				});
					
			}
			else{
				console.log("Succesfully updated  status");
				res.sendStatus(200);
			}
		}
	});
});

//View proposal details
router.post('/viewproposal', (req, res) =>{
	var eid = req.body.eid;

	connection.query('SELECT name, theme, CONVERT(description USING utf8) as  description,event_date,creative_budget, publicity_budget, guest_budget,CONVERT(comment USING utf8) as  comment from events where eid=?',[eid],function(error,results){
		if (error){
			console.log("Fail to view proposal");
			res.sendStatus(400);
		}
		else{
			console.log("Successfully viewed proposal");
			res.status(200).send(results[0]);	
		}
	});
});

//Listing All proposal
router.get('/viewlistproposal', (req, res) =>{

	connection.query('SELECT eid,name,theme,status,p_date FROM events order by p_date DESC',function(error,results){
		if (error){
			console.log("Fail to list proposal");
			res.sendStatus(400);
		}
		else{
			console.log("Succedfully listed proposal");
			res.status(200).send(results);	
		}
	});
});

//Edit proposal
router.post('/editproposal',(req,res)=>{
	var eid = req.body.eid;
	var name = req.body.name;
	var theme = req.body.theme;
	var description = req.body.description;	
	var date = req.body.date;
	var creative_budget = req.body.cb;
	var publicity_budget = req.body.pb;
	var guest_budget = req.body.gb;

	connection.query('UPDATE events SET name=?,theme=?,description=?,event_date=?,creative_budget=?,publicity_budget=?,guest_budget=?,status=0 WHERE eid=?',[name,  theme, description, date, creative_budget, publicity_budget, guest_budget, eid],function(error){
		if(error){
			console.log("Fail to edit proposal");			
			res.sendStatus(400);
		}
		else{
			console.log("Succesfully edited proposal");
			res.sendStatus(200);
		}
	});
});


module.exports = router;


