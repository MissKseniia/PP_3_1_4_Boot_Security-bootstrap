async function showEditModal(userId) {

    const url = 'http://localhost:8080/admin/api/users/' + userId;
    const response = await fetch(url);
    const user = await response.json();

    $('#editUserModal').on('shown.bs.modal', function (event) {

        const modal = $(this)

        modal.find("#editId").val(user.id);
        modal.find("#editFirstname").val(user.firstname);
        modal.find("#editAge").val(user.age);
        modal.find("#editEmail").val(user.email);
        modal.find("#editLastname").val(user.lastname);
        modal.find("#editRoles").val(user.roles[0].id).attr('selected', 'selected');
    });

}


// EDIT USER FORM
const editModalForm = document.getElementById('editModalForm')
const editUser_id = document.getElementById('editId')
const editUser_firstname = document.getElementById('editFirstname')
const editUser_lastname = document.getElementById('editLastname')
const editUser_age = document.getElementById('editAge')
const editUser_password = document.getElementById('editPassword')
const editUser_email = document.getElementById('editEmail')
const editUser_roles = document.getElementById('editRoles')

$('#saveModalButton').on('click', function (event) {

    event.preventDefault();


    let isIncorrect = validateInputs(editModalForm, editUser_id, editUser_firstname, editUser_lastname, editUser_age,
        editUser_password, editUser_email, editUser_roles)

    if (isIncorrect) {
        event.stopPropagation();
    } else {
        updateUser();
        for (let element of editModalForm.getElementsByClassName('toClean')) {
            element.classList.remove('is-valid');
        }
    }

    editModalForm.classList.remove('was-validated');


});

function updateUser() {
    let modal = $('#editUserModal')

    const url = 'http://localhost:8080/admin/api/users';

    let rolesArray = modal.find("#editRoles").val();
    for (let i = 0; i < rolesArray.length; i++) {
        rolesArray[i] = Number(rolesArray[i]);
    }

    let user = {
        id: Number(modal.find("#editId").val()),
        firstname: modal.find("#editFirstname").val(),
        age: Number(modal.find("#editAge").val()),
        email: modal.find("#editEmail").val(),
        lastname: modal.find("#editLastname").val(),
        password: modal.find("#editPassword").val(),
        roles: rolesArray
    }

    $.ajax({
        url: url,
        type: 'PUT',
        data: JSON.stringify(user),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: () => {
            loadIntoTable();
            console.log("updated");
        },
        error: (err) => {
            alert(err);
        }
    })

    modal.modal('hide');
}



