/**
 * 합이 N이 되는 두 요소의 인덱스를 O(N) 시간 복잡도로 찾는 함수입니다.
 * 원본 배열을 정렬하지 않고 Map을 사용하여 인덱스를 추적합니다.
 *
 * @param {number[]} arr - 정수 배열
 * @param {number} N - 목표 합
 * @returns {[number, number]} - 합이 N이 되는 두 요소의 인덱스 쌍
 */
const keyPair = (arr, N) => {
    // Map을 사용하여 (필요한 값: 인덱스) 쌍을 저장합니다.
    // Map을 사용하면 평균 O(1) 시간에 삽입 및 조회가 가능합니다.
    const map = new Map();

    for (let i = 0; i < arr.length; i++) {
        const currentValue = arr[i];
        
        // 1. 현재 값의 짝(보수)이 Map에 있는지 확인
        // Map에 현재 값이 키로 저장되어 있다는 것은, 이전에 그 '짝'을 만났고
        // 그 짝의 인덱스가 Map의 값으로 저장되어 있다는 의미입니다.
        if (map.has(currentValue)) {
            // 짝을 찾았습니다!
            const firstIndex = map.get(currentValue);
            const secondIndex = i;
            // 문제의 테스트 케이스는 항상 작은 인덱스를 먼저 반환하므로 정렬하여 반환합니다.
            return [firstIndex, secondIndex].sort((a, b) => a - b);
        }

        // 2. 짝을 찾지 못했다면, 현재 값의 '보수'와 현재 인덱스를 Map에 저장
        // Map의 키는 '나중에 찾아야 할 짝', 값은 '현재 요소의 인덱스'가 됩니다.
        const complement = N - currentValue;
        map.set(complement, i);
    }
    
    // 짝을 찾지 못한 경우
    return [];
};

// --- 테스트 코드 (Node.js의 assert 모듈 가정) ---
// assert 모듈이 없으므로, 결과를 직접 확인하는 방식으로 대체합니다.
const assertDeepStrictEqual = (a, b) => JSON.stringify(a) === JSON.stringify(b);
const assertOk = (condition) => condition ? "OK" : "FAIL";

console.log("--- KeyPair 함수 테스트 시작 ---");

const testCases = [
    // [arr, N, expected_result_1, expected_result_2(선택적)]
    [[1, 3, 4, 5, 7], 7, [1, 2]],
    [[1, 4, 45, 6, 10, 8], 16, [3, 4]],
    [[1, 2, 4, 3, 6], 10, [2, 4]],
    [[1, 2, 3, 4, 5, 7, 9], 9, [3, 4], [1, 5]] // [3, 4] 또는 [1, 5] 허용
];

testCases.forEach(([arr, N, expected1, expected2]) => {
    const result = keyPair(arr, N);
    
    let isCorrect = assertDeepStrictEqual(result, expected1);
    let expectedOutput = expected1;

    if (!isCorrect && expected2) {
        isCorrect = assertDeepStrictEqual(result, expected2);
        expectedOutput = `${expected1} 또는 ${expected2}`;
    }

    console.log(`\nkeyPair([${arr.join(', ')}], ${N})`);
    console.log(`  결과: [${result.join(', ')}]`);
    console.log(`  기대: [${expectedOutput}]`);
    console.log(`  테스트: ${isCorrect ? '✅ 성공' : '❌ 실패'}`);
});

console.log('===============================================\n 다른 풀이')
const assert = require('assert')
const keyPair_2 = (arr, sum)=>{
    const myPairIndex = {여성1호:3};
    for(let i = 0; i<arr.length; i++){
        const val = arr[i];
        if(myPairIndex[val]) return [myPairIndex[val], i];
        myPairIndex[sum-val] = i;
    }
};

assert.deepStrictEqual(keyPair_2([1,3,4,5],7), [1,2]);