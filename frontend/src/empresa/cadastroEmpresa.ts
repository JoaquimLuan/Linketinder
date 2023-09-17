import Empresa from "./empresa";
export class CadastroEmpresa {
  // ...
}
document.addEventListener('DOMContentLoaded',() => {

  const nomeEmpresa = document.getElementById('nome') as HTMLInputElement;
  const emailEmpresa = document.getElementById('email') as HTMLInputElement;
  const cnpjEmpresa = document.getElementById('cnpj') as HTMLInputElement;
  const cepEmpresa = document.getElementById('cep') as HTMLInputElement;
  const estadoEmpresa = document.getElementById('estado') as HTMLInputElement;
  const descricaoEmpresa = document.getElementById('descricao') as HTMLInputElement;

  let linguagensSelecionadas: string[] = [];

  function armazenarLinguagens() {
    let checkboxes = document.getElementsByName('linguagem');
    for (let i = 0; i < checkboxes.length; i++) {

      if ((checkboxes[i] as HTMLInputElement).type === 'checkbox' && (checkboxes[i] as HTMLInputElement).checked) {
        linguagensSelecionadas.push((checkboxes[i] as HTMLInputElement).value);
      }
    }
    console.log(linguagensSelecionadas);
    return false;
  }

  let botaoSalvaEmpresa = document.getElementById('button-adicionar') as HTMLElement;

  // Evento
  botaoSalvaEmpresa.addEventListener("click", () => {
    armazenarLinguagens();

    let newEmpresa = new Empresa();

    const regexNome = /^[a-zA-Z \.]+$/;
    if (regexNome.test(nomeEmpresa.value.trim())) {
      newEmpresa.nome = nomeEmpresa.value;
    } else {
      alert('O campo nome só pode receber letras e pontos.');
      return;
    }

    const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (regexEmail.test(emailEmpresa.value)) {
      newEmpresa.email = emailEmpresa.value;
    } else {
      alert('O campo email deve conter um email válido.');
      return;
    }

    const regexCNPJ = /^\d{14}$/;
    const cnpj = parseInt(cnpjEmpresa.value, 10);
    if (regexCNPJ.test(cnpj.toString())) {
      newEmpresa.cnpjEmpresa = cnpj;
    } else {
      alert('O campo cnpj deve conter 14 números.');
      return;
    }

    const regexCEP = /^\d{8}$/;
    const cep = parseInt(cepEmpresa.value, 10);
    if (regexCEP.test(cep.toString())) {
      newEmpresa.cep = cep;
    } else {
      alert('O campo cep deve conter 8 números.');
      return;
    }

    if (estadoEmpresa.value.trim() !== "" && descricaoEmpresa.value.trim() !== "") {
      newEmpresa.estado = estadoEmpresa.value;
      newEmpresa.descricao = descricaoEmpresa.value;
    } else {
      alert('Por favor, preencha todos os campos antes de enviar o formulário!');
      return;
    }

    newEmpresa.competencias = linguagensSelecionadas;
    newEmpresa.vagas = [];

    saveEmpresa(newEmpresa);

    alert("Empresa cadastrada com sucesso.");
  });

  // Função
  function saveEmpresa(empresa: Empresa) {
    let empresas: Empresa[] = [];

    const empresasString = localStorage.getItem("empresas");
    if (empresasString) {
      try {
        empresas = JSON.parse(empresasString);
      } catch (error) {
        console.error(`Não foi possível analisar o JSON armazenado em 'empresas': ${error}`);
      }
    }

    empresas.push(empresa);

    localStorage.setItem("empresas", JSON.stringify(empresas));
  }
});
