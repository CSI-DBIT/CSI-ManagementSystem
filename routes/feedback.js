var express=require('express');
var router=express.Router();

//Feedback 
router.post('/',(req,res)=>{
			res.status(200).send({"message":"Feedback Mail is in progress"});
	
});

module.exports=router;
