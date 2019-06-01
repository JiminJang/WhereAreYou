var express=require('express');
var router=express.Router();
var bodyParser=require('body-parser');

router.use(bodyParser.urlencoded({extended:true}));

var Moim=require('./Moim');

router.post('/',function(req,res){
    Moim.create({
        moimname: req.body.moimname,
        numofppl: req.body.numofppl,
        moimtime: req.body.moimtime
    },
    function(err, user){
            if(err) return res.status(500).send("수정 실패");
            res.status(200).send(user);
    
    });
});
