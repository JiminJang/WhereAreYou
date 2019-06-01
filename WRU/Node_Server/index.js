var mysql =require ('mysql');
var connection =mysql.createConnection({
    host : 'localhost',
    user : 'root',
    password : 'jang4378!!',
    port : 3306,
    database : 'WRU'

});

connection.connect();

connection.query('select * from location',function(err, rows, fields){
    if(!err)
        console.log('The solution is:',rows);
    else
        console.log('Error while performing Query.',err);

});
connection.end();