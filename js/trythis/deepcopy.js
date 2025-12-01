// 1) shallow copy
const kim = {nid: 3, nm: 'Kim', addr: 'Pusan'};
const newKim1 = shallowCopy(kim);                      // cf. {...kim}
newKim1.addr = 'Daegu';
assert.notEqual(kim.addr, newKim1.addr); 

function shallowCopy(obj){
    return {...obj};
}

// 2) 이하 deep copy
const kim2 = {nid: 3, nm: 'Kim', 
              addr: {city: 'Busan', road: 'Haeundaero', zip: null },
            };
const newKim1 = shallowCopy(kim);
newKim1.nid = 5;
assert.notEqual(kim.nid, newKim1.nid);
assert.strictEqual(kim.nm, newKim1.nm);
const newKim2 = deepCopy(kim2); 
newKim2.addr.city = 'Daegu';
assert.notEqual(kim.addr.city, newKim1.addr.city); 
console.log(kim);
console.log(newKim1);

/*
function shallowCopy(obj){
    // return {...obj}
    // return Object.assign({}, obj)
    const ret = {};
    for(const [k, v]) 
} 
*/

/*
shallow copy - 힙에서 스택으로 복사 - 객체는 복사 안함.
deep copy - 객체까지 모두 복사

모든 변수는 다 스택에 있다.
*/

function deepCopy(obj){

    if(obj === null || typeof obj !== 'object') return obj;

    const ret = {};
    for(const [k,v] of Object.entries(obj)){
        ret[k] = deepCopy(v);
    }
    return ret; // object라면 계속 돌아가고, 아니라면 끝
}
