const hong = { id: 1, name: 'Hong' };
const kim = { id: 2, name: 'Kim' };
const lee = { id: 3, name: 'Lee' };
const park = { id: 4, name: 'Park' };
const users = [hong, kim, lee, park];

const find3 = a => a.id === 3;
const idxId2 = users.findIndex(find3);

// Try this: id가 전달 된 pid인 user를 반환하는 findId 함수를 작성하시오.
const findIdx = pid => () =>
  users.find(user => {
    console.log(pid, user, user.id === pid);
    return user.id === pid;
  });
// const user1 = findId(1);
// console.log(user1, user1());
// const idxId11 = users.findLastIndex(findId(1));
// const idxId11 = users.findLastIndex(user => user.id === 1);
// const findId = pid => user => user.id === pid;
const findId =
  pid =>
  ({ id }) =>
    id === pid;
const idxId11 = users.findLastIndex(findId(1));
// const idxId11 = users.findLastIndex(a => a.id === 1);
// console.log(users);
console.log('🚀  idxId11:', idxId11);

console.log('----------------------');

const arr = Array.from({length: 5}, (_, i) => i + 1);

const isEven = n => n%2 === 0;
const evl = arr.map((_,i) => isEven(i));
console.log("🚀 ~ evl:", evl);
const ev2 = arr.map(isEven);
console.log("🚀 ~ ev2:", ev2);
const onlyEvens = arr.filter(isEven);
console.log("🚀 ~ onlyEvens:", onlyEvens);

arr.forEach(a => console.log(a, isEven(a)));
console.log('---------')
for(let i =0; i<arr.length; i++) console.log(arr[i], isEven(arr[i]));
// 내가 아는 전통적인 for문은 잘 안쓴다. forEach를 주로 씀
for(const a of arr) console.log(a, isEven(a)); // 얘가 성능이 제일 좋긴 함

const arr2 = [...arr];
console.log("🚀 ~ arr2:", arr2 === arr)

const arr3 = arr2.concat(arr);
console.log("🚀 ~ arr3:", arr3 === arr )

const arr4 = [...arr2, ...arr3];
console.log("🚀 ~ arr4:", arr4)

const a3 = arr.find(a => a === 3);
console.log("🚀 ~ a3:", a3)
const evenOdds = Object.groupBy(arr, (a) => isEven(a) ?
'even' : 'odd');
console.log("🚀 ~ evenOdds:", evenOdds)

const jarr = arr.join('');
console.log("🚀 ~ jarr:", jarr)

const a = [1,2,3,4,5,6,7];

a.copyWithin(4,2); // 인덱스4에다가, 인덱스2부터 끝까지 복사 ㄱ
// 34567을 5부터 7까지 자리에 복사? 근데 길이 초과는 못함 1234345
// ~1118 1교시
let b = a;
b.push('02', '01', '03', 'a', 'c', 'b', 'aa');
const s1 = b.sort();
console.log("🚀 ~ s1:", s1, a)
b=a;
const s2= b.sort((a,b) => a>b?1 : -1);
console.log("🚀 ~ s2:", s2)
b=a
const s3= b.sort((a,b) => a<b?1 : -1);
console.log("🚀 ~ s3:", s3)
b=a;
const s4= b.sort((a,b) => a<b?1 : -1);
console.log("🚀 ~ s4:", s4)


