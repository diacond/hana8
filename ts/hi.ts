const myName: string = "hi";
console.log("🚀 ~ myName:", myName);

// export {}; // hi.ts만의 스코프를 생성한다. 그래서 중복이 안생긴다. 원래는 myName으로 중복생겼었는데 안 난다 이제
let x: string = Math.random() > 0.5 ? "abc" : undefined;
console.log(x?.length);
