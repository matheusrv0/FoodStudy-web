/* -----------------------------------------------------
   FOODSTUDY - SCRIPT GLOBAL
------------------------------------------------------ */

const API_URL = "";

/* -----------------------------------------------------
   HELPERS DE API
------------------------------------------------------ */

async function apiRequest(method, endpoint, body) {
    const options = {
        method,
        headers: {}
    };

    if (body) {
        options.headers["Content-Type"] = "application/json";
        options.body = JSON.stringify(body);
    }

    const resp = await fetch(`${API_URL}${endpoint}`);

    if (!resp.ok) {
        const msg = await resp.text().catch(() => "");
        throw new Error(`Erro ${resp.status} em ${endpoint} - ${msg}`);
    }

    const text = await resp.text();
    return text ? JSON.parse(text) : null;
}

function apiGet(endpoint) {
    return apiRequest("GET", endpoint);
}

function apiPost(endpoint, body) {
    return apiRequest("POST", endpoint, body);
}

/* -----------------------------------------------------
   UTILITÁRIOS
------------------------------------------------------ */

function getUserIdFromQuery() {
    const params = new URLSearchParams(window.location.search);
    return params.get("usuario");
}

function refreshIcons() {
    if (window.lucide) {
        lucide.createIcons();
    }
}

/* -----------------------------------------------------
   PRODUTOS
------------------------------------------------------ */

async function initPaginaProdutos() {
    const tabela = document.getElementById("tabela-produtos");
    if (!tabela) return;

    try {
        const produtos = await apiGet("/produtos");
        tabela.innerHTML = "";

        produtos.forEach((prod) => {
            const preco = Number(prod.preco || 0).toFixed(2);
            tabela.innerHTML += `
                <tr>
                    <td>${prod.id}</td>
                    <td>${prod.nome}</td>
                    <td>${prod.descricao || "-"}</td>
                    <td>R$ ${preco}</td>
                    <td>${prod.tipo || "-"}</td>
                </tr>
            `;
        });
    } catch (err) {
        console.error(err);
        tabela.innerHTML = `
            <tr>
                <td colspan="5">Erro ao carregar produtos.</td>
            </tr>
        `;
    }
}

/* -----------------------------------------------------
   ESTABELECIMENTOS
------------------------------------------------------ */

async function initPaginaEstabelecimentos() {
    const grid = document.getElementById("grid-estabelecimentos");
    if (!grid) return;

    let estabelecimentos = [];
    try {
        estabelecimentos = await apiGet("/estabelecimentos");
    } catch (err) {
        console.error(err);
        grid.innerHTML = "<p>Erro ao carregar estabelecimentos.</p>";
        return;
    }

    // Como a entidade ainda não tem cidade/campus, preenchemos fake
    const cidades = ["Natal/RN", "Mossoró/RN"];
    estabelecimentos = estabelecimentos.map((est, idx) => ({
        ...est,
        cidade: cidades[idx % cidades.length],
        campus: "Campus acadêmico",
        rating: (4 + Math.random()).toFixed(1),
        itens: Math.floor(Math.random() * 40) + 20
    }));

    function renderLista(filtroCidade = "todas") {
        grid.innerHTML = "";

        estabelecimentos
            .filter((e) => {
                if (filtroCidade === "todas") return true;
                if (filtroCidade === "natal") return e.cidade.includes("Natal");
                if (filtroCidade === "mossoro") return e.cidade.includes("Mossoró");
                return true;
            })
            .forEach((est) => {
                grid.innerHTML += `
                    <article class="card-estab">
                        <div class="card-top">
                            <div class="card-icon">
                                <i data-lucide="store"></i>
                            </div>
                            <div class="rating-pill">
                                <i data-lucide="star" class="star"></i>
                                <span>${est.rating}</span>
                            </div>
                        </div>

                        <h3 class="estab-name">${est.nome}</h3>
                        <p class="estab-location">${est.campus}</p>
                        <p class="estab-city">${est.cidade}</p>

                        <div class="card-footer">
                            <span class="items-badge">${est.itens} itens</span>
                            <button class="btn-mini"
                                onclick="window.location.href='/produtos.html?estabelecimento=${est.id}'">
                                Ver cardápio
                            </button>
                        </div>
                    </article>
                `;
            });

        refreshIcons();
    }

    const chips = document.querySelectorAll(".filter-chip");
    chips.forEach((chip) => {
        chip.addEventListener("click", () => {
            chips.forEach((c) => c.classList.remove("active"));
            chip.classList.add("active");
            renderLista(chip.dataset.filter || "todas");
        });
    });

    renderLista("todas");
}

/* -----------------------------------------------------
   PLANOS / ASSINATURAS
------------------------------------------------------ */

async function initPaginaPlanos() {
    const container = document.getElementById("lista-planos");
    if (!container) return;

    try {
        const planos = await apiGet("/assinaturas");
        container.innerHTML = "";

        planos.forEach((plano) => {
            const preco = Number(plano.valor || 0).toFixed(2);
            container.innerHTML += `
                <div class="card-plano">
                    <h3>${plano.tipo}</h3>
                    <p class="plano-preco">R$ ${preco}</p>
                    <p class="plano-beneficios">
                        ${plano.beneficios || "Benefícios em breve."}
                    </p>
                </div>
            `;
        });
    } catch (err) {
        console.error(err);
        container.innerHTML = "<p>Erro ao carregar planos.</p>";
    }
}

/* -----------------------------------------------------
   MINHA CONTA / FOODCASH
------------------------------------------------------ */

async function initPaginaMinhaConta() {
    const userId = getUserIdFromQuery() || 1;
    const spanNome = document.getElementById("conta-nome");
    const spanSaldo = document.getElementById("conta-saldo");
    const botaoAdd = document.getElementById("btn-add-saldo");

    try {
        const usuario = await apiGet(`/usuarios/${userId}`);
        spanNome.textContent = usuario.nome || `Usuário #${userId}`;
        const saldo = usuario.foodCash?.saldo ?? 0;
        spanSaldo.textContent = Number(saldo).toFixed(2);
    } catch (err) {
        console.error(err);
    }

    if (botaoAdd) {
        botaoAdd.addEventListener("click", async () => {
            const valorStr = prompt("Quanto deseja adicionar? (R$)");
            const valor = Number(valorStr);
            if (!valor || valor <= 0) return;

            try {
                await fetch(`${API_URL}/usuarios/${userId}/saldo?valor=${valor}`, {
                    method: "POST"
                });

                const usuario = await apiGet(`/usuarios/${userId}`);
                const saldo = usuario.foodCash?.saldo ?? 0;
                spanSaldo.textContent = Number(saldo).toFixed(2);
                alert("Saldo adicionado com sucesso!");
            } catch (err) {
                console.error(err);
                alert("Erro ao adicionar saldo.");
            }
        });
    }
}

/* -----------------------------------------------------
   PEDIDOS
------------------------------------------------------ */

async function initPaginaPedidos() {
    const tabela = document.getElementById("tabela-pedidos");
    if (!tabela) return;

    const userId = getUserIdFromQuery() || 1;

    try {
        const pedidos = await apiGet(`/usuarios/${userId}/pedidos`);
        tabela.innerHTML = "";

        pedidos.forEach((p) => {
            tabela.innerHTML += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.produto?.nome || "-"}</td>
                    <td>${p.estabelecimento?.nome || "-"}</td>
                    <td>${p.status || "-"}</td>
                    <td>${p.horarioRetirada || "-"}</td>
                </tr>
            `;
        });
    } catch (err) {
        console.error(err);
        tabela.innerHTML = `
            <tr>
                <td colspan="5">Erro ao carregar pedidos.</td>
            </tr>
        `;
    }
}

/* -----------------------------------------------------
   CADASTRO USUÁRIO
------------------------------------------------------ */

function initPaginaCadastroUsuario() {
    const form = document.getElementById("form-cadastro-usuario");
    if (!form) return;

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const usuario = {
            nome: form.nome.value,
            cpf: form.cpf.value,
            tipo: form.tipo.value
        };

        try {
            await apiPost("/usuarios", usuario);
            alert("Usuário cadastrado com sucesso!");
            window.location.href = "/index.html";
        } catch (err) {
            console.error(err);
            alert("Erro ao cadastrar usuário.");
        }
    });
}

/* -----------------------------------------------------
   ROTEADOR POR PÁGINA
------------------------------------------------------ */

document.addEventListener("DOMContentLoaded", () => {
    const page = document.body.dataset.page;

    switch (page) {
        case "produtos":
            initPaginaProdutos();
            break;
        case "estabelecimentos":
            initPaginaEstabelecimentos();
            break;
        case "planos":
            initPaginaPlanos();
            break;
        case "conta":
            initPaginaMinhaConta();
            break;
        case "pedidos":
            initPaginaPedidos();
            break;
        case "cadastro-usuario":
            initPaginaCadastroUsuario();
            break;
        default:
        // landing não precisa JS
    }

    refreshIcons();
});
