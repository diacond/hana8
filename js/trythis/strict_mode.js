'use strict'
var aa = 1;
console.log(aa); // 1 출력

function f1(a,ax){
    console.log('f1', aa); // f1 1 출력
}
// 오류 - NaN = 1;
// 오류 - Infinite = 1;

{
    var aa = 2;
    function f1(){
        console.log('ineer-f1', aa) // 출력 안된다. 이 블록 안에서만 노는 애들이라 그런가??
    }
    // f1(); 블럭 안에서는 출력된다.
}

f1();