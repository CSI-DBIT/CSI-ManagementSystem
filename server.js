var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

// MySQL Connection
const connection = mysql.createConnection({
    host: 'localhost',
    user: process.env.database_user,
    password: process.env.database_password,
    database: 'CsiManagementSystem'
});

connection.connect(function(err) {
    if (!err) {
        console.log('Connected to MySql!\n');
    } else {
        console.log(err);
    }
});

//server scripting
var router=express.Router();
app.post('/login', (req, res) =>{
     var id= req.body.id;
     var password = req.body.password;
     //console.log(id,password);
     connection.query('SELECT * FROM users WHERE id = ?',[id], function (error, results, fields) {
     if (error) {
      //console.log("error ocurred",error);
      res.status(400).send({
      "failed":"error ocurred"
      })
     }else{
        // console.log('The solution is: ', results);
       if(results.length >0){
          if(results[0].password == password){
             res.status(200).send(
             JSON.parse(JSON.stringify(`{'role':'" ${results[0]}.role "'}`))
             {
               "role":results[0].role
             }
             );
          }
	else{
          res.status(204).send({
          "success":"Email and password does not match"
          });
        }
       }
       else{
         res.status(204).send({
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
