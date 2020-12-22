async function getPastRequests() {
  //creates default table row to display when user does not have any tickets.

  let EndPoint =
    "/ProjectOne/apiroot/user/employee/servicereciepts/getallbyusername";
  let host = "http://ec2-54-218-134-168.us-west-2.compute.amazonaws.com:8088";

  function createNothingToDisplayElement() {
    let nothingToDisplayRow = document.createElement("tr");
    let nothingToDisplayData = document.createElement("td");

    nothingToDisplayData.setAttribute("colspan", "7");
    nothingToDisplayData.innerHTML = "No pending or prior requests discovered.";
    nothingToDisplayRow.appendChild(nothingToDisplayData);

    return nothingToDisplayRow;
  }

  //this function converts JSON data into a table row
  function createTableRowDataEntry(JSONdata) {
    //nested function that generates td for our tr

    function appendTD(tr, data, colspan) {
      let td = document.createElement("td");
      td.innerHTML = data + "";

      //if user passed colspan param into function,
      //add the colspan attribute to td
      if (colspan !== undefined) td.setAttribute("colspan", colspan + "");

      tr.appendChild(td);
    }

    let tr = document.createElement("tr");
    let {
      amount,
      description,
      processedByString,
      statusString,
      dateSubmittedString,
    } = JSONdata;

    appendTD(tr, dateSubmittedString, 1);
    appendTD(tr, description, 3);
    appendTD(tr, amount, 1); //spaces added to fix offset caused by scroll on tbody
    appendTD(tr, statusString, 1);
    appendTD(tr, processedByString, 1);

    return tr;
  }

  //creates table body for user
  function createTableBody(tableData) {
    let tableRows = createNothingToDisplayElement();
    let tableRowElement = null;

    if (Array.isArray(tableData) && tableData.length > 0) {
      tableRows = [];

      for (let dataElement of tableData) {
        tableRowElement = createTableRowDataEntry(dataElement);
        tableRows.push(tableRowElement);
      }
    }

    return tableRows;
  }

  fetch(host + EndPoint, { method: "GET", credentials: "include" }).then(
    async (response) => {
      let tableBody = document.getElementById("submitted-tickets-tbody");
      let noResultsRow = createNothingToDisplayElement();
      tableBody.appendChild(noResultsRow);

      if (response.status == 200) {
        tableBody.innerHTML = "";
        let respData = await response.json();
        if (respData.length > 0) {
          let tableRows = createTableBody(respData);
          for (let row of tableRows) tableBody.appendChild(row);
        }
      }
    },
    (failure) => {
      let tableBody = document.getElementById("submitted-tickets-tbody");
      let noResultsRow = createNothingToDisplayElement();
      tableBody.appendChild(noResultsRow);
    }
  );
}

window.onload = () => getPastRequests();
