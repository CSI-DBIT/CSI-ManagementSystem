var express = require('express');
var router = express.Router();
var fs = require("fs");
var pdfkit = require("pdfkit");
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
        	console.log('Connected to MySql!Report.js');
    	}
    	else{
        	console.log('Not Connected To Mysql!Report.js');
    	}
});



//Download Report
router.get('/download',(req,res)=>{
var eid = req.query.eid;

	connection.query('SELECT name,theme,event_date,speaker,venue,reg_fee_c,reg_fee_nc,prize,convert(description using utf8)as description,creative_budget,publicity_budget,guest_budget FROM events WHERE eid= ?',[eid], function( err , result){
		if(err) {
    			throw err;
 			} 
		else {
			const a= new pdfkit
			a.pipe(fs.createWriteStream("report/".concat(result[0].name).concat(".pdf")));
			a.fontSize(15).text("Don Bosco Institute of Technology, Kurla(W)",{align: 'center'})
			a.text("Department of Information Technology",{align: 'center'})
			a.moveDown(2);
			a.font('Helvetica-Bold').fontSize(30).text(result[0].name,{align: 'center'})
			a.moveDown();
			a.font('Helvetica-Bold').fontSize(15).text('Name: ', {continued:true}).font('Helvetica').text (result[0].name)
			a.moveDown();
			a.font('Helvetica-Bold').text('Theme: ', {continued:true}).font('Helvetica').text (result[0].theme)
			a.moveDown();
			a.font('Helvetica-Bold').text('Description: ', {continued:true}).font('Helvetica').text (result[0].description)
			a.moveDown();
			a.font('Helvetica-Bold').text('Event Date: ', {continued:true}).font('Helvetica').text (result[0].event_date)
			a.moveDown();
			a.font('Helvetica-Bold').text('Venue: ', {continued:true}).font('Helvetica').text (result[0].venue)
			a.moveDown();
			a.font('Helvetica-Bold').text('Speaker: ', {continued:true}).font('Helvetica').text (result[0].speaker)
			a.moveDown();
			a.font('Helvetica-Bold').text('Finance Summary ')
			a.font('Helvetica').text('Registration fee for CSI members: ',{continued:true}).font('Helvetica').text (result[0].reg_fee_c)
			a.moveDown(2);
			a.text('Registration fee for Non-CSI members: ', {continued:true}).font('Helvetica').text (result[0].reg_fee_nc)
			a.moveDown(2);
			a.font('Helvetica-Bold').text('Prize: ', {continued:true}).font('Helvetica').text (result[0].prize)
			a.moveDown();

		connection.query('SELECT poster_link FROM creative WHERE eid= ?',[eid], function( err , result){
			if(err) {
					throw err;
				} 
			else {
				result[0].poster_link=result[0].poster_link.substr(25);
				a.addPage();
				a.moveDown(2);
				a.font('Helvetica-Bold').fontSize(25).text('Marketing: ')
				a.moveDown();
				a.font('Helvetica').fontSize(15).text('Poster: ')
				a.image(result[0].poster_link,{width: 250, height: 350})
				}
		connection.query('SELECT creative_budget,publicity_budget,guest_budget,total_budget FROM events WHERE eid= ?',[eid], function( err , result){
			if(err) {
					throw err;
				} 
			else {
				a.addPage();
				a.font('Helvetica-Bold').fontSize(25).text('Budget:')
				a.lineCap('butt')
					.moveTo(330, 110)
					.lineTo(330, 200)
					.stroke()

				row(a, 110);
				row(a, 132.5);
				row(a, 155);
				row(a, 177.5);

				textInRowFirst(a, 'Creative budget', 115);
				textInRowFirst(a, 'Publicity budget', 137.5);
				textInRowFirst(a, 'Guest budget', 160);
				textInRowFirst(a, 'Total budget', 182.5);
				textInRowSecond(a,result[0].creative_budget, 115);
				textInRowSecond(a,result[0].publicity_budget, 137.5);
				textInRowSecond(a,result[0].guest_budget, 160);
				textInRowSecond(a,result[0].total_budget, 182.5);

			function textInRowFirst(a, text, heigth) {
				a.y = heigth;
				a.x = 30;
				a.fillColor('black')
				a.font('Helvetica')
				a.fontSize(15)
				a.text(text, {
						paragraphGap: 5,
						indent: 60,
						align: 'justify',
						columns: 1,
					});
				return a
				}

			function textInRowSecond(a, text, heigth) {
				a.y = heigth;
				a.x = 30;
				a.fillColor('black')
				a.font('Helvetica')
				a.text(text, {
						paragraphGap: 5,
						indent: 310,
						columns: 1,
					});
				return a
				}

			function row(a, heigth) {
				a.lineJoin('miter')
					.rect(75, heigth, 500, 22.5)
					.stroke()
				return a
				}
		connection.query('SELECT collected,spent,target FROM publicity WHERE eid= ?',[eid], function( err , result){
			if(err) {
					throw err;
				} 
			else {
				a.moveDown(4);
				a.font('Helvetica-Bold').fontSize(25).text('Summary: ',{indent:45})
				a.moveDown(0.5);
				a.font('Helvetica-Bold').fontSize(15).text('Money collected: ', {indent:45,continued:true}).font('Helvetica').text (result[0].collected)
				a.moveDown();
				a.font('Helvetica-Bold').text('Money spent: ', {indent:45,continued:true}).font('Helvetica').text (result[0].spent)
				a.moveDown();
				
			a.end()  
		connection.query('SELECT name FROM events WHERE eid= ?',[eid], function( err , result){
			if(err) {
					throw err;
				} 
			else {
				var file = 'report/'.concat(result[0].name).concat('.pdf');
				res.download(file); // Set disposition and send it.
				}
				});
			}
			});
			}
			});
		});
	}
});
});
module.exports = router;
