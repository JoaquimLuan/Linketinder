import Empresa from "./empresa";
import Vagas from "./vagas";

document.addEventListener('DOMContentLoaded',() => {

  const empresas: Empresa[] = JSON.parse(localStorage.getItem('empresas') || "[]");

  const cnpjInput: HTMLInputElement = document.getElementById('cnpj') as HTMLInputElement;
  const nomeInput: HTMLInputElement = document.getElementById('nome') as HTMLInputElement;
  const descricaoInput: HTMLInputElement = document.getElementById('descricao') as HTMLInputElement;
  const salarioInput: HTMLInputElement = document.getElementById('salario') as HTMLInputElement;
  const salvarVagaButton: HTMLButtonElement = document.getElementById('button-adicionar') as HTMLButtonElement;

  let linguagensSelecionadas: string[] = [];

  function armazenarLinguagens() {
    let checkboxes = document.getElementsByName('linguagem');
    for (let i = 0; i < checkboxes.length; i++) {
      if ((checkboxes[i] as HTMLInputElement).type === 'checkbox' && (checkboxes[i] as HTMLInputElement).checked) {
        linguagensSelecionadas.push((checkboxes[i] as HTMLInputElement).value);
      }
    }
    console.log(linguagensSelecionadas);
  }

  salvarVagaButton.addEventListener('click', () => {
    armazenarLinguagens();

    const cnpj: number = parseInt(cnpjInput.value);

    if (isNaN(cnpj) || nomeInput.value.trim() === "" || descricaoInput.value.trim() === "" || salarioInput.value.trim() === "") {
      alert('Por favor, preencha todos os campos corretamente antes de enviar o formulário!');
      return;
    }

    const empresa: Empresa | undefined = empresas.find((e) => e.cnpjEmpresa === cnpj);

    if (empresa && empresa.vagas) {
      const novaVaga: Vagas = new Vagas();
      novaVaga.competencias = linguagensSelecionadas;
      novaVaga.nome = nomeInput.value;
      novaVaga.salario = salarioInput.value;
      novaVaga.descricao = descricaoInput.value;

      empresa.vagas.push(novaVaga);

      localStorage.setItem('empresas', JSON.stringify(empresas));
      alert('Vaga salva com sucesso!');
    } else {
      alert('Empresa não encontrada!');
    }
  })
});
