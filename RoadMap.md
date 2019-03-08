# ROAD MAP

---

<p >A series of versions to be implemented to achive a fully functional CSI-Management System</p>

## Table of Content
+ [Version 0.1](#v0.1)
+ [Version 0.2](#v0.2)
+ [Version 0.3](#v0.3)
+ [Version 0.4](#v0.4)

### Version 0.1 <a name="v0.1"></a>
### Login
#### Front-End
+ A Page for any CSI-stakeholder to login.
+ It will consist of two input fields :
   + ID -> Validation for 10 digits.
   + Password -> Validation for minimum 8 characters.
+ A POST request will be sent to IP/login with a json object -> 
<pre>
{
   "id" : "2016134613",
   "password" : "1234"
}
</pre>
+ On OK response, a JSON Object will be received -> 
{
   "role" : " hod / sbc / chairPerson / techHead / creativeHead / prHead / treaurer "
}
+ Take the user to a blank page and display the role.
#### Back-End
+ Create a Database -> "CsiManagementSystem"
+ Create a table -> "users(id varchar(10),name varchar(25),role varchar(25),password varchar(60))"
+ Install nodejs and write and test API -> method="POST" , Inputs(id,password), Description: Check if passwords match of the user, Output(role)

### Version 0.2 <a name="v0.2"></a>
### Minutes
#### Front-End
+ A page consisting of a recycler view in which each element consists of Agenda, date, time.
+ On clicking on the element one is taken to a page consisting of the Agenda, date, time, creator of the minutes, Bullet points of meeting.
+ Page should also consist of an "Add" button which is used to create a new Minute, in the form of bullet points.
+ A POST request will be sent to IP/minutesList to get the list of minutes.
+ On OK response, a JSON Array will be received -> 
<pre>
{
   "minutes":[
      {
         "agenda":"Some Agenda",
         "date":"Some standard format",
         "time":"Some standard format",
         "creator":"Calden Rodrigues",
         "points":"Hello\nWorld"
      },
      {
         "agenda":"Some Other Agenda",
         "date":"Some standard format",
         "time":"Some standard format",
         "creator":"Sarah Solkar",
         "points": "Some\nWord\nExist"
      }
   ]
}
</pre>
+ A POST request will be sent to IP/createMinutes to create a new minute with the JSON Object ->
<pre>
{
   "agenda":"Some Agenda",
   "date":"Some standard format",
   "time":"Some standard format",
   "creator":"Calden Rodrigues",
   "points":"Hello\nWorld"
}
</pre>
#### Back-End
+ Create a table -> "minutes(id varchar(10),agenda varchar(25),date varchar(25),time varchar(25),creator varchar(25),minute blob)"
+ Write and test "createMinutes" API -> method="POST" , Inputs(agenda,user id, points), Description: get realtime date and time and save it in the database, Output(status codes)
+ Write and test "minutesList" API -> method="POST" , Inputs(userid), Check if user is valid and return a JSON object as mentioned above.

### Version 0.3 <a name="v0.3"></a>
### Profile
#### Front-End
+ Create a page that displays profile details, in key value pairs.
+ The details include:
   + Dummy Profile picture (Future scope to implement real profile pictures)
   + Student ID (Non-Editable)
   + Name (Editable)
   + Email ID (Editable)
   + Phone no (Editable)
   + Year (Editable)
   + Branch (Editable)
   + Roll no. (Editable)
   + Batch (Editable)
+ All the data will be fetched by calling an API "/profile" using the POST request with a JSON Object ->
<pre>
{
  "id" : "2016134613"
}
</pre>
+ For updating the data keep an EDIT button and make the text fields editable.
+ Call the API "editProfile" using POST request with the JSON Object ->
<pre>
{
   "id":"2016134613",
   "name":"Calden Rodrigues",
   "position":"Technical Head"
   "email":"caldenrodrigues1202@gmail.com",
   "phone":"0123456789",
   "year":"FE/SE/TE/BE",
   "branch":"IT/COMPS/EXTC/MECH",
   "rollno":"99",
   "batch":"A"
}
</pre>
#### Back-End
+ Write the two APIs "/profile" and "/editProfile" knowing the inputs from the JSON array as stated.
+ For profile make a SQL Query and return the details in the format stated above.
+ For Edit profile Update the SQL Database with the new details.

### Version 0.4 <a name="v0.4"></a>
### Login
#### Front-End
+ Create a page which consists of a form to request for attendance:
   + Date
   + Multiple select checkbox to input the time slot (9-10, 10-11 etc)
   + Reason (TextView)
+ Call /attendance/request and submit JSON object:
<pre>
{
   "id":"201732709",
   "date":"whatever date format",
   "1":"1",
   "2":"1",
   "3":"0",
   "4":"1",
   "5":"1",
   "6":"0",
   "7":"1",
   "Reason":"Just wasting time, john plz give attendance"
}
</pre>
+ Create another page for accepting requests -> basically a recycler view with 2 buttons Icons (Ok, Reject) and at the bottom a confirm button
+ Call /attendance/requestslist and you will receive this:
<pre>
{
   "requests":[
      {
         "id":"829yhf",
         "stud_id":"201732709",
         "name":"Chaitanya",
         "date":"whatever date format",
         "1":"1",
         "2":"1",
         "3":"0",
         "4":"1",
         "5":"1",
         "6":"0",
         "7":"1",
         "Reason":"Just wasting time, john plz give attendance"
      },
      {
         "id":"92831y",
         "stud_id":"2016134613",
         "name":"Calden",
         "date":"whatever date format",
         "1":"0",
         "2":"0",
         "3":"0",
         "4":"1",
         "5":"1",
         "6":"0",
         "7":"0",
         "Reason":"Doing real work, john plz give attendance"
      }
   ]
}
</pre>
+ On confirm call /attendance/final with the below JSON object:
<pre>
{
   "accepted":[
      "829yhf",
      "92831y"
   ]
}
</pre>
+ Figure out how to show pages only if the user is authorized (eg: Only PR Head should be able to see the page of accepting requests.)
#### Back-End
+ Write the two APIs "/attendance/request" and "/attendance/requestlist" knowing the inputs & outputs from the JSON array as stated.
+ Create a database of time slot with correcponding lecture (Not important as of now)
<table width="100%" cellspacing="0" cellpadding="4"><colgroup><col width="42*" /> <col width="35*" /> <col width="33*" /> <col width="32*" /> <col width="28*" /> <col width="24*" /> <col width="30*" /> <col width="32*" /> </colgroup>
<tbody>
<tr valign="top">
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="16%">
<p>Days</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="13%">
<p>1</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="13%">
<p>2</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="13%">
<p>3</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="11%">
<p>4</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="9%">
<p>5</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="12%">
<p>6</p>
</td>
<td style="border: 1px solid #000000; padding: 0.1cm;" width="12%">
<p>7</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>MondayA</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>csd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>csd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>MondayB</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>bi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>bi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>MondayC</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>sn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>sn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>MondayD</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>sd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>sd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>TuesdayA</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>csd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>csd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>TuesdayB</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>bi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>bi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>TuesdayC</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>sn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>sn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="16%">
<p>TuesdayD</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>wn</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>dmbi</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="13%">
<p>sd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="11%">
<p>sd</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>sepm</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="12%">
<p>mentoring</p>
</td>
</tr>
</tbody>
</table>

+ Create 2 identical tables for requests and final List where ID is some fixed length randon string

<table width="100%" cellspacing="0" cellpadding="4"><colgroup><col width="32*" /> <col width="36*" /> <col width="26*" /> <col width="26*" /> <col width="16*" /> <col width="22*" /> <col width="19*" /> <col width="16*" /> <col width="21*" /> <col width="21*" /> <col width="21*" /> </colgroup>
<tbody>
<tr valign="top">
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="12%">
<p>ID</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="14%">
<p>stud_id</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="10%">
<p>Name</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="10%">
<p>Date</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="6%">
<p>1</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="9%">
<p>2</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="8%">
<p>3</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="6%">
<p>4</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="8%">
<p>5</p>
</td>
<td style="border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0.1cm 0cm 0.1cm 0.1cm;" width="8%">
<p>6</p>
</td>
<td style="border: 1px solid #000000; padding: 0.1cm;" width="8%">
<p>7</p>
</td>
</tr>
<tr valign="top">
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="12%">
<p>2781yr83</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="14%">
<p>2016134613</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="10%">
<p>Calden</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="10%">
<p>date</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="6%">
<p>0</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="9%">
<p>0</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="8%">
<p>0</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="6%">
<p>1</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="8%">
<p>1</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding: 0cm 0cm 0.1cm 0.1cm;" width="8%">
<p>0</p>
</td>
<td style="border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding: 0cm 0.1cm 0.1cm 0.1cm;" width="8%">
<p>0</p>
</td>
</tr>
</tbody>
</table>

+ When a call is made to /attendance/request insert it into request table
+ When a call is made to /attendance/requestlist send the data from requestlist
+ When a call is made to /attendance/final move the data from request table to final table
