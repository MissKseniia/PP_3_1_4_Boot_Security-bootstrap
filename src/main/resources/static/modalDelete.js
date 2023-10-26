$('document').ready(function () {
    $('.table .deleteButton').on('click', function (event) {
        event.preventDefault();

        let href = $(this).attr('href')

        $.get(href, function (userForDelete, status) {

            $(".deleteForm #deleteId").val(userForDelete.id);
            $(".deleteForm #deleteFirstname").val(userForDelete.firstname);
            $(".deleteForm #deleteAge").val(userForDelete.age);
            $(".deleteForm #deleteEmail").val(userForDelete.email);
            $(".deleteForm #deleteLastname").val(userForDelete.lastname);

            $("#deleteUserModal #deleteModalButton").attr('href', '/admin/remove/' + userForDelete.id);
        });

        $('#deleteUserModal').modal();
    });
});