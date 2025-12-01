const gate1counter = (function counter(){
    let count = 0;
    return function (){
        return count++;
    };
})();
// 표현식 - 리터럴이 존재하는것? 리터럴?
//const gate1counter = counter();
console.log(':', gate1counter());
console.log(':', gate1counter());
console.log(':', gate1counter());

/*
{
    var / function (x)
    const / let
}
즉시 호출 함수 - 불필요한 전역, 메모리 낭비 줄이기
closure 함수 즉시 반환
부분 await 활용 가능
*/
/*
(async function af(){
    // async - 비동기 - await 쓸수있다
    const data = await fetch('https://jsonplaceholder.typicode.com/todos/1')
      .then(res => res.json()
    );
    return data;
})().then(data=>console.log(data));

for(let i =0; i<10; i++){
    // var안쓰는 이유?? 오류가 생길 수 있어서 
    // let 쓴다.
    setTimeout(
        function(n){
            console.log('xxxxxx',i,n);
        },
        1000,
        i
    );
}
*/

setInterval(
    function (n){
        console.log('xxxxxx', n);
    },
    1000,
    100
);

setTimeout(() => clearInterval(intl),5000);

/*
이벤트 루프 중요함 - 128p
큐에다가 프로미스 콜백들이 쭉 들어간다. 1ms에 한번씩 돈다. 
프로미스 콜백 있어? 없으면 nextTickQ로 간다. 
그림보고 뭐가 언제 실행되는지 알아야함.
*/



