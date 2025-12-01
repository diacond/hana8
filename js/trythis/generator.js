/*
function* gener(){
    const x = yield 'x는?';
    const y = yield (x+10);
    console.log('x y =', x, y);
    return x + y;
}

const it = gener();
console.log('iter<<', it);
const it1 = it.next();
console.log(it1.value);
console.log(it3.next(5));
console.log(it2.value);
if(it2.done) console.log('end');
const it3 = iter.next(100);
if(it3.done) console.log('end');
*/
/*
console.log('??');

rl.on('line', answer => {
  console.log('line.answer>>', answer);
  if (answer === 'bye') rl.close();
}).on('close', () => {
  process.exit();
}); // r1.on의 반환값은 r1임. 그래서 r1.on 한번더 실행된다. 이게 빌더 패턴임. 그거뭐지..
*/
// 200-201 page practice


const assert = require('assert');
class Subway{
    static stations = [ // const??
        '신도림', '성수', '신설동', '용두', '신답', '용답', '시청', '충정로', '아현', 
        '이대', '신촌', '공항철도', '홍대입구', '합정', '당산', '영등포구청', '문래', 
        '대림', '구로디지털단지', '신대방', '신림', '봉천', '서울대입구', '낙성대', 
        '사당', '방배', '서초', '교대', '강남', '역삼', '선릉', '삼성', '종합운동장', 
        '신천', '잠실', '잠실나루', '강변', '구의', '건대입구', '뚝섬', '한양대', 
        '왕십리', '상왕십리', '신당', '동대문역사문화공원', '을지로4가', '을지로3가', 
        '을지로입구'
    ];
    
    constructor(start, end) {
        this.start = start;
        this.end = end;
        this.route = this._calculateRoute(start, end);
        this.currIdx = 0;
    }
    
    _calculateRoute(start, end) {
        const stations = Subway.stations;
        const startIndex = stations.indexOf(start);
        const endIndex = stations.indexOf(end);
        const N = stations.length; 

        if (startIndex === -1 || endIndex === -1) return [];


        let distance = (endIndex - startIndex + N) % N;

        let route = [];

        for (let i = 0; i <= distance; i++) {
            route.push(stations[(startIndex + i) % N]); 
        }

        return route;
    }

    [Symbol.iterator]() {
        let index = 0;
        const route = this.route;
        
        return {
            next() {
                if (index < route.length) {
                    return { value: route[index++], done: false };
                } else {
                    return { value: undefined, done: true };
                }
            }
        };
    }
    iterator() {
        return {
            next: () => {
                if (this.currIdx < this.route.length) {
                    const value = this.route[this.currIdx];
                    this.currIdx++; // next() 호출 시 현재 위치를 한 칸 이동시킴
                    return { value: value, done: false };
                } else {
                    return { value: undefined, done: true };
                }
            }
        };
    }
    toString() {
        // currIdx가 가리키는 현재 역을 출력 (iterator.next()가 호출된 후의 상태 반영)
        // next()가 호출되면 값을 반환하고 인덱스를 증가시키므로, 
        // 현재 역은 증가된 인덱스의 바로 이전 값(또는 현재 보여줘야 할 값)이어야 하나,
        // 문제의 의도상 iterator().next()가 '이동'을 의미하므로 이동 후의 역을 가리키는 로직입니다.
        // 단, 배열 범위 보호를 위해 Math.min 사용
        const currentStation = this.route[Math.min(this.currIdx, this.route.length - 1)];
        return `${this.start}역에서 ${this.end}역까지 가는 열차이며, 현재 ${currentStation}역입니다`;
    }
}





const routes1 = new Subway('문래', '신림');
console.log([...routes1]);
assert.deepStrictEqual(
  [...routes1],
  ['문래', '대림', '구로디지털단지', '신대방', '신림']
);

const it1 = routes1.iterator();
['문래', '대림', '구로디지털단지', '신대방', '신림'].forEach((value, i) => {
  assert.deepStrictEqual(it1.next(), { value, done: false });
  console.log(i, routes1.toString());
});
assert.deepStrictEqual(it1.next(), { value: undefined, done: true });

const routes2 = new Subway('구로디지털단지', '성수'); // 32개 정거장
routes2.iterator().next();
assert.strictEqual(
  routes2.toString(),
  '구로디지털단지역에서 성수역까지 가는 열차이며, 현재 신대방역입니다'
);
console.log([...routes2]); // ['신대방', ..., '성수']
const it2 = routes2[Symbol.iterator]();
while (true) {
  const x = it2.next();
  console.log(x);
  if (x.done) break;
}

const route3 = new Subway('문래', '합정'); // 46개 정거장이면 통과!
assert.strictEqual([...route3].length, 46);
const route4 = new Subway('신도림', '을지로입구'); // 48개 정거장이면 통과!
assert.strictEqual([...route4].length, 48);
