var express=require('express');
var router=express.Router();

//Mysql Connection
var mysql=require('mysql');
var connection=mysql.createConnection({
	host:'localhost',
	user:process.env.database_user,
	password:process.env.database_password,
	database:'CsiApp'
});
connection.connect(function(err){
	if(err){
		console.log('Not Connected to MySql!\n');
	}
	else{
		console.log("Connected To Mysql!\n");
	}
});

//Listing All events
router.get('/listcreative',(req,res)=>{
	connection.query('SELECT eid,name,theme,event_date FROM events where status=2',function(err,result){
		if(err){
			console.log("Error");
		}
		else{
			console.log("Succesfully Listed");
			res.status(200).send(result);
		}
	});
});

//Viewing pre-filled event detail
router.post('/viewpropdetail',(req,res)=>{
	var eid=req.body.eid;

	connection.query('SELECT name,theme,event_date,speaker,venue,reg_fee_c,reg_fee_nc,prize,convert(description using utf8)as description,creative_budget,publicity_budget,guest_budget FROM events WHERE eid=?',[eid],function(err,result){
		if(err){
			console.log("Error");
			res.sendStatus(400);
		}
		else{
			console.log("Succesfully Listed");
			res.status(200).send(result[0]);
		}
	});
});

//Poster,video uploading
var multer=require('multer');

var storage=multer.diskStorage({
	destination:function(req,file,cb){
		cb(null,'creative/');
	},
	filename:function(req,file,cb){
		cb(null,file.originalname);
	}
});

var upload=multer({
	storage:storage,
	limits:{
		fileSize: 1024*1024 *20
	}
}).array('file',3);

router.post('/upload',(req,res)=>{
   	upload(req,res,function(err){
		if(err){
			console.log("Error");
			res.sendStatus(400);
		}
		else{
			console.log("Succesfully Uploaded");
			res.sendStatus(200);
		}
	});
});

module.exports=router;
