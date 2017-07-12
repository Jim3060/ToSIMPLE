$(function () {

    $("#commit").click(function (e) {
        var username = $("input[name='username']").val();
        jQuery.ajax({
            url: 'fetchRSA',
            processData: true,
            dataType: "json",
            data: {},
            success: function (data) {
                var encrypt = new JSEncrypt();
                encrypt.setPublicKey(data.publicKey);
                jQuery.ajax({
                    url: 'login',
                    processData: true,
                    dataType: "text",
                    data: {
                        passwordSECURE: encrypt.encrypt(username),
                    },
                    success: function (data) {
                        alert(data);
                    }

                });
            }

        });


    });
});