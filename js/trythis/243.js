// const assert = require('assert');

// // 1. 받침 유무 확인 함수
// // const isEndJaum = (str) => {
// //     if (!str) return false;
// //     const lastChar = str[str.length - 1];
// //     const code = lastChar.charCodeAt(0);

// //     // 1. 한글 완성형 (가-힣)
// //     // 공식: (문자코드 - 0xAC00) % 28 > 0 이면 종성(받침) 있음
// //     if (code >= 0xAC00 && code <= 0xD7A3) {
// //         return (code - 0xAC00) % 28 > 0;
// //     }

// //     // 2. 숫자 (0-9)
// //     // 0(영), 1(일), 3(삼), 6(육), 7(칠), 8(팔) ⇒ 받침 있음
// //     // 2(이), 4(사), 5(오), 9(구) ⇒ 받침 없음
// //     if (code >= 48 && code <= 57) { 
// //         return /[013678]/.test(lastChar);
// //     }

// //     // 3. 영문 (L, M, N, R)
// //     // 문제 조건: L(엘), M(엠), N(엔), R(알)만 받침 있는 것으로 처리
// //     if ((code >= 65 && code <= 90) || (code >= 97 && code <= 122)) {
// //         return /[lmnr]/i.test(lastChar);
// //     }

// //     // 4. 한글 자모 (낱자)
// //     // 'ㅜㅜ', 'ㅋㅋ' 같은 경우 처리
// //     // ㄱ(0x3131) ~ ㅎ(0x314E): 자음 ⇒ true (받침처럼 취급하거나 소리값이 있으므로 true)
// //     // ㅏ(0x314F) ~ ㅣ(0x3163): 모음 ⇒ false
// //     if (code >= 0x3131 && code <= 0x314E) return true; 
// //     if (code >= 0x314F && code <= 0x3163) return false;

// //     // 그 외(특수문자 등)는 받침 없음으로 간주
// //     return false;
// // };

// // // 2. 조사 처리 함수들
// // const iga = (str) => isEndJaum(str) ? '이' : '가';
// // const eunun = (str) => isEndJaum(str) ? '은' : '는';
// // const eulul = (str) => isEndJaum(str) ? '을' : '를';
// // const eyuya = (str) => isEndJaum(str) ? '이어야' : '여야';
// // const ilang = (str) => isEndJaum(str) ? '이랑' : '랑';

// const isEndJaum = str =>{
//     const alphaNums = 'lmnr136780';
//     const lastChar = str.at(-1);
//     if(alphaNums.includes(lastChar)) return true;

//     const lastCharCode = lastChar.charCodeAt();
//     const ㄱ = 'ㄱ'.charCodeAt();
//     const ㅎ = 'ㅎ'.charCodeAt();
//     if(lastCharCode >= ㄱ && lastCharCode <= ㅎ) return
//     true;
//     const 가 = '가'.charCodeAt();
//     const 힣 = '힣'.charCodeAt();
//     // ㄱ-ㅎ
//     // 가-힣

//     return(
//         (lastCharCode > ㄱ && lastCharCode <= ㅎ && 
//             (lastCharCode >= ㄱ && lastCharCode <= ㅎ) return
//             true;
// };

// // --- Test Cases ---
// console.log("Running Tests...");

// try {
//     // isEndJaum 테스트
//     assert.equal(isEndJaum('아지오'), false);
//     assert.equal(isEndJaum('북한강'), true);
//     assert.equal(isEndJaum('뷁'), true);
//     assert.equal(isEndJaum('강원도'), false);
//     assert.equal(isEndJaum('바라당'), true);
//     assert.equal(isEndJaum('ㅜㅜ'), false); // 모음으로 끝남
//     assert.equal(isEndJaum('케잌'), true);
//     assert.equal(isEndJaum('점수 A'), false);
//     assert.equal(isEndJaum('알파벳L'), true); // L, M, N, R 조건
//     assert.equal(isEndJaum('24'), false); // '사'
//     assert.equal(isEndJaum('23'), true);  // '삼'

//     // 조사 붙이기 테스트
//     assert.equal(`고성군${iga('고성군')}`, '고성군이');
//     assert.equal(`고성군${eunun('고성군')}`, '고성군은');
//     assert.equal(`고성군${eulul('고성군')}`, '고성군을');
    
//     assert.equal(`성동구${iga('성동구')}`, '성동구가');
//     assert.equal(`성동구${eunun('성동구')}`, '성동구는');
//     assert.equal(`성동구${eulul('성동구')}`, '성동구를');
    
//     // 추가 조사 테스트 (~이어야/여야)
//     assert.equal(`고성군${eyuya('고성군')}`, '고성군이어야');
//     assert.equal(`성동구${eyuya('성동구')}`, '성동구여야');

//     // 추가 조사 테스트 (~이랑/랑)
//     assert.equal(`철수${ilang('철수')}`, '철수랑');
//     assert.equal(`길동${ilang('길동')}`, '길동이랑');

//     console.log("🎉 All tests passed successfully!");
// } catch (e) {
//     console.error("❌ Test Failed:", e.message);
//     console.error("Expected:", e.expected);
//     console.error("Actual:", e.actual);
// }