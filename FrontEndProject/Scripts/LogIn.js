async function login() {
  const loginUrl = "/ProjectOne/apiroot/login";
  //Get values from inputusername  boxes
  let username = document.getElementById("username-input").value;
  let password = document.getElementById("password-input").value;
  //get hidden warning element that displays errors in the case of 500/400 error resp
  let hiddenWarning = document.getElementById("login-error-warning");
  const hostname =
    "http://ec2-54-218-134-168.us-west-2.compute.amazonaws.com:8088";

  //declare these functions here to hide from global scope
  //do this when fetch priomise is fullfiled.responseBody is the parsed json sent from
  //server.

  function redirect(statusCode) {
    if (statusCode === 200)
      location.replace(
        "http://s3-us-west-2.amazonaws.com/www.projectone.com/HTML/employeeDash.html"
      );
    else if (statusCode === 209)
      location.replace(
        "http://s3-us-west-2.amazonaws.com/www.projectone.com/HTML/Mgr/ViewAllEmployeeRequests.html"
      );
  }

  function onFulfill(responseBody) {
    if (validToken(responseBody)) {
      document.authToken = responseBody;
      RedirectBasedOnToken(responseBody);
    } else {
      onReject();
    }
  }

  //This function is used to respond to server failures and or userinput errors,
  //such as a bad username/passsword combination.
  function onReject(msg) {
    hiddenWarning.hidden = false;
    console.log(msg);
  }

  let reqHeaders = new Headers();

  reqHeaders.append("Content-Type", "application/json");

  fetch(hostname + loginUrl, {
    method: "POST",
    body: JSON.stringify({ username: username, password: password }),
    credentials: "include",
  })
    .then(
      (response) => {
        //if http status is 200 and we are reasonably sure the response object
        // is the fetch response object.
        if (response.status === 200 || response.status === 209) {
          redirect(response.status);
        } else {
          onReject(response); //otherwise, we probably got a 401 or 400, so we should call on Reject.
        }
      },
      (fail) => onReject()
    )
    .catch((failure) => onReject(failure));
}
