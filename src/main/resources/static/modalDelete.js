
$("#deleteUserModal").on("show.bs.modal", function(e) {
    let button = $(e.relatedTarget)
    let href = button.attr('href')
    let id

    $.get(href, function (userForEdit, status) {

        id = userForEdit.id;
        $("#deleteId").val(userForEdit.id);
        $("#deleteFirstname").val(userForEdit.firstname);
        $("#deleteAge").val(userForEdit.age);
        $("#deleteEmail").val(userForEdit.email);
        $("#deleteLastname").val(userForEdit.lastname);

    });

    $("#deleteModalButton").attr('onclick', window.location.href+'/admin/remove/'+ id);
});