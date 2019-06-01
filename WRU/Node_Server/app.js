
var express=require("express");
var mongoose=require('mongoose');
var bodyParser=require('body-parser');
var app=express();

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";


module.exports=app;
app.use(bodyParser.json());


let moim=[
    {moimname:'1',
    moimtime:'6'}
]
app.use(express.json());


app.get('/',(req,res)=>{
    
    console.log(req.body);
    console.log('info of moim');
    res.json(moim)

 })
app.post('/',function(req,res){
    var moimname=req.body.moimname, 
    numofppl=req.body.numofppl,
    moimtime=req.body.moimtime;
    moimlocation=req.body.moimlocation,
    moimpw=req.body.moimpw;
    console.log(req.body);
    console.log(moimname);
    console.log(numofppl);
    console.log(moimtime);

    res.write('okayy');

    MongoClient.connect(url, function(err, db){
        if(err) throw err;
        var dbo=db.db("wru");
        dbo.collection("moim").insert({moimname:moimname, numofppl:numofppl, moimtime: moimtime, moimpw: moimpw, moimlocation: moimlocation},function(err,doc){
            console.log('inserted successfully')
        
        });
    });
})

app.listen(3000,()=>{
    console.log("example app listening on port 3000")
});


