
var MongoClient = require('mongodb').MongoClient;
var url = "mongodb://localhost:27017/";

MongoClient.connect(url, function(err, db) {
  if (err) throw err;
  var dbo = db.db("test");
  var query={moimname: "moim1"};
  var newvalues={$set: {moimname: "moim2"}};
  dbo.collection("moim").updateOne(query,newvalues,function(err,res){
    if (err) throw err;
    console.log("updated successfully");
    db.close();
  });
});
  //dbo.collection("moim").findOne({}, function(err, result) {
    