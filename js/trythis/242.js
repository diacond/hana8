const assert = require('assert'); // Node.js 환경에서 테스트 확인용

// --- Solution 1: 대소문자 변환 ---
const swapCase = (str) => {
    // 문자열을 전개 연산자([...str])로 배열로 만든 뒤 map으로 순회
    return [...str].map(char => 
        // 문자가 원본과 대문자가 같다면(즉, 대문자라면) 소문자로, 아니면 대문자로 변환
        char === char.toUpperCase() ? char.toLowerCase() : char.toUpperCase()
    ).join('');
};

// --- Solution 2: 전화번호 포맷팅 ---
const telfmt = (str) => {
    const tel = str.replace(/[^0-9]/g, '');
    const len = tel.length;

    if (len === 8) {
        return tel.replace(/^(\d{4})(\d{4})$/, '$1-$2');
    }

    if (tel.startsWith('02')) {
        return tel.replace(/^(02)(\d{3,4})(\d{4})$/, '$1-$2-$3');
        // 별로임 이거 다시
    }

    // 3. 안심번호/인터넷 전화 등 (12자리: 0507-1234-5678)
    if (len === 12) {
        return tel.replace(/^(\d{4})(\d{4})(\d{4})$/, '$1-$2-$3');
    }

    // 4. 일반 휴대폰 및 기타 지역 번호 (10~11자리: 010-1234-5678)
    // 3자리 국번 + 3~4자리 중간번호 + 4자리 뒷번호
    return tel.replace(/^(\d{3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
};

// --- Test Cases (검증) ---

console.log("Running Tests...");

try {
    // 1. swapCase 테스트
    assert.equal(swapCase('Senior Coding Learning JS'), 'sENIOR cODING lEARNING js');
    assert.equal(swapCase('Hanaro 8 Class'), 'hANARO 8 cLASS');
    console.log("✅ swapCase Pass");

    // 2. telfmt 테스트
    assert.deepStrictEqual(telfmt('0101234567'), '010-123-4567');       // 10자리 휴대폰 (구)
    assert.deepStrictEqual(telfmt('01012345678'), '010-1234-5678');     // 11자리 휴대폰
    assert.deepStrictEqual(telfmt('0212345678'), '02-1234-5678');       // 10자리 서울
    assert.deepStrictEqual(telfmt('021234567'), '02-123-4567');         // 9자리 서울
    assert.deepStrictEqual(telfmt('0331234567'), '033-123-4567');       // 10자리 지방
    assert.deepStrictEqual(telfmt('15771577'), '1577-1577');            // 8자리 대표번호
    assert.deepStrictEqual(telfmt('07012341234'), '070-1234-1234');     // 11자리 인터넷전화
    assert.deepStrictEqual(telfmt('050712345678'), '0507-1234-5678');   // 12자리 안심번호
    console.log("✅ telfmt Pass");

    console.log("🎉 All tests passed successfully!");

} catch (e) {
    console.error("❌ Test Failed:", e.message);
    console.error("Expected:", e.expected);
    console.error("Actual:", e.actual);
}