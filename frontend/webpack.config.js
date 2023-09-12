const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");

module.exports = {
  mode: 'development',
  entry: './src/index.ts',
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
    filename: 'main.min.js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/dist/'
  },
  plugins: [
    new CopyPlugin({
      patterns: [
        {from: 'public'}
      ]
    })
  ]

};
