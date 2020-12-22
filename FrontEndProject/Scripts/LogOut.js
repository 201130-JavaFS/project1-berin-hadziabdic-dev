let logoutHost =
  "http://ec2-54-218-134-168.us-west-2.compute.amazonaws.com:8088";
let endpoint = "/ProjectOne/apiroot/logout";

function logOut() {
  fetch(logoutHost + endpoint, {
    method: "POST",
    credentials: "include",
  })
    .then(
      (res) => {
        window.location.replace("../index.html");
      },
      (err) => {
        window.location.replace("../index.html");
      }
    )
    .catch((e) => window.location.replace("../index.html"));
}

function logOutMgr() {
  fetch(logoutHost + endpoint, {
    method: "POST",
    credentials: "include",
  })
    .then(
      (res) => {
        window.location.replace(
          "http://s3-us-west-2.amazonaws.com/www.projectone.com/index.html"
        );
      },
      (err) => {
        window.location.replace(
          "http://s3-us-west-2.amazonaws.com/www.projectone.com/index.html"
        );
      }
    )
    .catch((e) =>
      window.location.replace(
        "http://s3-us-west-2.amazonaws.com/www.projectone.com/index.html"
      )
    );
}
