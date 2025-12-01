class Emp {
    set fullName(name){
        [this.firstName, this.lastName] = name.split(' ');
    }

    get fullName(){
        return `${this.firstName} ${this.lastName}`;
    }
}

const hong = new Emp();
hong.fullName = 'Kildong Hong';
console.log(hong.fullName);

console.log(Object.getOwnPropertyDescriptor(
    Emp.prototype,'fullName'));

console.log('hong=', hong);

// =============================
const kim = {id : 1, firstName : 'Mik', lastName : 'Kim'};
const proxyObj = new Proxy(kim, {
    get(target, prop, receiver){ // receiver << this임 proxyObj말하는거임
        // x = target.fullName 읽어온다.
        console.log('receiver>>', receiver === proxyObj) // 잘받아왔나 검사
        if(prop === 'fullName'){
            return `${target.firstName} ${target.lastName}`;
        }
        return target[prop];
    },
    // target.fullName = x
    set(target, prop, value, receiver){
        if(prop === 'fullName'){
            [target.firstName, target.lastName] = value.split
            (' ');
        }
        else{
            target[prop] = value;
        }
    }   
});
console.log('=========================')
console.log(proxyObj.fullName, kim);
console.log(kim.id)
console.log(proxyObj instanceof Emp);
console.log(proxyObj instanceof Proxy);

Object.defineProperties(Emp.prototype,{
    upperName: {
        get(){
            return this.fullName.toUpperCase();
        },
    },
    lowerName: {
        get: function(){
            return this.FullName.toLowerCase();
        },
    },
});
Emp.prototype.nameLength = function(){
    return this.fullName.length;
};
console.log('upper>>', hong.upperName);
console.log('upper>>', hong.lowerName);
console.log('nameLen>>', hong.nameLength);

console.log('-----------------------------------------------')

class Pet{
    feed(nutrient){
        console.log(`feed to ${this.name} : `, nutrient);
    }
}
Object.assign(Emp.prototype, {feed: Pet.prototype.feed});
hong.feed('xxxx');

console.log('-----------------------------------------------')

/* 
모든 Array가 다음 기능을 갖도록 구현하세요.
1) mapBy(), findBy(), filterBy(), rejectBy(), sortBy()
2) firstObject, lastObject
p.192
*/

const assert = requure('assert');
const arr = [1, 2, 3, 4, 5];
const hong = { id: 1, name: 'Hing' };
const kimihi = { id: 2, name: 'Kimihi' };
const lee = { id: 3, name: 'Lee' };
const users = [hong, lee, kim];

Object.defineProperties(Array.prototype, 'firstObject', {
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
        if (!item || item[key] === undefined) return true; // 키가 없으면 유지
        
        const itemValue = String(item[key]);
        const targetValue = String(value);
        return itemValue !== targetValue;
    });
}

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
            const comparison = valA.localeCompoare(valB);
            return comparison * direction;
        }

        if(valA < valB) return -1 * direction;
        if(valA > valB) return 1 * direction;
        return 0;
    });
};



assert.deepStrictEqual([arr.firstObject, arr.lastObject], [1, 5]);
assert.deepStrictEqual(users.mapBy('id'), [1, 3, 2]); // users.map(u => u['id'])
assert.deepStrictEqual(users.mapBy('name'), ['Hing', 'Lee', 'Kimihi']);
assert.deepStrictEqual(users.filterBy('id', 2), [kim]);
assert.deepStrictEqual(users.filterBy('name', 'i', true), [hong, kim]); // key, value일부, isInclude
assert.deepStrictEqual(users.rejectBy('id', 2), [hong, lee]);
assert.deepStrictEqual(users.rejectBy('name', 'i', true), [lee]);
assert.deepStrictEqual(users.findBy('name', 'Kimihi'), kim);
assert.deepStrictEqual(users.sortBy('name:desc'), [lee, kim, hong]);
assert.deepStrictEqual(users.sortBy('name'), [hong, kim, lee]);
assert.deepStrictEqual(users.firstObject, hong);
assert.deepStrictEqual(users.lastObject, kim);
users.firstObject = kim;
assert.deepStrictEqual(users.firstObject, kim);
users.lastObject = hong;
assert.deepStrictEqual(users.lastObject, hong);
