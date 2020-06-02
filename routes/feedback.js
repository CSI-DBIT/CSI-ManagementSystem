var express=require('express');
var router=express.Router();

//Email
var nodemailer=require('nodemailer');
var transporter=nodemailer.createTransport({
	service:'gmail',
	auth:{
		user:'generixteam2019@gmail.com',
		pass: 'Lifeisgud'
	}
});

//Request Handler
router.post('/',(req,res)=>{
	var mailOptions={
		from:'generixteam2019@gmail.com',
		to:'generixteam2019@gmail.com',
		subject:'CSI-App Feedback',
        html: '<p><span style="font-size: 18px;">Greetings!</span></p><br><p>Here is the feedback provided by <strong>"'+req.body.name+'"</strong> with id <strong>"'+req.body.id+'"</strong>:</p><br>'+req.body.feedback+'<br><br><br>Regards,<br><strong>CSI-Management APP development team.</strong>'
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