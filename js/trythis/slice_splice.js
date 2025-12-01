// sort는 순수함수가 아님. a라는 걸 변형시키니까...

const assert = require('assert');
const arr2 = [1,2,3,4,5];
const ex1 = arr2.slice(1,3);
assert.deepStrictEqual(ex1, [2,3]);

// ex2) [3]부터 모든 원소 다 추출
const ex2 = arr2.slice(2);
assert.deepStrictEqual(ex2, [3,4,5])

//EX3) [2,3,4] 제거하기
const ex3 = arr2.splice(1,3);
assert.deepStrictEqual(ex3, [2,3,4]);
assert.deepStrictEqual(arr2, [1,5]);

//ex4) 복원
const ex4 = arr2.splice(1,0,...ex3); // 1부터 지우는 건 없고 ex3 넣는다.

assert.deepStrictEqual(ex4, []);
assert.deepStrictEqual(arr2, [1,2,3,4,5]);

const ex5 = arr2.splice(2);
console.log(ex5)
console.log(arr2)

const ex6 = arr2.splice(2,0, ...ex5)
console.log(arr2);

/*
const rmv = arr2.splice(2,3,'x','y','z',4,5);
console.log(arr2);
*/

// 2. 추가할 새 요소
const newItems = ['X', 'Y', 'Z'];

// 3. slice와 spread를 사용해 새 배열 생성
//    arr2.slice(0, 2)  --> [1, 2]
//    arr2.slice(3)     --> [4, 5]  (인덱스 3부터 끝까지)
const ex7 = [
  ...arr2.slice(0, 2),  // [1, 2]의 요소들을 펼침
  ...newItems,          // ['X', 'Y', 'Z']의 요소들을 펼침
  ...arr2.slice(3)      // [4, 5]의 요소들을 펼침
];

// 4. 결과 확인
console.log("최종 결과:", ex7);
console.log("원본 arr2:", arr2);
console.log('==============================')

// p.161 연습문제

const arr3 = [1,2,3,4]
const add_arr = [5,6]
const push_arr = arr3.splice(4,0, ...add_arr);
console.log(arr3)
console.log('==============================')

const sub_four = arr3.splice(3,1)
console.log(arr3)
console.log('==============================')
arr3.splice(3,0, ...sub_four)
console.log('원복 : ',arr3)

const sub_three_four = arr3.splice(2,2) // 인덱스 기준 2번부터 2개 뺀다 => 인덱스 2,3 제거함
console.log(arr3)
console.log('==============================')

const arr4 = [1,2,3,4]
const arr5 = [7,8]
// 스프레딩 사용함
const arr6 = [...arr5, ...arr4]
const arr7 = [0]
const arr8 = [...arr7, ...arr4]

console.log(arr6)
console.log(arr8)
console.log('==============================')

