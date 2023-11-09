async function loadUserData() {

    const url = 'http://localhost:8080/admin/api/users/current';

    const response = await fetch(url);
    const user = await response.json();

    // Header
    const divEmail = document.getElementById('userEmailData');
    const divRoles = document.getElementById('userRolesData');

    let email = user.email;
    let allRoles = '';
    const space = ', '

    for (let i = 0; i < user.roles.length; i++) {
        allRoles += user.roles[i].role.substring(5, user.roles[i].role.length);
        allRoles += space;
    }
    allRoles = allRoles.trimEnd().substring(0, allRoles.length - 2);

    divEmail.innerHTML = email;
    divRoles.innerHTML = allRoles;


    // User tab
    const table = document.getElementById('userInfoTable');
    const tableBody = table.querySelector("tbody");

    let tab = ''

    tab += `<tr> 
                <td>${user.id}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>    
                <td>${allRoles}</td>                           
            </tr>`;

    tableBody.innerHTML = tab;

}
