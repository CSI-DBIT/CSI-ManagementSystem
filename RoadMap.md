# ROAD MAP

---

<p >A series of versions to be implemented to achive a fully functional CSI-Management System</p>

## Table of Content
+ [Version 0.1](#v0.1)
+ [Version 0.2](#v0.2)

### Version 0.1 <a name="v0.1"></a>
### Login
#### Front-End
+ A Page for any CSI-stakeholder to login.
+ It will consist of two input fields :
   + ID -> Validation for 10 digits.
   + Password -> Validation for minimum 8 characters.
+ A POST request will be sent to IP/login with a json object -> {"name":"abc","password":"1234"}.
+ On OK response, a JSON Object will be received -> {"role":" hod / sbc / chairPerson / techHead / creativeHead / prHead / treaurer "}
+ Take the user to a blank page and display the role.
#### Back-End
+ Create a Database -> "CsiManagementSystem"
+ Create a table -> "users(id varchar(10),name varchar(25),role varchar(25),password varchar(60))"
+ Install nodejs and write and test API -> method="POST" , Inputs(name,password), Description: Check if passwords match of the user, Output(role)

### Version 0.2 <a name="v0.2"></a>
### Minutes
#### Front-End
+ A page consisting of a recycler view in which each element consists of Agenda, date, time.
+ On clicking on the element one is taken to a page consisting of the Agenda, date, time, creator of the minutes, Bullet points of meeting.
+ Page should also consist of an "Add" button which is used to create a new Minute, in the form of bullet points.
+ A POST request will be sent to IP/minutesList to get the list of minutes.
+ On OK response, a JSON Array will be received -> 
{
   "minutes":[
      {
         "agenda":"Some Agenda",
         "date":"Some standard format",
         "time":"Some standard format",
         "creator":"Calden Rodrigues",
         "points":[
            "Hello",
            "World"
         ]
      },
      {
         "agenda":"Some Other Agenda",
         "date":"Some standard format",
         "time":"Some standard format",
         "creator":"Sarah Solkar",
         "points":[
            "Some",
            "Other",
            "World"
         ]
      }
   ]
}
+ A POST request will be sent to IP/createMinutes to create a new minute with the JSON Object ->
{
   "agenda":"Some Agenda",
   "date":"Some standard format",
   "time":"Some standard format",
   "creator":"Calden Rodrigues",
   "points":[
      "Hello",
      "World"
   ]
}

#### Back-End
+ Create a Database -> "CsiManagementSystem"
+ Create a table -> "users(id varchar(10),name varchar(25),role varchar(25),password varchar(60))"
+ Install nodejs and write and test API -> method="POST" , Inputs(name,password), Description: Check if passwords match of the user, Output(role)
