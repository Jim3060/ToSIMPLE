var fs   = require("fs"  );
var http = require("http");
var url  = require("url" );
var path = require("path");
var sendRequest = require("request");

var root = path.resolve(".");
var allowExternalRequest = true;
var address = "static.cnbetacdn.com";

http.createServer(function(request,response){
    if(!allowExternalRequest&&request.headers["host"]!="127.0.0.1:8080"&&request.headers["host"]!="localhost:8080"){
        response.writeHead(403);
        response.end("<h1>403 Forbidden</h1>")
        return;
    }
    
    console.log("-------------------------------------");
    console.log(request.method+":"+url.parse(request.url).pathname);
    console.log(url.parse(request.url,true).query);
    console.log("user-agent:"+request.headers["user-agent"]);

    var fpath = path.join(root, url.parse(request.url).pathname);
    fs.stat(fpath,function(err, stat){
        if(fpath == root+"/"){
            console.log("status: 200");
            response.writeHead(200,{"Content-type":"text/html; charset=utf-8"});
            fs.createReadStream("index.html").pipe(response);
        }
        else if(err||!stat.isFile()){
            //TODO
            var target = "http://" + address + url.parse(request.url).pathname;
            console.log(target);
            sendRequest(target).pipe(response);
        }
        else{
            console.log("status: 200");
            response.writeHead(200);
            fs.readFile(fpath,"utf-8",function(err,data){
                var rsp = data ;
                response.end(rsp);
            })
        }
    });    
}).listen(8080, ()=>{console.log("listen on 8080");});
