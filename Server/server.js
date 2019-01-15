const express = require('express');
const app = express();
const bodyParser = require('body-parser');

// Body-parser Middleware
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.get("/test",(req,res)=>{
	return res.send("It works!!");
});

// Server start
app.listen(8080, (req, res) => {
    console.log("Listening to port 8080");
});
