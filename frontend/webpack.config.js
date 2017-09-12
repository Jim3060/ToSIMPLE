let webpack = require("webpack");

const UglifyEsPlugin = require("uglify-es-webpack-plugin");
 
module.exports = {
    entry: __dirname + "/src/main.js",
    output: {
        path: __dirname + "/out",
        filename: "bundle.js"
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: "vue-loader",
                options: {
                    // other vue-loader options go here
                }
            },
            {
                test: /\.js$/,
                loader: "babel-loader",
                exclude: /node_modules/
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
            {
                test: /\.(eot|svg|ttf|woff|woff2)(\?\S*)?$/,
                loader: "file-loader"
            },
            {
                test: /\.less$/,
                loader: "less-loader",
                exclude: /node_modules/
            },
            {
                test: /\.(png|jpg|gif|svg)$/,
                loader: "file-loader",
                options: {
                    name: "[name].[ext]?[hash]"
                }
            }
        ]
    },
    performance: {
        hints: false
    },
    resolve: {
        alias: {
            "vue$": "vue/dist/vue.esm.js"
        }
    },
    devtool: false,
    plugins:[
        /*new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: "\"production\""
            }
        }),
        new UglifyEsPlugin()*/
    ]
};
/*
if (process.env.NODE_ENV === 'production') {
    module.exports.devtool = 'source-map';
 
    module.exports.plugins = (module.exports.plugins || []).concat([
        new UglifyEsPlugin()
    ]);
}*/