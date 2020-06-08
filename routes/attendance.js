var express=require('express');
var router=express.Router();
var dotenv = require('dotenv');
dotenv.config();

// MySQL Connection
var mysql=require('mysql');
const connection=mysql.createConnection({
    	host:'localhost',
		user: process.env.user,
		password: process.env.password,
    	database:'csiApp'
});

connection.connect(function(err){
    	if(!err){
        	console.log('Connected to MySql!');
    	}
	else{
        	console.log("Not Connected To Mysql!");
    	}
});

//Attendance Request
router.post('/request',(req,res)=>{
	var id=req.body.id;
	var date=req.body.date;
	var s1=req.body.s1;
	var s2=req.body.s2;
	var s3=req.body.s3;
	var s4=req.body.s4;
	var s5=req.body.s5;
	var s6=req.body.s6;
	var s7=req.body.s7;
	var reason=req.body.reason;

	//fetching name from users table
	connection.query('SELECT name,year FROM profile WHERE profile.id=?',[id],function(error,rest){
		if (error){
			//console.log("Error");
			res.sendStatus(400);
		}
		else{
			//pushing into request table
			connection.query('INSERT INTO request VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)',[Math.random().toString(36).replace('0.',''),id,rest[0].name,date,s1,s2,s3,s4,s5,s6,s7,reason,rest[0].year],function(err,results,fields){
				if(err){
					//console.log("Error");
					res.sendStatus(400);
				}
				else{
					//console.log("Data Inserted");
					res.sendStatus(200);
				}
			});
		}
	});
});

//Display all the requests
router.post('/requestlist',(req,res)=>{
	connection.query('SELECT * FROM request',function(error,result){
		if(error){
			//console.log"(Error");
			res.sendStatus(400);
		}
		else
		{
    			res.status(200).send(result);
		}
	});
});

//Accept json array,move the record from request to final_list
router.post('/finallist', (req, res) =>{
  	for(var i=0;i<req.body.accepted.length;i++)
	{
		var rid = req.body.accepted[i]
		connection.query('INSERT INTO final_list SELECT * FROM request WHERE RID = ?',[rid], function (error, fields){
			if (error){
				//console.log("Error");
				res.sendStatus(400);
			}
			else{
				connection.query('DELETE FROM request WHERE RID = ?',[rid], function (error, results, fields) {
					if (error){
						//console.log("Error");
					res.sendStatus(400);
					}
					else{
						//console.log("Deleted succesfully");
	    					res.sendStatus(200);
					}
				});
				//console.log("Inserted succesfully");
				res.sendStatus(200);
			}
		});
	}
});

//Attendance Reject
router.post('/reject',(req,res)=>
{
	var ids=req.body.rejected;

	//Deleting from request table
	for(i in ids){
		connection.query('DELETE FROM request WHERE RID=?',[ids[i]],function (error,result){
			if (error){
				//console.log("Error");
				res.sendStatus(400);
			}
		});
	}
	res.sendStatus(200);
});

//SBC Attendance
router.post('/view',(req,res)=>{
	var year=req.body.year;
	connection.query('SELECT sum(s1+s2+s3+s4+s5+s6+s7) as total,sum(s1+s2+s3+s4+s5+s6+s7)*0.38 as percent,Name FROM final_list WHERE year=? group by stud_id',[year], function (error,result){
		if(error){
			res.sendStatus(400);
			console.log(error);
		}
		else{
			res.status(200).send(result);
			console.log("Successfull");
		}
	});
});



module.exports = router;
