import Empresa from "../empresa/empresa";

document.addEventListener('DOMContentLoaded',() => {

  const empresasJSON: string | null = localStorage.getItem('empresas');
  const empresas: Empresa[] = empresasJSON ? JSON.parse(empresasJSON) : [];


  if (empresas) {

    const vagasDiv: HTMLDivElement = document.getElementById('vagas') as HTMLDivElement;

    empresas.forEach((empresa) => {
      const vagas = empresa.vagas ?? [];

      if (vagas.length > 0) {

        const listaVagas: HTMLUListElement = document.createElement('ul');


        vagas.forEach((vaga) => {

          const itemVaga: HTMLLIElement = document.createElement('li');

          const nomeVaga: HTMLHeadingElement = document.createElement('h3');
          nomeVaga.textContent = `Nome da vaga: ${vaga.nome}`;
          itemVaga.appendChild(nomeVaga);

          const competenciasVaga: HTMLParagraphElement = document.createElement('p');
          competenciasVaga.textContent = `Competências: ${vaga.competencias?.join(', ')}`;
          itemVaga.appendChild(competenciasVaga);

          const descricaoVaga: HTMLParagraphElement = document.createElement('p');
          descricaoVaga.textContent = `Descrição: ${vaga.descricao}`;
          itemVaga.appendChild(descricaoVaga);

          const salarioVaga: HTMLParagraphElement = document.createElement('p');
          salarioVaga.textContent = `Salário: R$ ${vaga.salario}`;
          itemVaga.appendChild(salarioVaga);

          listaVagas.appendChild(itemVaga);
        });

        vagasDiv.appendChild(listaVagas);
      }
    });
  }
});
