const myName: string = 'Jade';
greet(myName);

function greet(str: string){ // string 이 jade 보다 큰 스코프라서, greet는 jade말고 다른거 와도 받을수있다
    console.log(`Hello, ${str}`); // 타입일치되면 제일 좋다
}
