function p13(){
    let a = 1;
    let b = a;
    let c = [1,2];
    let d = c;

    a = 2;
    console.log(a,b);
    c.push(3);

    d = [3,4];

    console.log(c,d);
}

function coercion(){
    i=100;
    i.toString();
    console.log(10 + i.toString());
}

function p18(){

    i=100;
    i.toString();
    console.log(10 + i.toString());

    let u = 'hong';
    u.age = 30;
    console.log(u.age); // undefined 뜬다...why??
    u=7;
    console.log(u, !i); // 뒤에 i는 숫자인데 !붙었으니 boolean -> false

    a = 1;
    b = (a.b = 5, console.log('xx.>>', a.b));
    console.log(a,b);
    // 여기서도 a.b는 undefined다. 괄호가 있는 해당 "문" 속에서만 유효
    // 괄호밖에서는 아무 의미가 없다...

    a = 1;
    console.log('xx>>', a.b, a.toString() === '1');
    console.log(a);
}

p18();
