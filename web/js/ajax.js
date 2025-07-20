
function createTableFromJSON(data) {
    var html = "<table><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table>";
    return html;

}

function generateHTMLTable(jsonArray) {
    // Parse the JSON strings into objects
    console.log(jsonArray);
    const data = jsonArray.map(item => JSON.parse(item));

    // Create the HTML table
    let html = `<table border="1" style="border-collapse: collapse; width: 100%;">`;
    html += `
        <thead>
            <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Availability</th>
            </tr>
        </thead>
        <tbody>
    `;

    // Add rows for each item in the data
    data.forEach(item => {
        html += `
            <tr>
                <td>${item.Name}</td>
                <td>${item.Type}</td>
                <td>${item.Availability}</td>
            </tr>
        `;
    });

    html += `</tbody></table>`;
    return html;
}



function getUser() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#ajaxContent").html(createTableFromJSON(xhr.responseText));
          //  $("#ajaxContent").html("Successful Login");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("User not exists or incorrect password");
        }
    };
    var data = $('#loginForm').serialize();
    xhr.open('GET', 'GetUser?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


function initDB() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              $("#ajaxContent").html("Successful Initialization");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Error Occured");
        }
    };

    xhr.open('GET', 'InitDB');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function deleteDB() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              $("#ajaxContent").html("Successful Deletion");
        } else if (xhr.status !== 200) {
             $("#ajaxContent").html("Error Occured");
        }
    };

    xhr.open('GET', 'DeleteDB');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


function GetAvailableTickets(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
              $("#answer_events_holder").html(createTableFromJSON(JSON.parse(xhr.responseText)));
        } else if (xhr.status !== 200) {
             $("#answer_events_holder").html("Error Occured");
        }
    };

    xhr.open('GET', 'GetAvailableTicketsEvents');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

$(document).ready(function ()
{
    isLoggedIn();
});



function isLoggedIn() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#answer_login").html("Welcome again" + xhr.responseText);
            return xhr.responseText;
        } else if (xhr.status !== 200) {
            return -1;
            //$("#choices").load("buttons.html");
            console.log("Nothing to say");
        }
    };
    xhr.open('GET', 'Login');
    xhr.send();
}


function loginPOST() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {

            $("#answer_login").html("Successful Login");
            const responseData = JSON.parse(xhr.responseText);

        } else if (xhr.status !== 200) {
            $("#answer_login").html("Wrong Credentials");
            ('Request failed. Returned status of ' + xhr.status);
        }
    };
    var data = $('#user_login_form').serialize();
    console.log(data);
    xhr.open('POST', 'Login');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(data);
}


function logoutPOST(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#answer_login").html("Successful Logout");
        } else if (xhr.status !== 200) {
            alert('Request failed. Returned status of ' + xhr.status);
        }   
    };
    xhr.open('POST', 'Logout');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}
   

function reservationPOST(){
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#answer_ticket_holder").html(xhr.responseText);
        }else if(xhr.status === 307){
            alert('Request failed.You must login to book tickets\n Returned status of ' + xhr.status);
        }else{
            alert('Request failed. Returned status of ' + xhr.status);
        }   
    };
    var data = $('#book_tickets_form').serialize();
    console.log(data);
    xhr.open('POST', 'MakeReservation');
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send(data);
}
