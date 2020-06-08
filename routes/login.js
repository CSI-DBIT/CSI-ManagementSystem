var express=require('express');
var router=express.Router();
var dotenv = require('dotenv');
dotenv.config();

// MySQL Connection
var mysql=require('mysql');
const connection = mysql.createConnection({
	host: 'localhost',
	user: process.env.user,
	password: process.env.password,
	database: 'csiApp'
});

connection.connect(function(err) {
	if (!err){
        	console.log('Connected to MySql!');
    	}
    	else{
        	console.log('Not Connected To Mysql!');
    	}
});

router.post('/',(req,res)=>{
     	var id=req.body.id;
     	var password=req.body.password;

	//Query to select the tuple of the user
     	connection.query('SELECT * FROM profile WHERE id = ?',[id],function(error,result){
     		if(error){
      			//console.log("Error");
      			res.status(404);
     		}
		else{
       			if(result.length>0){
          			if(result[0].password==password){
					//console.log("Succesfully Logged In");
             				res.status(200).send({
              					"role":result[0].role,
	       					"name":result[0].name,
	       					"dp":result[0].dp
             				});
          			}
	        		else{
            				//Users password do not match
					console.log("Password Do ");
          				res.sendStatus(400);
          			}
       			}
       			else{
         			//User does not exist
				console.log("user does not exist");
         			res.sendStatus(400);
       			}
      		}
    	});
});

module.exports = router;
