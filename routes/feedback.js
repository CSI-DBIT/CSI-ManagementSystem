var express=require('express');
var router=express.Router();

//Email
var nodemailer=require('nodemailer');
var transporter=nodemailer.createTransport({
	service:'gmail',
	auth:{
		user:'generixteam2019@gmail.com',
		pass:'Lifeisgud'
	}
});

//Request Handler
router.post('/',(req,res)=>{
	var mailOptions={
		from:'generixteam2019@gmail.com',
		to:'generixteam2019@gmail.com',
		subject:'CSI-App Feedback',
		text:'UserID:'+ req.body.id+'\nName:'+req.body.name+'\nFeedback:'+req.body.feedback
	};
	transporter.sendMail(mailOptions,function(error,info){
		if(error){
			console.log("Mail Error");
			res.sendStatus(400);
		}
		else{
		console.log('Email sent:'+ info.response);
		res.sendStatus(200);
		}
	});
});

module.exports=router;