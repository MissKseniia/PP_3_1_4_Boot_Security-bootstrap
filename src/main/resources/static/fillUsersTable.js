async function loadIntoTable() {

    const url = 'http://localhost:8080/admin/api/users';
    const table = document.getElementById('usersTable');

    const tableBody = table.querySelector("tbody");
    const response = await fetch(url);
    const data = await response.json();

    let tab = ''
    for (let user of data) {

        let allRoles = '';
        const space = ', '

        for (let i = 0; i < user.roles.length; i++) {
            allRoles += user.roles[i].role.substring(5, user.roles[i].role.length);
            allRoles += space;
        }

        allRoles = allRoles.trimEnd().substring(0, allRoles.length - 2);

        tab += `<tr> 
                    <td>${user.id}</td>
                    <td>${user.firstname}</td>
                    <td>${user.lastname}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>    
                    <td>${allRoles}</td>    
                        
                    <td>
                        <button 
                            id="editUserButton"
                            class= "btn btn-info editingButton"
                            data-toggle="modal"
                            data-target="#editUserModal"
                            onclick="showEditModal(${user.id})"             
                            >Edit
                        </button>

                    </td>
                    
                    <td>
                        <button id="deleteUserButton"
                           class="btn btn-danger deleteButton"
                           data-toggle="modal"
                            data-target="#deleteUserModal"
                            onclick="showDeleteModal(${user.id})" 
                           >Delete
                        </button>

                    </td>
                      
                </tr>`;
    }

    tableBody.innerHTML = tab;


}
