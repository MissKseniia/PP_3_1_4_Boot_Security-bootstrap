// NEW USER FORM
const newUserForm = document.getElementById('newUserForm')
const newUser_firstname = document.getElementById('firstname')
const newUser_lastname = document.getElementById('lastname')
const newUser_age = document.getElementById('age')
const newUser_password = document.getElementById('password')
const newUser_email = document.getElementById('email')
const newUser_roles = document.getElementById('roles')

// EDIT USER FORM
const editModalForm = document.getElementById('editModalForm')
const editUser_firstname = document.getElementById('editFirstname')
const editUser_lastname = document.getElementById('editLastname')
const editUser_age = document.getElementById('editAge')
const editUser_password = document.getElementById('editPassword')
const editUser_email = document.getElementById('editEmail')
const editUser_roles = document.getElementById('editRoles')

const validateInputs = (firstname, lastname, age, password, email, roles) => {
    const firstname_value = firstname.value.trim();
    const lastname_value = lastname.value.trim();
    const password_value = password.value.trim();
    const email_value = email.value.trim();

    const age_value = Number(age.value);
    const roles_value = Number(roles.value);

    let invalid = false;

    if (firstname_value === '') {
        setInvalidStatus(firstname);
        invalid = true;
    } else {
        setValidStatus(firstname);
    }

    if (lastname_value === '') {
        setInvalidStatus(lastname);
        invalid = true;
    } else {
        setValidStatus(lastname);
    }

    if (password_value === '' || password_value.length < 5) {
        setInvalidStatus(password);
        invalid = true;

    } else {
        setValidStatus(password);
    }

    if (email_value === '' || !isEmailValid(email_value)) {
        setInvalidStatus(email);
        invalid = true;
    } else {
        setValidStatus(email);
    }

    if (age_value <= 0 || age_value > 100) {
        setInvalidStatus(age);
        invalid = true;
    } else {
        setValidStatus(age);
    }

    if (roles_value === 0) {
        setInvalidStatus(roles);
        invalid = true;
    } else {
        setValidStatus(roles);
    }

    return invalid;

};

const setInvalidStatus = (element) => {

    element.classList.remove('is-valid')
    element.classList.add('is-invalid');
}

const setValidStatus = (element) => {

    element.classList.remove('is-invalid')
    element.classList.add('is-valid');
}

const isEmailValid = (email) => {
    const regexExp = /\w+@[a-z]{4,10}\.(com|ru)/
    return regexExp.test(String(email).toLowerCase());
}

(function () {
    'use strict';
    window.addEventListener('load', function () {

        newUserForm.addEventListener('submit', ev => {

            if (validateInputs(newUser_firstname, newUser_lastname, newUser_age,
                newUser_password, newUser_email, newUser_roles)) {

                ev.preventDefault();
                ev.stopPropagation();
            }

            newUserForm.add('was-validated');

        }, false);

        editModalForm.addEventListener('submit', ev => {

            if (validateInputs(editUser_firstname, editUser_lastname, editUser_age,
                editUser_password, editUser_email, editUser_roles)) {

                ev.preventDefault();
                ev.stopPropagation();
            }

            editModalForm.add('was-validated');

        }, false);

    }, false);

})();





