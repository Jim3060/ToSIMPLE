let http = require("http");
let config = require("./config.json");

let reports = [];
let count = 0;
const timeout = config.timeout;

function sendRequest(option, body, expect){
    return new Promise((resolve, reject)=>{
        let buffer = "";
        const startTime = new Date();
        if (body != "")
            option.headers["Content-Length"] = Buffer.byteLength(body);
        const req = http.request(option, res=>{
            let statusCode = res.statusCode;
            res.on("data", data=>{
                buffer += data;
            });
            res.on("end", ()=>{
                const endTime = new Date();
                const time = endTime.getTime() - startTime.getTime();
                if (expect == ""){
                    resolve([statusCode, time]);
                }else{
                    resolve([statusCode, time, buffer==expect?'success':'fail']);
                }
            });
        })

        req.on('socket', function (socket) {
            socket.setTimeout(timeout);  
            socket.on('timeout', function() {
                req.abort();
            });
        });

        req.on("error", e=>{
            resolve(["error", 0, "fail"]);
        })

        if (body != "")
            req.write(body);
        req.end();
    })
} 

function printStatistics(reports){
    let status = {};
    let timeTotal = 0;
    let timeMin = 10000000;
    let timeMax = 0;
    let count = 0;
    let success = 0;
    let fail = 0;

    for(let i in reports){
        let report = reports[i];
        count++;
        status[report[0]] = (status[report[0]] || 0) + 1;
        timeTotal += report[1];
        timeMin = timeMin>report[1]&&report[0]!="error"?report[1]:timeMin;
        timeMax = timeMax<report[1]&&report[0]!="error"?report[1]:timeMax;
        if(report.length == 3){
            report[2]=="success"?success++:fail++;
        }
    }

    console.log("Status: ");
    for(let key in status){
        console.log(`   ${key}: ${status[key]}`);
    } 

    if(timeMin <= timeMax){
        console.log("");
        console.log("Time: ");
        console.log(`   min: ${timeMin} ms, max: ${timeMax} ms, average: ${timeTotal/count} ms`);

        if(reports[0].length == 3){
            console.log("");
            console.log(`success: ${success}, fail: ${fail}`);
        }
    }

}

function main(){
    const interval = config.interval;
    const times = config.times;
    const option = config.sendOptions;
    const body = config.sendBody;
    const expect = config.expectResponse;

    for(let i = 0; i < times; i++){
        setTimeout( ()=>{
            let p = sendRequest(option, body, expect);
            p.then(value=>{
                reports.push(value);
                count++;
                if(count == times){
                    printStatistics(reports);
                }
            }, err=>{
                console.log(err);
            })
        }, i * interval)
    }

    //setTimeout(()=>{console.log(report)}, times * interval + 5000)
}

main();