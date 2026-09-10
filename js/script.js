document.addEventListener("DOMContentLoaded", function () {

    const formLogin = document.getElementById("formLogin");

    if (formLogin) {

        formLogin.addEventListener("submit", function (event) {

            event.preventDefault();

            const usuario = document.getElementById("usuario").value;
            const senha = document.getElementById("senha").value;

            if (usuario === "" || senha === "") {

                alert("Preencha o usuário e a senha.");

                return;
            }

            alert("Login realizado com sucesso!");

            window.location.href = "dashboard.html";

        });

    }

});