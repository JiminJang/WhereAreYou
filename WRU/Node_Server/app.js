
var express=require("express");
var mongoose=require('mongoose');
var bodyParser=require('body-parser');
var app=express();

var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

// var searchmoim_page=require('./routes/searchmoim_page');
// app.use('/searchmoim_page',searchmoim_page);

module.exports=app;
app.use(bodyParser.json());


let moim=[
    {moimname:'1',
    moimtime:'6'}
]
app.use(express.json());


// app.get('/searchmoim_page',(req,res)=>{
    
//     console.log(req.body);
//     res.json(moim)

//  })
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
app.post('/moimlist',function(req,res){
    MongoClient.connect(url, function(err, db){
        if(err) throw err;
        var dbo=db.db("wru");
        dbo.collection("moim").find().toArray(function(err,result){
            if(err) throw err;
            res.json(result);
            console.log(result);
            db.close();
        });
        
        });

})
app.post('/searchmoim_page',function(req,res){

    var search_moimname=req.body.search_moim;
    console.log(search_moimname);
    MongoClient.connect(url, function(err, db){
        if(err) throw err;
        var dbo=db.db("wru");
        var query={moimname: search_moimname};
        dbo.collection("moim").find(query).toArray(function(err,result){
            if(err) throw err;
            res.send(result);
            console.log(result);
            db.close();
        });
        
        });
    });


app.listen(3000,()=>{
    console.log("example app listening on port 3000")
});


