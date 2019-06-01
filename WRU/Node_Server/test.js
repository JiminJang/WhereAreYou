const express=require("express");
const app=express();
var mysql=require('mysql');
var connection=mysql.createConnection({
    host:'localhost',
    user:'root',
    password:'jang4378!!',
    port:3306,
    database:'WRU'
});

connection.connect();
connection.query('select * from moim',function(err,rows,fields){
    if(!err)
        console.log('the solution is:',rows);
    else
        console.log('error',err);
});
// connection.query('insert into location (username,latitude,longitude) values("jiwon",30,30)',function(err,rows,fields){
//     if(!err)
//         console.log('the solution is:',rows);
//     else
//         console.log('error',err);
// });
// connection.query('select * from moim',function(err,rows,fields){
//     if(!err)
//         console.log('the solution is:',rows);
//     else
//         console.log('error',err);
// });

connection.end();
let users=[
    {id:1,
    name:'alice'}
]
let moim=[
    {moimname:'1',
    moimtime:'6'}
]
app.get('/',(req,res)=>{
    console.log('info of moim');
    res.json(moim)

});

app.post('/',(req,res)=>{
    console.log('info of moim');
    var inputData;

    req.on('data',(data)=>{
        inputData=JSON.parse(data);
    });

    req.on('end',()=>{
        console.log("moimname:"+inputData.moimname);
    });
    res.write("Okay");
    res.end();
});
app.listen(3000,()=>{
    console.log("example app listening on port 3000")
});