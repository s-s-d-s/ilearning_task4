const path = process.cwd()
const fs = require("fs");
sha3_256 = require('js-sha3').sha3_256;

fs.readdirSync(path).forEach(file => {
    data = fs.readFileSync(file)
    r = sha3_256(data);
    console.log(file + ' ' + r)
})