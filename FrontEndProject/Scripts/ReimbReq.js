let host = "http://ec2-54-218-134-168.us-west-2.compute.amazonaws.com:8088";
let apiendpoint =
  "/ProjectOne/apiroot/user/employee/servicereciepts/createticket";

function parseTypeFromForm(reimbType) {
  let type = 1;

  switch (reimbType) {
    case "TRAVEL":
      type = "2";
      break;
    case "FOOD":
      type = "3";
      break;
    case "OTHER":
      type = "4";
      break;
  }
  return type;
}
function BuildAndStringifyBody() {
  let body = {
    description: document.getElementById("description").value,
    amount: document.getElementById("amount").value,
    type: parseTypeFromForm(document.getElementById("type")),
    image: document.getElementById("file-image").value,
  };

  body = JSON.stringify(body);

  return body;
}

function submitTicket() {
  fetch(host + apiendpoint, {
    method: "post",
    credentials: "include",
    body: BuildAndStringifyBody(),
  })
    .then(
      (res) => {
        let msgBanner = document.getElementById("server-results");
        if (res.status === 205) {
          msgBanner.innerHTML = "Your reimbursment request was a success!";
          document.getElementById("description").value = "";
          document.getElementById("amount").value = 0;
          document.getElementById("file-image").value = null;
          document.getElementById("type").value = "LODGING";
        } else {
          msgBanner.innerHTML =
            "There was a problem with your reimbursment request. Please try at a later time.";
        }
      },
      (err) => {
        document.getElementById("server-results").innerHTML =
          "There was a problem with your reimbursment request. Please try at a later time.";
      }
    )
    .catch((err) => {});
}
