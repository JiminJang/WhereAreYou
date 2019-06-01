var mongoose=require('mongoose');
var MoimSchema=new mongoose.Schema({
    moimid: Int16Array,
    moimname: String,
    numofppl: Int16Array,
    moimtime: Int16Array
});

mongoose.model('Moim',MoimSchema);
module.exports=mongoose.model('Moim');