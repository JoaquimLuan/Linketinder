import Candidato from "./candidato";
document.addEventListener('DOMContentLoaded',() => {

let nomeCandidato = document.getElementById('nome') as HTMLInputElement;
let emailCandidato = document.getElementById('email') as HTMLInputElement;
let cpfCandidato = document.getElementById('cpf') as HTMLInputElement;
let cepCandidato  = document.getElementById('cep') as HTMLInputElement;
let estadoCandidato  = document.getElementById('estado') as HTMLInputElement;
let descricaoCandidato  = document.getElementById('descricao') as HTMLInputElement;

let botaoSalvaCandidato = document.getElementById('button-adicionar') as HTMLElement;

let linguagensSelecionadas: string[] = [];

function armazenarLinguagens() {
  let checkboxes = document.getElementsByName('linguagem');
  for (let i = 0; i < checkboxes.length; i++) {
    // verificar se o elemento é um input do tipo "checkbox"
    if ((checkboxes[i] as HTMLInputElement).type === 'checkbox' && (checkboxes[i] as HTMLInputElement).checked) {
      linguagensSelecionadas.push((checkboxes[i] as HTMLInputElement).value);
    }
  }
  console.log(linguagensSelecionadas);
  return false;
}


//Evento
botaoSalvaCandidato.addEventListener("onclick", () => {


  armazenarLinguagens();

  let newCandidato = new Candidato();

  const regexNome = /^[a-zA-Z \.]+$/;
  if (regexNome.test(nomeCandidato.value.trim())) {
    newCandidato.nome = nomeCandidato.value;
  } else {
    alert('O campo nome só pode receber letras e pontos.');
    return;
  }

  const regexEmail = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (regexEmail.test(emailCandidato.value)) {
    newCandidato.email = emailCandidato.value;
  } else {
    alert('O campo email deve conter um email válido.');
    return;
  }

  const regexCpf = /^\d{11}$/;
  const cpf = parseInt(cpfCandidato.value, 10);
  if (regexCpf.test(cpf.toString())) {
    newCandidato.cpfCandidato = cpf;
  } else {
    alert('O campo cpf deve conter 11 números.');
    return;
  }

  const regexCEP = /^\d{8}$/;
  const cep = parseInt(cepCandidato.value, 10);
  if (regexCEP.test(cep.toString())) {
    newCandidato.cep = cep;
  } else {
    alert('O campo cep deve conter 8 números.');
    return;
  }

  if (estadoCandidato.value.trim() !== "" && descricaoCandidato.value.trim() !== "") {
    newCandidato.estado = estadoCandidato.value;
    newCandidato.descricao = descricaoCandidato.value;
  } else {
    alert('Por favor, preencha todos os campos antes de enviar o formulário!');
    return;
  }

  newCandidato.competencias = linguagensSelecionadas;

  console.log(newCandidato);

  saveCandidato(newCandidato);
});

//Função

function saveCandidato(empresa: Candidato) {
  let candidatos: Candidato[] = [];

  const candidatosString = localStorage.getItem("candidatos");
  if (candidatosString) {
    try {
      candidatos = JSON.parse(candidatosString);
    } catch (error) {
      console.error(`Não foi possível analisar o JSON armazenado em 'candidatos': ${error}`);
    }
  }

  candidatos.push(empresa);

  localStorage.setItem("candidatos", JSON.stringify(candidatos));
}
});
