/*
  Name: James McCulloch, Logan Doughty, Tamara Wold, Nathan-Fittler-Willis
  Student No: 3291441, 3276255, 3088810 , 3189731
  Date: 08/06/2019
  Course: SENG2050
  Assignment: Assignment 3

  Description: external javascript for alerts and validation
*/


/* hides and shows menu for mobile */
function revealNav() {
  var links = document.getElementById("menu");
  if (links.style.display === "block") {
    links.style.display = "none";
  } else if(links.style.display === "inline-block") {
    links.style.display = "inline-block";
  }else {
    links.style.display = "block";
  }
}


/* Validates login input */
/*make sure inputs arnt empty*/
function validateLogin() {

	if(document.login.uName.value == "") {
		alert("incorrect user ID. Input cannot be empty");
		return false;
  } else if (document.login.pw.value == "") {
    alert("incorrect password. Input cannot be empty");
		return false;
  } else {
  	return true;
  }
} // validateLogin()


/* Validates selected Issue input changes for ITSTaff and USerIssues.jsp page*/
/*make sure inputs arnt empty*/
function validateChange() {
  if(document.selectIssueForm.statusDropdown.value == "option") {
    alert("please select a status change");
    return false;
  } else if(document.selectIssueForm.newComment.value == "") {
    alert("field is empty");
    return false;
  }else {
    return true;
  }
}

/* Validates new Issue input on userPage.jsp*/
/*make sure inputs arnt empty*/
function validateNewIssue() {
  if(document.newIssue.category.value == "option") {
    alert("please selected a status change");
    return false;
  } else if(document.newIssue.title.value == "") {
    alert("field is empty");
    return false;
  } else if(document.newIssue.description.value == "") {
    alert("field is empty");
    return false;
  } else {
    return true;
  }
}

/*Validates the marked as knowledeg base items so nothing can be
submitted to Knowledge base without first being selected */
function validateCheckBoxes() {
  if(this.submitKB.checkbox1.checked && !this.submitKB.issueid.checked){
    alert("you must select a checkbox in order to continue, resolution details must be added to a resolved issue before being able to submit");
    return false;
  }

}


/* Validates knowledgebase comments */
function newCom() {
  if(document.selectIssueForm.newComment.value != "") {
    return true;
  } else {
    alert("field is empty");
    return false;
  }
}

/*function to control user alerts when a change has been made
to the status of an issue and requires the users attention and
displays a message*/
function newAction(newAlert){
  alert("!!!!ATTENTION!!!!\r\n\r\nIT Staff have responded to your query\r\nPlease check your Active issues.\r\nHave a great day and I hope we have solved your problem :)");
}
