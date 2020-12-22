let host = "http://ec2-54-218-134-168.us-west-2.compute.amazonaws.com:8088";
let apiEndpoint =
  "/ProjectOne/apiroot/user/financemanager/servicereciepts/getalltickets";
let updateStatusEndpoint =
  "/ProjectOne/apiroot/user/financemanager/servicereciepts/updatestatus";
let apiData = [];

async function getAllReimbRequests() {
  //creates default table row to display when user does not have any tickets.

  fetch(host + apiEndpoint, { method: "GET", credentials: "include" }).then(
    async (resp) => {
      let tableBody = document.getElementById("reimbursment-requests-tbody");
      let tableRows = createNothingToDisplayElement();
      appendRowsToBody(tableRows);

      apiData = await resp.json();

      if (resp.status === 200 && apiData.length > 0) {
        tableRows = createTableBody(apiData);
        appendRowsToBody(tableRows);
      }
    },
    (err) => {
      let tableBody = document.getElementById("reimbursment-requests-tbody");
      let tableRows = createTableBody(null);
      appendRowsToBody(tableRows);
    }
  );
}

function createNothingToDisplayElement() {
  let nothingToDisplayRow = document.createElement("tr");
  let nothingToDisplayData = document.createElement("td");

  nothingToDisplayData.setAttribute("colspan", "7");
  nothingToDisplayData.innerHTML = "No reimbursment requests discovered.";
  nothingToDisplayRow.appendChild(nothingToDisplayData);

  return [nothingToDisplayRow];
}

//this function converts JSON data into a table row
function createTableRowDataEntry(JSONdata) {
  //nested function that generates td for our tr
  function appendTD(tr, data, colspan) {
    let td = document.createElement("td");
    td.innerHTML = data + "";
    td.className = "text-truncate";

    //if user passed colspan param into function,
    //add the colspan attribute to td
    if (colspan !== undefined) td.setAttribute("colspan", colspan + "");

    tr.appendChild(td);
  }

  function appendTDCheckBox(tr, requestId) {
    let td = document.createElement("td");
    let bootstrapCheckbox = document.createElement("input");

    bootstrapCheckbox.type = "checkbox";
    bootstrapCheckbox.id = requestId;
    bootstrapCheckbox.checked = false;
    bootstrapCheckbox.className = "form-input-checkbox";
    td.appendChild(bootstrapCheckbox);
    tr.appendChild(td);
  }

  let tr = document.createElement("tr");
  let {
    ticketNumber,
    amount,
    dateSubmittedString,
    reimb_resolved,
    description,
    reimb_receipt,
    processedByString,
    statusString,
    requestedByString,
    typeString,
  } = JSONdata;

  /*"ticketNumber": 1,
        "dateSubmitted": null,
        "dateResolved": null,
        "dateSubmittedString": "No date found. Check back soon.",
        "dateResolvedString": "No date found. Check back soon.",
        "description": "Hotel stay for a week in Seattle for a business trip.",
        "amount": 499.1199951171875,
        "status": 1,
        "requestedBy": 1,
        "processedBy": 1,
        "requestedByString": "berin",
        "processedByString": "berin",
        "type": 1,
        "image": null
    },*/

  //append all data to dynamically generated tr.

  appendTD(tr, dateSubmittedString, 1);
  appendTD(tr, ticketNumber, 1);
  appendTD(tr, description, 2);
  appendTD(tr, amount.toFixed(2), 1); //spaces added to fix offset caused by scroll on tbody
  appendTD(tr, typeString, 1);
  appendTD(tr, statusString, 1);
  appendTD(tr, requestedByString, 1);
  appendTD(tr, processedByString, 1);

  return tr;
}
//creates table body for user
function createTableBody(tableData) {
  let tableRows = createNothingToDisplayElement();
  let tableRowElement = null;

  let renderPending = document.getElementById("pending-check").checked;
  let renderApproved = document.getElementById("approved-check").checked;
  let renderDenied = document.getElementById("denied-check").checked;

  function renderRow(rowData) {
    let renderRowData = false;
    let { status } = rowData;

    switch (status) {
      case 1:
        renderRowData = renderPending;
        break;
      case 2:
        renderRowData = renderApproved;
        break;
      case 3:
        renderRowData = renderDenied;
        break;
    }
    return renderRowData;
  }

  if (Array.isArray(tableData) && tableData.length > 0) {
    tableRows = [];

    for (let dataElement of tableData) {
      if (renderRow(dataElement)) {
        tableRowElement = createTableRowDataEntry(dataElement);
        tableRows.push(tableRowElement);
      }
    }
  }

  return tableRows;
}

function appendRowsToBody(tableRows) {
  let tableBody = document.getElementById("reimbursment-requests-tbody");
  tableBody.innerHTML = "";

  if (tableRows !== null && tableRows !== undefined && tableRows.length > 0) {
    for (let row of tableRows) {
      tableBody.appendChild(row);
    }
  } else {
    let nothingToDisplayRow = createNothingToDisplayElement();
    tableBody.appendChild(nothingToDisplayRow[0]);
  }
}

function onCheckClick() {
  let rowsToReappend = createTableBody(apiData);
  appendRowsToBody(rowsToReappend);
}

function changeTicketStatus() {
  let ticketNumber = document.getElementById("ticket-id").value;
  let statusUpdate = document.getElementById("status-id").value;

  let headers = new Headers();
  headers.append("Content-Type", "Application/Json");
  let body = { ticketNumber: ticketNumber, status: statusUpdate };
  body = JSON.stringify(body);

  fetch(host + updateStatusEndpoint, {
    method: "POST",
    credentials: "include",
    body: body,
  }).then(
    (resp) => {
      if (resp.status == 200) {
        getAllReimbRequests();
      }
    },
    (err) => {}
  );
}

window.onload = () => getAllReimbRequests();
