import { Chart, registerables } from "chart.js/auto";
import type { ChartConfiguration } from "chart.js/auto";
document.addEventListener('DOMContentLoaded',() => {

  Chart.register(...registerables);

  const candidatosString = localStorage.getItem("candidatos");
  let candidatos = [];
  if (candidatosString) {
    candidatos = JSON.parse(candidatosString);
  }

  const listaCandidatos = document.getElementById("lista-candidatos") as HTMLElement;

  candidatos.forEach((candidato: any) => {
    const candidatoElement = document.createElement("div");
    candidatoElement.innerHTML = `
    <h3>${candidato.descricao}</h3>
    <p><strong>Competências:</strong> ${candidato.competencias.join(", ")}</p>
    <p><strong>CEP:</strong> ${candidato.cep}</p>
    <p><strong>Estado:</strong> ${candidato.estado}</p>
  `;
    listaCandidatos.appendChild(candidatoElement);
  });

  const contagemCompetencias = new Map<string, number>();
  for (const candidato of candidatos) {
    for (const competencia of candidato.competencias) {
      if (!contagemCompetencias.has(competencia)) {
        contagemCompetencias.set(competencia, 1);
      } else {
        contagemCompetencias.set(competencia, contagemCompetencias.get(competencia)! + 1);
      }
    }
  }

  const competencias = Array.from(contagemCompetencias.keys());
  const quantidades = Array.from(contagemCompetencias.values());

  const graficoBarras = document.getElementById("grafico-barras") as HTMLCanvasElement;

  const config: ChartConfiguration<"bar"> = {
    type: "bar",
    data: {
      labels: competencias,
      datasets: [{
        label: "Quantidade de usuários",
        data: quantidades,
        backgroundColor: "rgba(54, 162, 235, 0.5)"
      }]
    },
    options: {
      scales: {
        y: {
          type: 'linear',
          beginAtZero: true,
        }
      }
    }
  };

  new Chart(graficoBarras, config);

});
