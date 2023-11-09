const validateInputs = (form, id, firstname, lastname, age, password, email, roles) => {

    const firstname_value = firstname.value.trim();
    const lastname_value = lastname.value.trim();
    const password_value = password.value.trim();
    const email_value = email.value.trim();
    const id_value = Number(id.value);

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
        $(".emailErrorMessage").html("Please provide a valid email (example@mail.com)");
        invalid = true;
    } else {
        if (form.id === 'newUserForm') {
            isEmailExisted(email_value)
                .then(value => {
                    if (value === true) {
                        setInvalidEmailStatus(email);
                        invalid = true;
                    } else {
                        setValidStatus(email);
                    }

                })

        }
        if (form.id === 'editModalForm') {
            isEmailAvailableForUpdate(email_value, id_value)
                .then(value => {
                    if (value === false) {
                        setInvalidEmailStatus(email);
                        invalid = true;
                    } else {
                        setValidStatus(email);
                    }
                })

        }

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
    form.classList.add('was-validated');
    return invalid;

}

const setInvalidEmailStatus = (element) => {

    $(".emailErrorMessage").html("This email has already existed");

    element.classList.remove('is-valid')
    element.classList.add('is-invalid');
}

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

const isEmailExisted = async (userEmail) => {

    const url = 'http://localhost:8080/admin/api/users/emails'
    const response = await fetch(url);
    const emails = await response.json();

    let exists = false;

    for (let email of emails) {
        if (email === userEmail) {
            console.log("exists");
            exists = true;
        }
    }
    return exists;
}

const isEmailAvailableForUpdate = async (userEmail, userId) => {

    const url = 'http://localhost:8080/admin/api/users/?email=' + userEmail
    const response = await fetch(url);
    const relatedUser = await response.json();

    return relatedUser.id === userId;

}

