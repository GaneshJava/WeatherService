var webpack = require('webpack');
var path = require('path');

var config = {
   context: path.join(__dirname, "src"),
   entry: {
      app: ["./js/index.js"]
   },

   output: {
      path: path.resolve(__dirname, "src"),
      filename: 'index.bundle.js',
   },
	  
   devServer: {
      contentBase: './src',
      publicPath: '/',
      port: 3000
   },
	
   module: {
      loaders: [
         {
            test: /\.jsx?$/,
            exclude: /node_modules/,
            loader: 'babel-loader',
				
            query: {
               presets: ['es2015', 'react']
            }
         }
      ]

   }

}

module.exports = config;