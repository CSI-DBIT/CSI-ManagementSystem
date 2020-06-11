var express=require('express');
var app=express();
var bodyParser=require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var cors=require('cors');
app.use(cors());

var login=require('./routes/login');
var minutes=require('./routes/minutes');
var profile=require('./routes/profile');
var attendance=require('./routes/attendance');
var proposal=require('./routes/proposal');
var creative=require('./routes/creative');
var feedback=require("./routes/feedback");
var publicity=require("./routes/publicity");
var technical=require("./routes/technical");

app.use('/login',login);
app.use('/minutes',minutes);
app.use('/profile',profile);
app.use('/attendance',attendance);
app.use('/proposal',proposal);
app.use('/creative',creative);
app.use('/feedback',feedback);
app.use('/publicity',publicity);
app.use('/technical',technical);

app.get("/",(req,res)=>{
return res.send("Welcome to CSI-DBIT");
});

//Port Listening
app.listen(9000,(req,res)=>{
    console.log("Listening on 9000");
});
