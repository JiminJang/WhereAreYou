var http=require('http');
var hostname='127.0.0.1';
var port =3000;

http.createServer(function(req,res){
    res.writeHead(200,{'Content-Type':'text/plain'});
    res.end('Hello world\n');

}).listen(port,hostname);

console.log('server running at http://'+hostname+':'+port);