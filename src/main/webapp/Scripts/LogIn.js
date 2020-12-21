async function login() {
  const loginUrl = "/ProjectOne/ProjectOne/apiroot/login";
  //Get values from inputusername  boxes
  let username = document.getElementById("username-input").value;
  let password = document.getElementById("password-input").value;
  //get hidden warning element that displays errors in the case of 500/400 error resp
  let hiddenWarning = document.getElementById("login-error-warning");
  const hostname = "";

  function validToken(respToken) {
    let { JSESSIONID, role, username } = respToken;

    let JSESSIONchecks = typeof JSESSIONID === "string";
    let roleCheck = typeof role === "string";
    let usernameCheck = typeof username === "string";

    return roleCheck && usernameCheck && JSESSIONchecks;
  }

  function RedirectBasedOnToken(authToken) {
    if (authToken.role === "1") {
      location.replace("/ProjectOne/HTML/employeeDash.html");
    }
  }
  //declare these functions here to hide from global scope
  //do this when fetch priomise is fullfiled.responseBody is the parsed json sent from
  //server.

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
    credentials: "same-origin",
  })
    .then(
      async (response) => {
        //if http status is 200 and we are reasonably sure the response object
        // is the fetch response object.
        if (response.status == 200 && response.json !== undefined) {
          let jsonBody = await response.json();
          onFulfill(jsonBody); //pass the json to the fulfliff function
        } else {
          onReject(response); //otherwise, we probably got a 401 or 400, so we should call on Reject.
        }
      },
      (fail) => onReject()
    )
    .catch((failure) => onReject(failure));
}
