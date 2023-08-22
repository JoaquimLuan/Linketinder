import usuarios.Candidato
import usuarios.Empresa

static void main(String[] args) {
  def candidatos = []
  def empresas = []

  def scanner = new Scanner(System.in)

  loopMenu: while (true) {
    println("\nEscolha uma opção:")
    println("1 - Listar Candidatos")
    println("2 - Listar Empresas")
    println("3 - Adicionar Candidato")
    println("4 - Adicionar Empresas")
    println("0 - Sair")
    def opcao = scanner.nextLine()

    switch (opcao) {
      case "1":
        listarCandidatos(candidatos)
        break
      case "2":
        listarEmpresas(empresas)
        break
      case "3":
        adicionarCandidatos(candidatos, scanner)
        break
      case "4":
        adicionarEmpresas(empresas, scanner)
        break
      case "0":
        println("Encerrando o programa...")
        break loopMenu
      default:
        println("Opção inválida!")
    }
  }
}

def listarCandidatos(candidatos) {
  println("\nLista de Candidatos:")
  candidatos.each { candidato ->
    println("Nome: ${candidato.nome}")
    println("Email: ${candidato.email}")
    println("Idade: ${candidato.idade}")
    println("Estado: ${candidato.estado}")
    println("CEP: ${candidato.cep}")
    println("Descrição: ${candidato.descricao}")
    println("Competências: ${candidato.competencias.join(', ')}")
    println()
  }

  println("\nCandidatos armazenados no arquivo:")
  def candidatosArquivo = new File("candidatos.txt")
  println(candidatosArquivo.text)
}

def listarEmpresas(empresas) {
  println("\nLista de Empresas:")
  empresas.each { empresa ->
    println("Nome: ${empresa.nome}")
    println("Email Corporativo: ${empresa.email}")
    println("País: ${empresa.pais}")
    println("Estado: ${empresa.estado}")
    println("CEP: ${empresa.cep}")
    println("Descrição: ${empresa.descricao}")
    println("Competências Esperadas: ${empresa.competenciasEsperadas.join(', ')}")
    println()
  }

  println("\nEmpresas armazenadas no arquivo:")
  def empresasArquivo = new File("empresas.txt")
  println(empresasArquivo.text)
}

def validarEntrada(regex, mensagem, scanner) {
  while (true) {
    print(mensagem)
    def entrada = scanner.nextLine()

    if (entrada.matches(regex)) {
      return entrada
    } else {
      println("Entrada inválida. Por favor, insira uma entrada válida.")
    }
  }
}

def adicionarCandidatos(candidatos, scanner) {
  def regexNome = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
  def regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
  def regexCpf = /^\d{11}$/
  def regexIdade = /^(1[8-9]|[2-9][0-9])$/
  def regexEstado = /^[a-zA-Z]{2}$/
  def regexCep = /^\d{8}$/
  def regexDescricao = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
  def regexCompetencia = '^[a-zA-Z]+$'

  def nome = validarEntrada(regexNome, "Nome: ", scanner)
  def email = validarEntrada(regexEmail, "Email: ", scanner)
  def cpf = validarEntrada(regexCpf, "CPF: ", scanner) as long
  def idade = validarEntrada(regexIdade, "Idade: ", scanner)as int
  def estado = validarEntrada(regexEstado, "Estado: ", scanner)
  def cep = validarEntrada(regexCep, "CEP: ", scanner) as int
  def descricao = validarEntrada(regexDescricao, "Descrição: ", scanner)

  print("Competências (separadas por vírgula): ")
  Scanner entrada = new Scanner(System.in)
  def competenciasEntrada = entrada.nextLine()

  def competencias = competenciasEntrada.split(',').collect { it.trim() }

  adicionarCandidato(candidatos, nome, email, cpf, idade, estado, cep, descricao, competencias)
  salvarCandidatosEmArquivo(candidatos)
  println("Candidato adicionado com sucesso!\n")
}


def adicionarEmpresas(empresas, scanner) {
  def regexNomeEmpresa = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
  def regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
  def regexCnpj = /^\d{14}$/
  def regexPais = /^[A-Za-z\s]{1,35}$/
  def regexEstado = /^[a-zA-Z]{2}$/
  def regexCep = /^\d{8}$/
  def regexDescricao = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
  def regexCompetencia = '^[a-zA-Z]+$'

  def nome = validarEntrada(regexNomeEmpresa, "Nome: ", scanner)
  def email = validarEntrada(regexEmail, "Email: ", scanner)
  def cnpj = validarEntrada(regexCnpj, "CNPJ: ", scanner) as long
  def pais = validarEntrada(regexPais, "País: ", scanner)
  def estado = validarEntrada(regexEstado, "Estado: ", scanner)
  def cep = validarEntrada(regexCep, "CEP: ", scanner) as int
  def descricao = validarEntrada(regexDescricao, "Descrição: ", scanner)

  print("Competências Esperadas (separadas por vírgula): ")
  Scanner entrada = new Scanner(System.in)
  def competenciasEntrada = entrada.nextLine()

  def competenciasEsperadas = competenciasEntrada.split(',').collect { it.trim() }

  adicionarEmpresa(empresas, nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas)
  salvarEmpresasEmArquivo(empresas)
  println("Empresa adicionada com sucesso!\n")
}

static def adicionarCandidato(candidatos, nome, email, cpf, idade, estado, cep, descricao, competencias) {
  def candidato = new Candidato(nome, email, cpf, idade, estado, cep, descricao, competencias as List)
  candidatos << candidato
  return candidato
}

static def adicionarEmpresa(empresas, nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas) {
  def empresa = new Empresa(nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas as List)
  empresas << empresa
  return empresa
}

static void salvarCandidatosEmArquivo(candidatos) {
  def candidatosArquivo = new File("candidatos.txt")
  candidatosArquivo.withWriterAppend { writer ->
    candidatos.each { candidato ->
      writer << "Candidato\n"
      writer << "Nome:${candidato.nome}\n"
      writer << "Email:${candidato.email}\n"
      writer << "CPF:${candidato.cpf}\n"
      writer << "Idade:${candidato.idade}\n"
      writer << "Estado:${candidato.estado}\n"
      writer << "CEP:${candidato.cep}\n"
      writer << "Descrição:${candidato.descricao}\n"
      writer << "Competências:${candidato.competencias.join(', ')}\n\n"
    }
  }
}

static void salvarEmpresasEmArquivo(empresas) {
  def empresasArquivo = new File("empresas.txt")
  empresasArquivo.withWriterAppend { writer ->
    empresas.each { empresa ->
      writer << "Empresa\n"
      writer << "Nome:${empresa.nome}\n"
      writer << "Email Corporativo:${empresa.email}\n"
      writer << "CNPJ:${empresa.cnpj}\n"
      writer << "País:${empresa.pais}\n"
      writer << "Estado:${empresa.estado}\n"
      writer << "CEP:${empresa.cep}\n"
      writer << "Descrição:${empresa.descricao}\n"
      writer << "Competências Esperadas:${empresa.competenciasEsperadas.join(', ')}\n\n"
    }
  }
}

