function getAllReimbRequests() {
  //creates default table row to display when user does not have any tickets.
  function createNothingToDisplayElement() {
    let nothingToDisplayRow = document.createElement("tr");
    let nothingToDisplayData = document.createElement("td");

    nothingToDisplayData.setAttribute("colspan", "7");
    nothingToDisplayData.innerHTML = "No reimbursment requests discovered.";
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
    let { dateSubmitted, description, amount, status, requestedBy } = JSONdata;

    //append all data to dynamically generated tr.
    appendTD(tr, dateSubmitted, 1);
    appendTD(tr, description, 3);
    appendTD(tr, amount, 1); //spaces added to fix offset caused by scroll on tbody
    appendTD(tr, status, 1);
    appendTD(tr, requestedBy, 1);
    appendTDCheckBox(tr, "test-id", 1);

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
  const data = [];

  function renderData(data) {}
  for (let dataGenerator = 0; dataGenerator < 50; dataGenerator++) {
    data[dataGenerator] = {
      dateSubmitted: Date.now(),
      description: "abcd efg hi jk lmno pqrst uvwhxy 123455",
      status: "Pending",
      amount: "999.99",
      requestId: "1",
      requestedBy: "Gandalf",
    };
  }
  let tableBody = document.getElementById("reimbursment-requests-tbody");
  let tableRows = createTableBody(data);

  for (let row of tableRows) tableBody.appendChild(row);
}
window.onload = () => getAllReimbRequests();
