async function showDeleteModal(userId) {

    const url = 'http://localhost:8080/admin/api/users/' + userId;
    const response = await fetch(url);
    const user = await response.json();

    $('#deleteUserModal').on('shown.bs.modal', function (event) {

        const modal = $(this)

        modal.find("#deleteId").val(user.id);
        modal.find("#deleteFirstname").val(user.firstname);
        modal.find("#deleteAge").val(user.age);
        modal.find("#deleteEmail").val(user.email);
        modal.find("#deleteLastname").val(user.lastname);
        modal.find("#deleteRoles").val(user.roles[0].id).attr('selected', 'selected');
    });

}

$('#deleteModalButton').on('click', function (event) {

    event.preventDefault();

    let modal = $('#deleteUserModal')

    const url = 'http://localhost:8080/admin/api/users/' + Number(modal.find("#deleteId").val());

    $.ajax({
        url: url,
        type: 'DELETE',
        success: () => {
            loadIntoTable();
            console.log("deleted");
        },
        error: (err) => {
            alert(err);
        }
    })

    modal.modal('hide');

});