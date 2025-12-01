var x = 1;

function f1(){
    console.log(x); // 정적 - 1, 동적 - 2
    // outerEnv
}

function f2(){
    var x = 2;
    f1(); // 1나온다. var x = 2는 적용 안되는거?
    this.y = 999;
    f1.bind({y:100})();
}
globalThis.z = 555; // globalThis.z = 555; 가 맞음 원래는...
// globalThis = global obj
let cnt = 0;
const f3 = function(){
    cnt++;
}

f2();

// 출력 1 1나오는데 왜 그런거임..?

