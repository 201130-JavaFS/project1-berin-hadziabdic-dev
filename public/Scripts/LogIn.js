async function login() {
  const loginUrl = "";
  //Get values from inputusername  boxes
  let username = document.getElementById("username-input").value;
  let password = document.getElementById("password-input").value;
  //get hidden warning element that displays errors in the case of 500/400 error resp
  let hiddenWarning = document.getElementById("login-error-warning");

  //declare these functions here to hide from global scope
  //do this when fetch priomise is fullfiled.responseBody is the parsed json sent from
  //server.
  function onFulfill(responseBody) {
    let { username, password } = responseBody;

    if (username !== undefined && password !== undefined) {
      document.cookie = `username=${username};password=${password};`;
    } else onReject();
  }

  //This function is used to respond to server failures and or userinput errors,
  //such as a bad username/passsword combination.
  function onReject() {
    hiddenWarning.hidden = false;
  }

  fetch(loginUrl, {
    method: "POST",
    headers: "application/json",
    body: { username: username, password: password },
  })
    .then(
      (response) => {
        //if http status is 200 and we are reasonably sure the response object
        // is the fetch response object.
        if (response.status == 200 && response.json !== undefined) {
          onFulfill(response.json()); //pass the json to the fulfliff function
        } else {
          onReject(); //otherwise, we probably got a 401 or 400, so we should call on Reject.
        }
      },
      (fail) => onReject()
    )
    .catch((ooops) => onReject());
}
