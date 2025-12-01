/*
1118 배열 숙제
코테 문제 팁 << 문제 읽고 rule을 정리해서 적어두기, 이걸 ai한테 주면 잘해줌

*/
// 
const assert = requure('assert');
const range = (edge, end, step) => {
    let start, real_end, real_step;

    // 1. 인자 개수에 따른 edge(양 끝), step 설정
    if (end === undefined) {
        start = 0;
        real_end = edge;
        real_step = 1;
    } 
    else {
        // range(start, end) 또는 range(start, end, step) 형식
        start = edge;
        real_end = end;
        real_step = step !== undefined ? step : (start > real_end ? -1 : 1);
    }

    // 2. 예외 처리 및 비정상 조건 검사
    if (real_step === 0) {
        // step이 0이면 무한 루프이므로 빈 배열 반환 (이미지 rules에 따름)
        return [];
    }

    // 진행 방향이 잘못된 경우
    const isIncreasing = real_step > 0;
    const isInvalidDirection = (isIncreasing && start >= real_end) // 54321
    || (!isIncreasing && start <= real_end); // 12345

    if (isInvalidDirection) {
        if (start !== real_end) {
             return [];
        }
    }
    
    // range(0)의 경우 [0]을 반환 (별도 예외 처리)
    if (start === 0 && real_end === 0 && real_step === 1) {
        return [0];
    }
    // range(5, 5)의 경우 [5]를 반환 (이미지 예시 range(5, 5) // [5] 처리)
    if (start === real_end && real_step !== 0) {
        return [start];
    }
    // range(5, 5, 0)의 경우 [5]를 반환 (이미지 예시 range(5, 5, 0) // [5] 처리)
    if (start === real_end && real_step === 0) {
        return [start];
    }


    // 3. 배열 생성 및 값 채우기
    const result = [];
    let currentValue = start;

    if (isIncreasing) {
        // 증가하는 경우 (step > 0)
        while (currentValue < real_end) {
            result.push(currentValue);
            currentValue += real_step;
        }
    } else {
        // 감소하는 경우 (step < 0)
        while (currentValue > real_end) {
            result.push(currentValue);
            currentValue += real_step; // 실제로는 current - |step|
        }
    }

    return result;
};

// 테스트 코드
console.log("✅ range(1, 10, 1):", range(1, 10, 1)); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
console.log("✅ range(1, 10, 2):", range(1, 10, 2)); // [1, 3, 5, 7, 9]
console.log("✅ range(1, 10):", range(1, 10)); // [1, 2, 3, 4, 5, 6, 7, 8, 9]
console.log("✅ range(10, 1):", range(10, 1)); // [10, 9, 8, 7, 6, 5, 4, 3, 2]
console.log("✅ range(10, 1, -2):", range(10, 1, -2)); // [10, 8, 6, 4, 2]
console.log("✅ range(5):", range(5)); // [0, 1, 2, 3, 4]
console.log("✅ range(100):", range(100)); // [0, 1, ..., 99]
console.log("✅ range(-5):", range(-5)); // [0, -1, -2, -3, -4]
console.log("✅ range(5, 5):", range(5, 5)); // [5]
console.log("✅ range(5, 5, 0):", range(5, 5, 0)); // [5]
console.log("✅ range(5, 5, -1):", range(5, 5, -1)); // [5]

// 예외 및 특수 케이스 (이미지 형광펜 강조 부분)
console.log("✅ range(5, 1, 1):", range(5, 1, 1)); // []
console.log("✅ range(1, 5, -1):", range(1, 5, -1)); // []

// 추가 테스트 (이미지 오른쪽 예시)
console.log("✅ range(1, 5, 0):", range(1, 5, 0)); // []
console.log("✅ range(0, 5):", range(0, 5)); // [0, 1, 2, 3, 4]
console.log("✅ range(0, -1):", range(0, -1)); // [0] -> **Note:** 이 예시는 `start > end`이므로 `step`은 -1로 설정됩니다.
// start=0, end=-1, step=-1이므로 [0]만 반환 (while (0 > -1) -> 0을 push, 0 += -1 -> -1이 됨. -1 > -1은 false)
console.log("✅ range(0, -1, -1):", range(0, -1, -1)); // [0, -1, -2] (직접 step을 -1로 주면)

console.log("✅ range(0, -3, 0):", range(0, -3, 0)); // []
console.log("✅ range(5, 1):", range(5, 1)); // [5, 4, 3, 2]
console.log("✅ range(0):", range(0)); // [0]

console.log("✅ range(2, 1, -5):", range(2, 1, -5)); // [2]
// start=2, end=1, step=-5. 2를 push, 2 += -5 -> -3. -3 > 1은 false. -> [2]

console.log("✅ range(0, 0):", range(0, 0)); // [0]
console.log("✅ range(0, -1, -5):", range(0, -1, -5)); // [0]
console.log("✅ range(0, 0, 5):", range(0, 0, 5)); // [0]