var express = require('express');
var router = express.Router();
var generator = require('generate-password');
var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: process.env.email_id,
    pass: process.env.email_password
  }
});

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

router.post('/',(req,res)=>{
	var id = req.body.id;

	//fetching deatails from profile table
	connection.query('SELECT id,name,role,email,phone,year,branch,rollno,batch,membership_left FROM profile where id=?',[id],function(error,results,fields){
	if(error)
	{
		console.log(error);
		res.sendStatus(400);
	}
	else
	res.send(results[0]);
	});
});

router.post('/new',(req,res) => {
  var password = generator.generate({
    length: 10,
    numbers: true
  });
  var id = req.body.studentId;
  
  var name = req.body.fullName;
  var email = req.body.email;
  var phone = req.body.phone;
  var year = req.body.yearSelect;
  var branch = req.body.branchSelect;
  var rollno = req.body.rollno;
  var batch = req.body.batchSelect;
  var years = req.body.membershipSelect;
  var mailOptions = {
    from: process.env.email_id,
    to: email,
    subject: 'First Time Login Password',
    text: 'Your password for First Time Login into CSI-Management App is '+password
  };
  transporter.sendMail(mailOptions, function(error, info){
      if (error) {
        console.log(error);
      } else {
        console.log('Email sent: ' + info.response);
      }
    });
  connection.query('Insert into profile values(?,?,?,?,?,?,?,?,?,?,?)',[id,password,"member",name,email,phone,year,branch,rollno,batch,years],function(error,results,fields){
    if(error){
      console.log(error);
      res.sendStatus(400);
    }
    else{
      res.sendStatus(200);
    }
  });
});

router.post('/view', (req,res) => {
  connection.query('Select id,name,email,phone,year,branch,rollno,batch,membership_left from profile where role=\'member\'',[],function(error,results,fields){
    if(error){
      console.log(error);
      res.sendStatus(400);
    }
    else{
      res.send(results);
    }
  });
});

router.post('/edit',(req,res)=>{
	var id = req.body.id;
	var name = req.body.name;
	var role = req.body.role;
	var email= req.body.email;
	var phone = req.body.phone;
	var year = req.body.year;
	var branch = req.body.branch;
	var rollno = req.body.rollno;
	var batch = req.body.batch;


	//fetching creator from users table
	connection.query('UPDATE profile SET name =?,email =?,role=?,phone =?,year =?,branch =?,rollno =?,batch =? WHERE id=?',[name,email,role,phone,year,branch,rollno,batch,id],function(error,fields){
	if (error)
	res.sendStatus(400);
	else
	{
		res.sendStatus(200);
		//console.log("Data Updated");
	}
	});
});


module.exports = router;
