
$("#editUserModal").on("show.bs.modal", function(e) {
        let button = $(e.relatedTarget)
        let href = button.attr('href')


        $.get(href, function (userForEdit, status) {

            $("#editId").val(userForEdit.id);
            $("#editFirstname").val(userForEdit.firstname);
            $("#editAge").val(userForEdit.age);
            $("#editEmail").val(userForEdit.email);
            $("#editLastname").val(userForEdit.lastname);
            $("#editRoles").val(userForEdit.roles).attr('selected', 'selected')
        });

        $("#editModalForm").attr('action', window.location.href+'/admin/update/');
        $("#saveModalButton").attr('href', window.location.href+'/admin/update/');
   });

$('#editModalForm').on("submit",function() {

    let modal = $('#editUserModal')
    let user = {
        Id: modal.find('#editId').val(),
        firstname: modal.find('#editFirstname').val(),
        lastname: modal.find('#ditLastname').val(),
        email: modal.find('#editEmail').val(),
        age: modal.find('#editAge').val(),
        roles: modal.find('#editRoles').val(),
        password: modal.find('#editPassword').val()
    };


    $.ajax({
        url: '/admin/api/update',
        type: 'POST',
        data: JSON.stringify(user),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: () => {
            location.reload()
        },
        error: (err) => {
            alert(err);
        }
    });

});