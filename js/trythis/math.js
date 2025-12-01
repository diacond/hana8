const date1 = new Date('1970-01-01'); // (1970,1,1,0,0,0,0)
const date2 = new Date('1970-01-02');

const diffMs = date2 - date1;

const diffSec = diffMs / 1000;
// 다음달의 0일 -> 이번달의 말일임.

console.log(`${diffMs}`);
console.log('==================================')

const today = new Date();

const nyDate = new Date(2026, today.getMonth(), today.getDate());

const weekDays = ['일','월','화','수','목','금','토'];

const year = nyDate.getFullYear();
const month = nyDate.getMonth() + 1;
const date = nyDate.getDate();
const yoil = weekDays[nyDate.getDay()];

console.log(yoil);
console.log('=================================')

const td = new Date();
const after100 = new Date(td);

after100.setDate(td.getDate() + 100);

console.log(after100)
console.log('===================================')

const y = 2025;
const m = 10;

const last = 30;

const rdmDate = [];
for(let i = 0; i<5; i++){
    const d = Math.floor(Math.random() * last) + 1;
    rdmDate.push(new Date(y,m,d));
}
console.log(rdmDate)
// 랜덤으로 생성된 그 순서의 역? or 날짜 내림차순?

