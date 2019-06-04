var express=require('express');
var router=express.Router();
var bodyParser=require('body-parser');


router.post('/searchmoim_page',function(req,res){
    var search_moim=req.body.search_moim;
    //var sql='SELECT * FROM moim WHERE moimname=?';
    // clientInformation.query(sql,search_moim,function(error,results){
    //     if(error){
    //         console.log(error);
    //     }
    //     else{
    //         console.log(results);
    //         res.json(results[0]);
    //     }
    // });

    console.log(search_moim);

})


module.exports=router;