addPoints(0.12324, 0.1);

function addPoints(a,b){
    const alen = pointLength(a);
    const blen = pointLength(b);
    //const ret = alen > blen ? (a + b).toFixed(alen) : 
    //(a + b).toFixed(blen);
    //const ret = (a + b).toFixed(alen > blen) ? alen : blen;
    const ret = (a + b).toFixed(Math.max(alen, blen));
    console.log(a,b, '->', +ret);
}

function pointLength(num){
    if(!num) return 0;
    num.toString().length - Math.trunc(num); // trunc - 소수점 밑을 타겟?
    toString().length - 1;
}

//----------------------------------------------

function avg(prices){
    let N = 1000;
    let cnt = 0;
    let sum = 0;
    for(const price of prices){
        cnt++;
        if(price === null || isNaN(price)) continue;
        sum += price * 100;
    }
    const ret = Math.trunc(sum / cnt) / N;
    console.log("🚀 ~ avg ~ ret:", ret);
} // 이거 다시 꼭 복습해볼것. 진도가 너무 빠름

avg([
    15.234,
    undefined,
    '0.5',
])
