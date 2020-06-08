var express=require('express');
var router=express.Router();
var dotenv = require('dotenv');
dotenv.config();

//Email
var nodemailer=require('nodemailer');
var transporter=nodemailer.createTransport({
	service:'gmail',
	auth:{
		user:process.env.username,
		pass: process.env.pass
	}
});

//Request Handler
router.post('/',(req,res)=>{
	var mailOptions={
		from:'csi.managementapp@gmail.com',
		to:'csi.managementapp@gmail.com',
		subject:'CSI-App Feedback',
        html: '<p><span style="font-size: 18px;">Greetings!</span></p><br><p>Here is the feedback provided by <strong>"'+req.body.name+'"</strong> with id <strong>"'+req.body.id+'"</strong>:</p><br>'+req.body.feedback+'<br><br><br>Regards,<br><strong>CSI-Management APP development team.</strong>'
	};
	transporter.sendMail(mailOptions,function(error,info){
		if(error){
			console.log("Mail Error");
			console.log(error);
			res.sendStatus(400);
		}
		else{
		console.log('Email sent:'+ info.response);
		res.sendStatus(200);
		}
	});
});

module.exports=router;
