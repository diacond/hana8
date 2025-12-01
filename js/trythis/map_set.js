let hong = { id: 1, name: 'Hong' };
const map = new Map([  
    [1, 11], [2, 22]
  ]);
map.set('three', 333);        // { three: 333 }
map.set('four', [1, 2, 3, 4]); // { four: [1,2, 3, 4] }
console.log("🚀 ~ map:", map);
map.set(hong.name, hong);
map.set(hong, hong.name);
console.log(map);  // Map(6) {  1 => 11, 2 => 22, 'three' => 333, 'four' => [ 1, 2, 3, 4 ],    ?, ?}
console.log(map.get(hong));  // 'Hong'
map.delete(hong);   // console.log(hong?.name); // ?  ⇒ hong = null; console.log(map); // ?
console.log('hasHong=', map.has(hong));          // ?
console.log('hasHongName=', map.has(hong?.name)); // ?
map.clear();

map.set(1, 11).set(2, 22).set(3, 33);    // ⇐⇒ new Map([[1, 11], [2, 22], [3, 33]); 
map.entries(); map.keys(); map.values(); // { [ 1, 11 ], [ 2, 22 ], … };  { 1, 2, … };  { 11, 22, … }
const map2 = new Map([...map])           // Map(2) { 1 => 11, 2 => 22, 3 => 33 }
const map3 = new Map([...map, ...map2])  // Map(2) { 1 => 11, 2 => 22, 3 => 33 }

console.log('===================================')

