// NEW USER FORM
const newUserForm = document.getElementById('newUserForm')
const newUser_id = 0;
const newUser_firstname = document.getElementById('firstname')
const newUser_lastname = document.getElementById('lastname')
const newUser_age = document.getElementById('age')
const newUser_password = document.getElementById('password')
const newUser_email = document.getElementById('email')
const newUser_roles = document.getElementById('roles')

$('#addNewUserButton').on('click', function (event) {

    event.preventDefault();

    let isIncorrect =
        validateInputs(newUserForm, newUser_id, newUser_firstname, newUser_lastname, newUser_age,
            newUser_password, newUser_email, newUser_roles);

    if (isIncorrect) {
        event.stopPropagation();
    } else {
        insertNewUser();
        for (let element of newUserForm.getElementsByClassName('toClean')) {
            element.classList.remove('is-valid');
        }
    }
    newUserForm.classList.remove('was-validated');


});


function resetForm() {
    const form = document.getElementById('newUserForm');
    form.reset()
}

function insertNewUser() {
    let form = $('#newUserForm')

    const url = 'http://localhost:8080/admin/api/users';

    let rolesArray = form.find("#roles").val();
    for (let i = 0; i < rolesArray.length; i++) {
        rolesArray[i] = Number(rolesArray[i]);
    }

    let user = {
        firstname: form.find("#firstname").val(),
        age: Number(form.find("#age").val()),
        email: form.find("#email").val(),
        lastname: form.find("#lastname").val(),
        password: form.find("#password").val(),
        roles: rolesArray
    }

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(user),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: () => {
            loadIntoTable();
            $('#new_user-tab').removeClass('active').attr('aria-selected', false);
            $('#new_user').removeClass('active').removeClass('show');
            $('#users_table-tab').addClass('active').attr('aria-selected', true);
            $('#users_table').addClass('active').addClass('show');
            console.log("created");
            resetForm();
        },
        error: (err) => {
            alert(err);
        }
    })
}




