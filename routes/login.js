var express = require('express');
var router = express.Router();
var mysql = require('mysql');

// MySQL Connection
const connection = mysql.createConnection({
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

router.post('/', (req, res) =>{
     var id= req.body.id;
     var password = req.body.password;
     //Query to select the tuple of the user
     connection.query('SELECT * FROM profile WHERE id = ?',[id], function (error, results, fields) {
     if (error) {
      console.log(error);
      res.status(404);
     }else{
       if(results.length >0){
          //User exists
          if(results[0].password == password){
             //Users password match
             res.status(200).send({
               "role":results[0].role
             });
          }
	        else{
            //Users password do not match
            res.sendStatus(404);
          }
       }
       else{
         //User does not exist
         res.sendStatus(404);
       }
      }
    });
});


module.exports = router;
