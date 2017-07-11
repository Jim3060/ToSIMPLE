var fs   = require("fs"  );
var http = require("http");
var url  = require("url" );
var path = require("path");

var root = path.resolve(".");
var allowExternalRequest = true;
var address = "192.168.1.30:8080";
http.createServer(function(request,response){
    if(!allowExternalRequest&&request.headers["host"]!="127.0.0.1:8080"&&request.headers["host"]!="localhost:8080"){
        response.writeHead(403);
        response.end("<h1>403 Forbidden</h1>")
        return;
    }
    console.log(`Head: ${JSON.stringify(request.headers)}`);
    response.writeHead(200);
    response.end(`Head: ${JSON.stringify(request.headers)}`);
    
    let buffer = "";
    request.on("data", data=>{
        buffer += data;
    });
    request.on("end", ()=>{
        console.log(buffer);
    })

}).listen(8081, ()=>{console.log("listen on 8081");});
