import Usuarios from '../usuario'

export default class Candidato extends Usuarios {

  cpfCandidato?: number;

  getCpf(): string {
    const cpfString = this.cpfCandidato?.toString();
    const cpfFormatted = cpfString?.substring(0, 3) + '.' +
      cpfString?.substring(3, 6) + '.' +
      cpfString?.substring(6, 9) + '-' +
      cpfString?.substring(9, 11);
    return cpfFormatted;
  }
}
