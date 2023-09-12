import Usuarios from '../usuario'
import Vagas from './vagas';

export default class Empresa extends Usuarios {

  cnpjEmpresa?: number;

  getCnpj(): string {
    const cpfString = this.cnpjEmpresa?.toString();
    const cpfFormatted = cpfString?.substring(0, 2) + '.' +
      cpfString?.substring(2, 5) + '.' +
      cpfString?.substring(5, 8) + '/' +
      cpfString?.substring(8, 12) + '-' +
      cpfString?.substring(12, 14);
    return cpfFormatted;
  }

  vagas?: Vagas[];
}
