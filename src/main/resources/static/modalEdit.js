$('document').ready(function () {

    $('.table .editingButton').on('click', function (event) {
        event.preventDefault();
        let href = $(this).attr('href')

        $.get(href, function (userForEdit, status) {

            $(".editForm #editId").val(userForEdit.id);
            $(".editForm #editFirstname").val(userForEdit.firstname);
            $(".editForm #editAge").val(userForEdit.age);
            $(".editForm #editEmail").val(userForEdit.email);
            $(".editForm #editLastname").val(userForEdit.lastname);
        });
        $('.editForm #editUserModal').modal();

    });
});
