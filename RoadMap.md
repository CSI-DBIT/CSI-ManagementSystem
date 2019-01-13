# ROAD MAP

---

<p >A series of versions to be implemented to achive a fully functional CSI-Management System</p>

## Table of Content
+ [Version 0.1](#v0.1)

### Version 0.1 <a name="v0.1"></a>
#### Fron-End
+ A Page for any CSI-stakeholder to login.
+ It will consist of two input fields :
   + ID -> Validation for 10 digits.
   + Password -> Validation for minimum 8 characters.
+ A POST request will be sent to xyz/login with a json object -> {"name":"abc","password":"1234"}.
+ On OK response, a JSON Object will be received -> {"role":" hod / sbc / chairPerson / techHead / creativeHead / prHead / treaurer "}
+ Take the user to a blank page and display the role.
