var express=require('express');
var router=express.Router();


router.post('/feedback',(req,res)=>{
			res.status(200).send({"message":"Feedback Mail is in progress"});
	
});

module.exports=router;
