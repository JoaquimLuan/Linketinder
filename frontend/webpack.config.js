const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");

module.exports = {
  mode: 'development',
  entry: {
    candastroCandidato: '/home/joaquimborges/Documentos/LinketinderTs/frontend/src/candidato/cadastroCandidato.ts',
    perfilCandidato: '/home/joaquimborges/Documentos/LinketinderTs/frontend/src/candidato/perfilCandidato.ts',

    cadastravagas:'/home/joaquimborges/Documentos/LinketinderTs/frontend/src/empresa/cadastraVagas.ts',
    cadastroEmpresa:'/home/joaquimborges/Documentos/LinketinderTs/frontend/src/empresa/cadastroEmpresa.ts',
    perfilEmpresa:'/home/joaquimborges/Documentos/LinketinderTs/frontend/src/empresa/perfilEmpresa.ts',

    usuarios:'/home/joaquimborges/Documentos/LinketinderTs/frontend/src/usuario.ts',
    index:'/home/joaquimborges/Documentos/LinketinderTs/frontend/src/index.ts',

  },
  devServer: {
    static: path.join(__dirname, 'dist'),
    port: 9000,
    hot: true
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
  resolve: {
    extensions: ['.ts', '.js'],
  },
  output: {
    filename: '[name].min.js',
    path: path.resolve(__dirname, 'dist')
  },
  plugins: [
    new CopyPlugin({
      patterns:[
        {from: 'public'},
       ]
    })
  ]

};
