/* 
모든 Array가 다음 기능을 갖도록 구현하세요.
1) mapBy(), findBy(), filterBy(), rejectBy(), sortBy()
2) firstObject, lastObject
p.192 중요한 문제임. 타입스크립트로 열심히 해야하는 문제임...
*/

const assert = require('assert');
const arr = [1, 2, 3, 4, 5];
const hong = { id: 1, name: 'Hing' };
const kim = { id: 2, name: 'Kim' };
const lee = { id: 3, name: 'Lee' };
const users = [hong, lee, kim];

Object.defineProperty(Array.prototype, 'firstObject', {
    get(){
        return this.length > 0 ? this[0] : undefined;
    },
    set(value){
        if(this.lenth > 0){
            this[0] = value;
        } else{
            this.push(value);
        }
    }
});

Object.defineProperty(Array.prototype, 'lastObject', {
    get() {
        return this.length > 0 ? this[this.length - 1] : undefined;
    },
    set(value) {
        if (this.length > 0) {
            this[this.length - 1] = value;
        } else {
            this.push(value); // 배열이 비어있으면 추가
        }
    }
});

Array.prototype.mapBy = function (key) {
    return this.map(item => item && item[key]);
};

Array.prototype.filterBy = function (key, value, isInclude = false) {
    return this.filter(item => {
        if (!item || item[key] === undefined) return false;
        
        const itemValue = String(item[key]);
        const targetValue = String(value);
        return itemValue.toLowerCase().includes(targetValue.toLowerCase());
    });
};

Array.prototype.rejectBy = function (key, value, isInclude = false) {
    return this.filter(item => {
        // (1) 예외 처리: item이 null/undefined이거나 키가 없으면 해당 요소를 유지(true)
        if (!item || item[key] === undefined) return true; 

        const itemValue = String(item[key]);
        const targetValue = String(value);
        // isInclude 모드 (부분 일치 거부)
        if (isInclude) {
            // itemValue가 targetValue를 포함하고 있다면 (즉, 거부 조건 만족), false를 반환하여 '제외'해야 합니다.
            // 포함하지 않을 때만 true(유지)를 반환합니다.
            return !itemValue.toLowerCase().includes(targetValue.toLowerCase());
        } 
        
        // 완전 일치 모드 (완전 일치 거부)
        else {
            // itemValue가 targetValue와 완전히 일치하지 않을 때만 true(유지)를 반환합니다.
            return itemValue !== targetValue;
        }
    });
};

Array.prototype.findBy = function (key, value) {
    return this.find(item => {
        if (!item || item[key] === undefined) return false;
        return String(item[key]) === String(value);
    });
};

Array.prototype.sortBy = function (keySpec){
    const arrCopy = [...this];

    const parts = keySpec.split(':');
    const key = parts[0];
    const direction = parts.length > 1 && parts[1].toLowerCase() === 'desc' ? -1 : 1;
    return arrCopy.sort((a,b) => {
        const valA = a && a[key];
        const valB = b && b[key];

        if(valA === undefined) return 1;
        if(valB === undefined) return -1;

        if(typeof valA === 'string'){
            const comparison = valA.localeCompare(valB);
            return comparison * direction;
        }

        if(valA < valB) return -1 * direction;
        if(valA > valB) return 1 * direction;
        return 0;
    });
};



assert.deepStrictEqual([arr.firstObject, arr.lastObject], [1, 5]);
assert.deepStrictEqual(users.mapBy('id'), [1, 3, 2]); // users.map(u => u['id'])
assert.deepStrictEqual(users.mapBy('name'), ['Hing', 'Lee', 'Kim']);
assert.deepStrictEqual(users.filterBy('id', 2), [kim]);
assert.deepStrictEqual(users.filterBy('name', 'i', true), [hong, kim]); // key, value일부, isInclude
assert.deepStrictEqual(users.rejectBy('id', 2), [hong, lee]);
assert.deepStrictEqual(users.rejectBy('name', 'i', true), [lee]);
assert.deepStrictEqual(users.findBy('name', 'Kim'), kim);
assert.deepStrictEqual(users.sortBy('name:desc'), [lee, kim, hong]);
assert.deepStrictEqual(users.sortBy('name'), [hong, kim, lee]);
assert.deepStrictEqual(users.firstObject, hong);
assert.deepStrictEqual(users.lastObject, kim);
users.firstObject = kim;
assert.deepStrictEqual(users.firstObject, kim);
users.lastObject = hong;
assert.deepStrictEqual(users.lastObject, hong);

