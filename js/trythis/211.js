const assert = require('assert');

Object.defineProperties(Array.prototype, {
  firstObject: {
    get() {
      return this[0];
    },
    set(val) {
      this[0] = val;
    },
  },
  lastObject: {
    get() {
      // return this.at(-1)
      return this[this.length - 1];
    },
    set(val) {
      this[this.length - 1] = val;
    },
  },
});
assert.deepStrictEqual([arr.firstObject, arr.lastObject], [1, 5]);

Array.prototype.mapBy = function (prop) {
  return this.map(a => a[prop]);
};
assert.deepStrictEqual(users.mapBy('id'), [1, 3, 2]); // users.map(u => u['id'])
assert.deepStrictEqual(users.mapBy('name'), ['Hing', 'Lee', 'Kim']);

Array.prototype.filterBy = function (prop, value, isIncludes = false) {
  const cb = isIncludes
    ? a => a[prop]?.includes(value)
    : a => a[prop] === value;

  return this.filter(cb);
};

const hong = {id: 1, name: 'Hong', dept: 'HR'};
const kim = {id: 2, name: 'Kim', dept: 'Server'};
const lee = {id: 3, name: 'Lee', dept: 'Front'};
const park = {id: 4, name: 'Park', dept: 'HR'};
const ko = {id: 7, name: 'Ko', dept: 'Server'};
const loon = {id: 6, name: 'Loon', dept: 'Sales'};
const choi = {id: 5, name: 'Choi', dept: 'Front'};
const users = [ hong, kim, lee, park, ko, loon, choi ];

// 3-1. uniqBy: 특정 프로퍼티의 중복되지 않는 값을 배열로 반환
Array.prototype.uniqBy = function (prop) {
  return [...new Set(this.map(item => item[prop]))];
};
assert.deepStrictEqual(users.uniqBy('dept'), 
[ 'HR', 'Server', 'Front', 'Sales' ]);

// 3-2. groupBy: 특정 프로퍼티를 키로 그룹핑 (Object 반환)
/*
Array.prototype.groupBy = function (prop) {
  return this.reduce((acc, item) => {
    const key = item[prop];
    if (!acc[key]) {
      acc[key] = [];
    }
    acc[key].push(item);
    return acc;
  }, {});
};
*/

Array.prototype.groupBy = function (prop) {
    const ret = {'HR':[]};
    for(const a of this){
        const key = a[prop];
        ret[key] = ret[key] ?? [];
        ret[key].push(a);
    }
    return ret;
}

// 3-3. groupByMap: 특정 프로퍼티를 키로 그룹핑 (Map 반환)
Array.prototype.groupByMap = function (prop) {
  return this.reduce((acc, item) => {
    const key = item[prop];
    if (!acc.has(key)) {
      acc.set(key, []);
    }
    acc.get(key).push(item);
    return acc;
  }, new Map());
};

Array.prototype.groupByMap = function (prop) {
  const map = new Map();
  for(const a of this){
    const key = a[prop];
    const val = map.get(key);
    if(val) val.push(a);
    else map.set(key, [val]);
  }
  return map;
};


assert.deepStrictEqual(users.filterBy('id', 2), [kim]);
assert.deepStrictEqual(users.filterBy('name', 'i', true), [hong, kim]); // key, value일부, isInclude
Array.prototype.rejectBy = function (prop, value, isIncludes = false) {
  const cb = isIncludes
    ? a => !a[prop]?.includes(value)
    : a => a[prop] !== value;

  return this.filter(cb);
};
assert.deepStrictEqual(users.rejectBy('id', 2), [hong, lee]);
assert.deepStrictEqual(users.rejectBy('name', 'i', true), [lee]);

Array.prototype.findBy = function (prop, value) {
  return this.find(a => a[prop] === value);
};
assert.deepStrictEqual(users.findBy('name', 'Kim'), kim);

Array.prototype.sortBy = function (prop_asc) {
  const [prop, order = 'asc'] = prop_asc.split(':');
  const dir = order === 'asc' ? 1 : -1;
  return this.sort((a, b) => (a[prop] > b[prop] ? dir : -dir));
};
assert.deepStrictEqual(users.sortBy('name:desc'), [lee, kim, hong]);
assert.deepStrictEqual(users.sortBy('name'), [hong, kim, lee]);
assert.deepStrictEqual(users.firstObject, hong);
assert.deepStrictEqual(users.lastObject, lee);
users.firstObject = kim;
assert.deepStrictEqual(users.firstObject, kim);
users.lastObject = hong;
assert.deepStrictEqual(users.lastObject, hong);

//---------------------
class Dog {
  constructor(name) {
    this.name = name;
  }

  getName() {
    return this.name;
  }

  fn() {
    return 'FN';
  }

  static sfn() {
    // Dog.sfn
    return 'SFN';
  }
}

