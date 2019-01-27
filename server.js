/*const express = require('express');
const app = express();
const bodyParser = require('body-parser');

var port = process.env.PORT || 8080;

// Body-parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.get("/test",(req,res)=>{
	return res.send("Welcome to CSI DBIT!!!");
});

// Server start
app.listen(port, (req, res) => {
    console.log("Listening to port");
});                                    */





//BACKEND-LOGIN

var express = require('express');
var app = express();
var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

// MySQL Connection
var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'oracle',
    database: 'CsiManagementSystem'
});

connection.connect(function(err) {
    if (!err) {
        console.log('Connected to MySql!\n');
    } else {
        console.log('Not connected to MySql.\n');
    }
});

//server scripting
var router=express.Router();
router.post('/login', (req, res) =>{
	 var id= req.body.id;
     var password = req.body.password;
     connection.query('SELECT * FROM users WHERE id = ?',[id], function (error, results, fields) {
     if (error) {
      //console.log("error ocurred",error);
      res.send({
      "code":400,
      "failed":"error ocurred"
    })
	}else{
    // console.log('The solution is: ', results);
    if(results.length >0){
      if(results[0].password == password){
        res.send({
          "code":200,
          "success":"login sucessfull"
            });
      }
	  else{
        res.send({
          "code":204,
          "success":"Email and password does not match"
            });
      }
    }
	else{
      res.send({
        "code":204,
        "success":"Email does not exits"
          });
    }
  }
  });
});

//port activation
app.listen(8080, (req, res) => {
    console.log("Listening on 8080");
});


 
