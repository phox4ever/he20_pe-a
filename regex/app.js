const http = require('http')
const { parse } = require('querystring');

const hostname = '127.0.0.1'
const port = 3000

const server = http.createServer((req, res) => {
    const reg_email = /^[a-zA-Z.]+@hf-ict\.(ch|info)+$/
    const reg_pw = /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z]{8,}/
    const baseURL =  req.protocol + '://' + req.headers.host + '/'
    const reqUrl = new URL(req.url,baseURL)
    let body = '';
    req.on('data', function (chunk) {
        body += chunk;
    });
    req.on('end', function () {
        let result = ''
        let input = ''
        let pw = ''
        let success = true
        let params = {}
        if (body) {
            params = parse(body)
        }
        res.writeHead(200);
        if (params.input) {
            input = params.input
            let found = input.match(reg_email)
            if (found) {
                result = input + " is a valid  hf-ict email!<br>"
            }
            else {
                result = input + " is not valid!<br>"
                success = false
            }
        }

        if (params.pw) {
            pw = params.pw
            let pw2 = params.pw2
            if (!pw2 || pw !== pw2) {
                result = result + "PWs don't match"
            } else {
                let found = pw.match(reg_pw)
                if (found) {
                    result = result + "PW is a valid"
                }
                else {
                    success = false
                    result = result + "PW is not valid!"
                }
            }
        }

        if (!result) {
            result = 'Please register with your hf-ict email.'
        }

        const out = '<html><head><title>Regex Test</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous"></head><body>' +
            '<div class="container m-5"><div class="alert-' + (success ? 'success' : 'danger') + ' m-2 p-2">' + result + '</div>' +
            '<form method="post" action="/"><input class="m-2" name="input" type="text" value="' + input + '" placeholder="email"><br><input class="m-2" name="pw" type="password" placeholder="password" value="' + pw + '"><br><input class="m-2" name="pw2" type="password" placeholder="repeat" value="' + pw + '"><br><input class="btn btn-primary m-2" type="submit"></form>' +
            '</div></body></html>'
        res.end(out);
    });
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`)
});