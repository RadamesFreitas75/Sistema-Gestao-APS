document.addEventListener("DOMContentLoaded", function () {

    /* =====================================================
       CONFIGURAÇÃO DA API
    ===================================================== */

    const API_URL = "http://localhost:8080/api";


    /* =====================================================
       LOGIN
    ===================================================== */

    const formLogin = document.getElementById("formLogin");

    if (formLogin) {

        formLogin.addEventListener("submit", async function (event) {

            event.preventDefault();

            const usuario =
                document.getElementById("usuario").value.trim();

            const senha =
                document.getElementById("senha").value.trim();


            if (usuario === "" || senha === "") {

                alert("Preencha o usuário e a senha.");

                return;
            }


            try {

                const resposta = await fetch(
                    `${API_URL}/usuarios/login`,
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify({
                            email: usuario,
                            senha: senha
                        })
                    }
                );


                if (resposta.ok) {

                    const usuarioLogado =
                        await resposta.json();


                    localStorage.setItem(
                        "usuarioLogado",
                        JSON.stringify(usuarioLogado)
                    );


                    alert(
                        "Login realizado com sucesso!"
                    );


                    window.location.href =
                        "dashboard.html";


                } else if (resposta.status === 401) {

                    alert(
                        "Usuário ou senha inválidos."
                    );


                } else {

                    alert(
                        "Não foi possível realizar o login."
                    );

                }


            } catch (erro) {

                console.error(
                    "Erro ao conectar com a API:",
                    erro
                );


                alert(
                    "Não foi possível conectar ao servidor.\n\n" +
                    "Verifique se a API está funcionando."
                );

            }

        });

    }


    /* =====================================================
       USUÁRIO LOGADO
    ===================================================== */

    const nomeUsuario =
        document.getElementById("nomeUsuario");


    if (nomeUsuario) {

        const usuarioSalvo =
            localStorage.getItem("usuarioLogado");


        if (usuarioSalvo) {

            try {

                const usuario =
                    JSON.parse(usuarioSalvo);


                if (usuario.nome) {

                    nomeUsuario.textContent =
                        usuario.nome;

                }

            } catch (erro) {

                console.error(
                    "Erro ao ler usuário:",
                    erro
                );

            }

        }

    }


    /* =====================================================
       LOGOUT
    ===================================================== */

    const linkSair =
        document.getElementById("linkSair");


    if (linkSair) {

        linkSair.addEventListener(
            "click",
            function () {

                localStorage.removeItem(
                    "usuarioLogado"
                );

            }
        );

    }


    /* =====================================================
       PACIENTES
    ===================================================== */

    const formPaciente =
        document.getElementById("formPaciente");


    /*
       Se a tela de pacientes estiver aberta,
       carregar os pacientes da API.
    */

    if (document.getElementById("listaPacientes")) {

        carregarPacientes();

    }


    /* =====================================================
       CADASTRAR PACIENTE
    ===================================================== */

    if (formPaciente) {

        formPaciente.addEventListener(
            "submit",
            async function (event) {

                event.preventDefault();


                const nome =
                    document
                        .getElementById("nomePaciente")
                        .value
                        .trim();


                const cpf =
                    document
                        .getElementById("cpfPaciente")
                        .value
                        .trim();


                const dataNascimento =
                    document
                        .getElementById("dataNascimento")
                        .value;


                const sexo =
                    document
                        .getElementById("sexoPaciente")
                        .value;


                const telefone =
                    document
                        .getElementById("telefonePaciente")
                        .value
                        .trim();


                const endereco =
                    document
                        .getElementById("enderecoPaciente")
                        .value
                        .trim();


                const email =
                    document
                        .getElementById("emailPaciente")
                        .value
                        .trim();


                const paciente = {

                    nome: nome,

                    cpf: cpf,

                    dataNascimento: dataNascimento,

                    sexo: sexo,

                    telefone: telefone,

                    endereco: endereco,

                    email: email

                };


                try {

                    const resposta =
                        await fetch(
                            `${API_URL}/pacientes`,
                            {

                                method: "POST",

                                headers: {

                                    "Content-Type":
                                        "application/json"

                                },

                                body:
                                    JSON.stringify(
                                        paciente
                                    )

                            }
                        );


                    if (!resposta.ok) {

                        throw new Error(
                            "Erro ao cadastrar paciente."
                        );

                    }


                    const pacienteSalvo =
                        await resposta.json();


                    console.log(
                        "Paciente cadastrado:",
                        pacienteSalvo
                    );


                    alert(
                        "Paciente cadastrado com sucesso!"
                    );


                    formPaciente.reset();


                    fecharFormularioPaciente();


                    /*
                       Atualiza a tabela
                       buscando os dados novamente da API.
                    */

                    carregarPacientes();


                } catch (erro) {

                    console.error(
                        "Erro:",
                        erro
                    );


                    alert(
                        "Não foi possível cadastrar o paciente.\n\n" +
                        "Verifique se a API está funcionando."
                    );

                }

            }
        );

    }


    /* =====================================================
       CARREGAR PACIENTES DA API
    ===================================================== */

    async function carregarPacientes() {

        const tabela =
            document.getElementById(
                "listaPacientes"
            );


        if (!tabela) {

            return;

        }


        try {

            const resposta =
                await fetch(
                    `${API_URL}/pacientes`
                );


            if (!resposta.ok) {

                throw new Error(
                    "Erro ao buscar pacientes."
                );

            }


            const pacientes =
                await resposta.json();


            /*
               Limpa a tabela antes
               de inserir os dados da API.
            */

            tabela.innerHTML = "";


            /*
               Se não houver pacientes.
            */

            if (pacientes.length === 0) {

                tabela.innerHTML = `

                    <tr>

                        <td
                            colspan="6"
                            style="text-align: center;"
                        >
                            Nenhum paciente cadastrado.
                        </td>

                    </tr>

                `;

                return;

            }


            /*
               Cria uma linha para cada paciente.
            */

            pacientes.forEach(
                function (paciente) {

                    const linha =
                        document.createElement("tr");


                    /*
                       Guarda o ID do paciente
                       na própria linha.
                    */

                    linha.dataset.id =
                        paciente.id;


                    const dataFormatada =
                        formatarData(
                            paciente.dataNascimento
                        );


                    linha.innerHTML = `

                        <td>
                            ${escaparHTML(
                                paciente.nome || ""
                            )}
                        </td>

                        <td>
                            ${escaparHTML(
                                paciente.cpf || ""
                            )}
                        </td>

                        <td>
                            ${dataFormatada}
                        </td>

                        <td>
                            ${escaparHTML(
                                paciente.sexo || ""
                            )}
                        </td>

                        <td>
                            ${escaparHTML(
                                paciente.telefone || ""
                            )}
                        </td>

                        <td>

                            <button
                                class="botao-acao"
                                onclick="visualizarPaciente(${paciente.id})"
                                title="Visualizar"
                            >
                                👁️
                            </button>


                            <button
                                class="botao-acao botao-excluir"
                                onclick="excluirPaciente(${paciente.id})"
                                title="Excluir"
                            >
                                🗑️
                            </button>

                        </td>

                    `;


                    tabela.appendChild(linha);

                }
            );


        } catch (erro) {

            console.error(
                "Erro ao carregar pacientes:",
                erro
            );


            tabela.innerHTML = `

                <tr>

                    <td
                        colspan="6"
                        style="text-align: center;"
                    >
                        Não foi possível carregar os pacientes.
                    </td>

                </tr>

            `;

        }

    }


    /* =====================================================
       FORMATAR DATA
    ===================================================== */

    function formatarData(data) {

        if (!data) {

            return "";

        }


        /*
           Se a API retornar:
           1985-08-10

           será exibido:
           10/08/1985
        */

        const partes =
            data.split("-");


        if (partes.length === 3) {

            return (
                partes[2] +
                "/" +
                partes[1] +
                "/" +
                partes[0]
            );

        }


        return data;

    }


    /* =====================================================
       ESCAPAR HTML
    ===================================================== */

    function escaparHTML(texto) {

        return String(texto)

            .replace(/&/g, "&amp;")

            .replace(/</g, "&lt;")

            .replace(/>/g, "&gt;")

            .replace(/"/g, "&quot;")

            .replace(/'/g, "&#039;");

    }


    /* =====================================================
       ABRIR FORMULÁRIO DE PACIENTE
    ===================================================== */

    window.abrirFormularioPaciente =
        function () {

            const formulario =
                document.getElementById(
                    "formularioPaciente"
                );


            if (formulario) {

                formulario.style.display =
                    "block";


                const campoNome =
                    document.getElementById(
                        "nomePaciente"
                    );


                if (campoNome) {

                    campoNome.focus();

                }

            }

        };


    /* =====================================================
       FECHAR FORMULÁRIO DE PACIENTE
    ===================================================== */

    window.fecharFormularioPaciente =
        function () {

            const formulario =
                document.getElementById(
                    "formularioPaciente"
                );


            if (formulario) {

                formulario.style.display =
                    "none";

            }

        };


    /* =====================================================
       VISUALIZAR PACIENTE
    ===================================================== */

    window.visualizarPaciente =
        async function (id) {

            try {

                const resposta =
                    await fetch(
                        `${API_URL}/pacientes/${id}`
                    );


                if (!resposta.ok) {

                    throw new Error(
                        "Paciente não encontrado."
                    );

                }


                const paciente =
                    await resposta.json();


                alert(

                    "DADOS DO PACIENTE\n\n" +

                    "Nome: " +
                    (paciente.nome || "") +

                    "\nCPF: " +
                    (paciente.cpf || "") +

                    "\nData de nascimento: " +
                    formatarData(
                        paciente.dataNascimento
                    ) +

                    "\nSexo: " +
                    (paciente.sexo || "") +

                    "\nTelefone: " +
                    (paciente.telefone || "") +

                    "\nEndereço: " +
                    (paciente.endereco || "") +

                    "\nE-mail: " +
                    (paciente.email || "")

                );


            } catch (erro) {

                console.error(
                    erro
                );


                alert(
                    "Não foi possível visualizar o paciente."
                );

            }

        };


    /* =====================================================
       EXCLUIR PACIENTE
    ===================================================== */

    window.excluirPaciente =
        async function (id) {

            const confirmar =
                confirm(
                    "Deseja realmente excluir este paciente?"
                );


            if (!confirmar) {

                return;

            }


            try {

                const resposta =
                    await fetch(
                        `${API_URL}/pacientes/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                if (!resposta.ok) {

                    throw new Error(
                        "Erro ao excluir paciente."
                    );

                }


                alert(
                    "Paciente removido com sucesso!"
                );


                /*
                   Atualiza a tabela.
                */

                carregarPacientes();


            } catch (erro) {

                console.error(
                    "Erro:",
                    erro
                );


                alert(
                    "Não foi possível excluir o paciente."
                );

            }

        };


    /* =====================================================
       BUSCAR PACIENTE
    ===================================================== */

    window.buscarPaciente =
        function () {

            const campo =
                document.getElementById(
                    "buscarPaciente"
                );


            if (!campo) {

                return;

            }


            const filtro =
                campo.value
                    .toLowerCase()
                    .trim();


            const linhas =
                document.querySelectorAll(
                    "#listaPacientes tr"
                );


            linhas.forEach(
                function (linha) {

                    const texto =
                        linha.textContent
                            .toLowerCase();


                    if (
                        texto.includes(filtro)
                    ) {

                        linha.style.display =
                            "";

                    } else {

                        linha.style.display =
                            "none";

                    }

                }
            );

        };


    /* =====================================================
       FUNÇÕES DE AGENDAMENTOS
       Mantidas para não quebrar a tela atual.
    ===================================================== */

    const formAgendamento =
        document.getElementById(
            "formAgendamento"
        );


    if (formAgendamento) {

        formAgendamento.addEventListener(
            "submit",
            function (event) {

                event.preventDefault();

                alert(
                    "Agendamento cadastrado com sucesso!"
                );

                formAgendamento.reset();

                if (
                    typeof fecharFormularioAgendamento ===
                    "function"
                ) {

                    fecharFormularioAgendamento();

                }

            }
        );

    }


    window.abrirFormularioAgendamento =
        function () {

            const formulario =
                document.getElementById(
                    "formularioAgendamento"
                );


            if (formulario) {

                formulario.style.display =
                    "block";

            }

        };


    window.fecharFormularioAgendamento =
        function () {

            const formulario =
                document.getElementById(
                    "formularioAgendamento"
                );


            if (formulario) {

                formulario.style.display =
                    "none";

            }

        };


    window.visualizarAgendamento =
        function (nome) {

            alert(
                "Agendamento do paciente: " +
                nome
            );

        };


    window.excluirAgendamento =
        function (botao) {

            const confirmar =
                confirm(
                    "Deseja realmente excluir este agendamento?"
                );


            if (confirmar) {

                const linha =
                    botao.closest("tr");


                if (linha) {

                    linha.remove();

                }


                alert(
                    "Agendamento removido com sucesso!"
                );

            }

        };


    window.buscarAgendamento =
        function () {

            const campo =
                document.getElementById(
                    "buscarAgendamento"
                );


            if (!campo) {

                return;

            }


            const filtro =
                campo.value
                    .toLowerCase()
                    .trim();


            const linhas =
                document.querySelectorAll(
                    "#listaAgendamentos tr"
                );


            linhas.forEach(
                function (linha) {

                    const texto =
                        linha.textContent
                            .toLowerCase();


                    linha.style.display =
                        texto.includes(filtro)
                            ? ""
                            : "none";

                }
            );

        };


    /* =====================================================
       FUNÇÕES DE UNIDADES
       Mantidas para não quebrar a tela atual.
    ===================================================== */

    const formUnidade =
        document.getElementById(
            "formUnidade"
        );


    if (formUnidade) {

        formUnidade.addEventListener(
            "submit",
            function (event) {

                event.preventDefault();

                alert(
                    "Unidade cadastrada com sucesso!"
                );

                formUnidade.reset();

                if (
                    typeof fecharFormularioUnidade ===
                    "function"
                ) {

                    fecharFormularioUnidade();

                }

            }
        );

    }


    window.abrirFormularioUnidade =
        function () {

            const formulario =
                document.getElementById(
                    "formularioUnidade"
                );


            if (formulario) {

                formulario.style.display =
                    "block";

            }

        };


    window.fecharFormularioUnidade =
        function () {

            const formulario =
                document.getElementById(
                    "formularioUnidade"
                );


            if (formulario) {

                formulario.style.display =
                    "none";

            }

        };


    window.visualizarUnidade =
        function (nome) {

            alert(
                "Unidade selecionada: " +
                nome
            );

        };


    window.excluirUnidade =
        function (botao) {

            const confirmar =
                confirm(
                    "Deseja realmente excluir esta unidade?"
                );


            if (confirmar) {

                const linha =
                    botao.closest("tr");


                if (linha) {

                    linha.remove();

                }


                alert(
                    "Unidade removida com sucesso!"
                );

            }

        };


    window.buscarUnidade =
        function () {

            const campo =
                document.getElementById(
                    "buscarUnidade"
                );


            if (!campo) {

                return;

            }


            const filtro =
                campo.value
                    .toLowerCase()
                    .trim();


            const linhas =
                document.querySelectorAll(
                    "#listaUnidades tr"
                );


            linhas.forEach(
                function (linha) {

                    const texto =
                        linha.textContent
                            .toLowerCase();


                    linha.style.display =
                        texto.includes(filtro)
                            ? ""
                            : "none";

                }
            );

        };


    /* =====================================================
       INDICADORES
    ===================================================== */

    window.filtrarIndicadores =
        function () {

            const periodo =
                document.getElementById(
                    "periodoIndicador"
                );


            const unidade =
                document.getElementById(
                    "unidadeIndicador"
                );


            if (!periodo || !unidade) {

                return;

            }


            alert(

                "Filtros aplicados!\n\n" +

                "Período: " +
                periodo.value +

                "\nUnidade: " +
                unidade.value

            );

        };


});