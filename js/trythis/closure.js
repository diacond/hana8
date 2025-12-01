/*function discount(){
    return function discounter(price){
        return price * dcRate;
    }
}

const items = [
    {item: 'itemA', name: 30000},
    {item: 'itemB', name: 30000},
    {item: 'itemC', name: 30000}
];
const discounter = discount();

for(const {item,price : orgPrice} of items){ // 이게 디스컨덕터?
    const salePrice = orgPrice - discounter(orgPrice);
    console.log('${item} salePrice: ', salePrice. // ????
    toLocaleString());
}

console.log('-----------------')

const actions = ['입장', '입장', '입장', '퇴장', '입장', '퇴장']; // Status Queue

const {connect, disconnect, getCount} = currentCount();
for(const status of actions){
    // 삼항 연산자보다 객체로 만들어서 입장은 count - 퇴장은 disconnect?
    if(status === '입장') connect();
    else disconnect();
}

function currentCount(){
    let cnt = 0;
    function connect(){
        cnt++;
    }
    function disconnect(){
        cnt--;
    }
    function getCount(){
        return cnt;
    }

    return {
        connect: connect,
        disconnect: disconnect,
        getCount: function getCount(){
            return cnt;
        }
    }
}

console.log("🚀 ~ getCount ~ cnt:", cnt)

console.log('------------------')


function sum10(){
    let sum = 0;
    for(let i = 1; i<= 100; i++) sum += i;
    return sum;
}

console.log('sum100>>', sum100())

function sum100recur(n = 1){
    if(n===100) return ;

    return n + sum100recur(n+1);
} // 재귀함수

console.log('----------------------')

function factorial(n){
    let ret = 1;
    while (n>1){
        ret *= n;
        n--;
    }
    return ret;
}
factorial(n)
console.log("🚀 ~ factorial(n):", factorial(n))
*/

console.log('----------------')
function makeArr(n){
    if(n < 1){
        return;
    }
    makeArr(n-1);

    console.log(",", n);
}

makeArr(10);

console.log('----------------')

function makeReverseArr(n){
    if(n < 1){
        return;
    }

    console.log(",", n);
    makeReverseArr(n-1);
}

makeReverseArr(5);

console.log('----------------')

function makeArr(n){
    if(n === 1) return [1];
    else return [n,...makeArr(n-1),]; // ... << 껍데기 벗겨라
}
const myArray = makeArr(5);
console.log(myArray);

console.log('----------------')

const maTCO = makeArrayTCO(10);
console.log(maTCO)
function makeArrayTCO(n,acc=[]){
    if(n===0) return acc;
    return makeArrayTCO(n-1, [n, ...acc]);
}

console.log('--------------')
/*
const memoizedTable = {};

let runcnt = 0;
function factorial(n){
    runcnt++;
    if(n===1) return 1;
    return memoizedTable(n) ?? 
    (memoizedTable[n] = n * factorial(n-1));
}

console.log('---------------')

console.log(factorial(3), runcnt);
runcnt=0;
console.log(factorial(5), runcnt);
runcnt=0;
console.log(factorial(10), runcnt);
*/
// memoized, 디바운스, 루트??? 
/*
    memoized 가 뭔데 ... 
*/

function memoized(fn){
    const cache = {};
    return function(k){
        return cache[k] ?? (cache[k] = fn(k));
    }
}

//function facto(k){return k;}
const memoizedFactorial = memoized(function facto(k){
    if(k === 1) return 1;
    return K* memoizedFactorial(k-1); 
});

console.log('-------------------------')
// 피보나치
function loopFb(n){
    if(n<=1){
        return n;
    }
    let f0 = 0;
    let f1 = 1;
    let fn;

    for(let i = 2; i<=n; i++){
        fn = f0+f1;

        f0 = f1;
        f1 = fn;
    }
    return f0;
}
const result = loopFb(10);
console.log(result);

function recurFb(n){
    if(n<=1){
        return n;
    }

    return recurFb(n-2) + recurFb(n-1);
}
const result2 = recurFb(5);
console.log(result2);


// 쉘로우 vs 디카
