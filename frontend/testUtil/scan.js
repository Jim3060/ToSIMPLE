let http = require("http");

function scan(address, port) {
    return new Promise((resolve) => {
        let option = {
            "hostname": address,
            "port": port,
            "method": "GET",
            "path": "/",
            "headers":{}
        };

        const req = http.request(option, res => {
            res.on("data", () => {});

            res.on("end", () => {
                resolve(true);
            });
        });

        req.on("socket", function (socket) {
            socket.setTimeout(2000);
            socket.on("timeout", function () {
                req.abort();
            });
        });

        req.on("error", () => {
            resolve(false);
        });

        req.end();
    });
}

function main() {
    const base = "192.168.1.";
    const ports = [80, 8080];
    let total = 0;
    let waiting = 0;

    for (let i = 0; i < 255; i++) {
        let ip = base + i;
        for (let idx in ports) {
            waiting ++;
            let port = ports[idx];
            let p = scan(ip, port);
            p.then(data => {
                waiting--;
                if (data){
                    console.log(`${ip}:${port}`);
                    total++;
                }
                if (waiting == 0)
                    console.log(`Total: ${total}`);
            });
        }
    }
}

main();