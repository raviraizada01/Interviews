var a = [1,2,[3,4,[8,9,[1,2],[6,7]]]];
newArr = [];
function flattenArray(arr){

for(var i=0; i<arr.length;i++)
{
if(typeof arr[i] == "object")
{
flattenArray(arr[i]);
}
else{
newArr.push(arr[i]);
}
}
}
flattenArray(a);
console.log(newArr);