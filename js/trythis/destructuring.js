const hong = {id:1, name:'Hong'};
const lee = {id:2, name:'Lee'};
f1(hong);

function f1(user){
    const {id,name} = user;
    console.log(id,name);
}

function f2({id,name}){
    console.log(id,name);
}

console.log('-----------------') // ; can omit
const arr = [[{id:1}], [{id:2}], [{id:3}]];
const [[{id:id1}], [{id:id2}], [{id:id3}]] = arr;
console.log(id1, id2, id3);
// result ; 1 2 3

console.log("---------------------------")
const user = {name : 'Hong', pw: 'xyz', addr: 'seoul'};
function getUser(k){
    const {[k] : val} = user;
    const [, ...rest] = val;
    return rest.join('');
}

console.log(getUser('name'));
console.log(getUser('name'));
console.log(getUser('name'));
console.log('---------------------')

const ar = [1,2] // ?? 
