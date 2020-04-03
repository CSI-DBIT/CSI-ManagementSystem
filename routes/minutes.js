var express=require('express');
var router=express.Router();


// MySQL Connection
var mysql=require('mysql');
var connection=mysql.createConnection({
    	host:'localhost',
    	user:'csi',
    	password:'Root@Csi123',
    	database:'csiApp'
});
connection.connect(function(err) {
    	if (!err){
        	console.log('Connected to MySql!');
    	}
	else{
        	console.log("Not Connected To Mysql");
    	}
});

//To Create Minute
router.post('/create',(req,res)=>{
	var id=req.body.id;
	var agenda=req.body.agenda;
	var points=req.body.points;
	var work=JSON.stringify(req.body.work);

	//fetching creator from users table
	connection.query('SELECT name FROM profile WHERE profile.id=?',[id],function(error,creator){
		if(error){
			//console.log("Error");
			res.sendStatus(400);
		}
		else{
			//pushing into minute table 
			connection.query('INSERT INTO minute VALUES(?,?,CURDATE(),CURTIME(),?,?,?)',[id,agenda,creator[0].name,points,work],function(err,result){
				if(err){
					//console.log("Error);
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

//To List All minutes
router.post('/list', (req, res) =>{
	var id = req.body.id;

	//fetching from minute table
	connection.query('SELECT id,agenda,da_te,ti_me,creator,CONVERT(minute USING utf8) as minute,CONVERT(work USING utf8) as work FROM minute order by da_te desc',function(error,result){
		if (error){
			//console.log("Error");
			res.sendStatus(400);
		}
		else{
			//console.log("Succesfully Listed");
			res.status(200).send(result);
		}
	});
});

router.post('/viewminute',(req,res)=>{
	var date = req.body.date;
	var time = req.body.time;

	//fetching from minute table
	connection.query('SELECT id,agenda,da_te,ti_me,creator,CONVERT(minute USING utf8) as minute,CONVERT(work USING utf8) as work FROM minute WHERE da_te=? and ti_me=?',[date,time],function(error,result){
		if (error){
			//console.log("Error");
			res.sendStatus(400);
		}
		else{
			//console.log("Succesfully Listed");
			res.status(200).send(result[0]);
		}
	});
});	

module.exports = router;
