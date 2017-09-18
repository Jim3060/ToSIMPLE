var http = require("http");

var allowExternalRequest = true;
http.createServer(function(request,response){
    if(!allowExternalRequest&&request.headers["host"]!="127.0.0.1:8080"&&request.headers["host"]!="localhost:8080"){
        response.writeHead(403);
        response.end("<h1>403 Forbidden</h1>");
        return;
    }
    console.log(`Head: ${JSON.stringify(request.headers)}`);
    response.writeHead(200);
    setTimeout(()=>{
        response.end(`Head: ${JSON.stringify(request.headers)}`);
    }, 1);
    
    let buffer = "";
    request.on("data", data=>{
        buffer += data;
    });
    request.on("end", ()=>{
        console.log(buffer);
    });

}).listen(8081, ()=>{console.log("listen on 8081");});
