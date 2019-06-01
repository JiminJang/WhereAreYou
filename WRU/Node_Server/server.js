var app=require('./app');
var port=3000;



var server=app.listen(3000,()=>{
    console.log("example app listening on port 3000")
});





var express=require("express");
var bodyParser=require('body-parser');
var app=express();
var db=require('./db');

module.exports=app;
app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({
//     extended: true,
//     useNewUrlParser: true
// }));


let moim=[
    {moimname:'1',
    moimtime:'6'}
]
//app.use(express.urlencoded());
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
    console.log(req.body);
    console.log(moimname);
    console.log(numofppl);
    console.log(moimtime);

    res.write('okayy');
})

app.listen(3000,()=>{
    console.log("example app listening on port 3000")
});


