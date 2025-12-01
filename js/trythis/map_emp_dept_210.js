
/*const hrTeam = {id: 1, dname: '인사팀'};  
const devTeam = {id: 2, dname: '개발팀'};
const depts = [ hrTeam, devTeam ];  
const hong = {id: 1, name: 'Hong', dept: 1};  // hong.dept.dname ⇒ deptMap.get(hong.dept)?.dname
const kim = {id: 2, name: 'Kim', dept: 2};
const emps = [ 
    hong, kim, 
    {id:3, name: 'Park', dept: 2},
    {id: 4, name: 'Choi', dept: 2}
];
const x = depts.map(dept => [dept.id, dept]);

console.log("🚀 ~ x:", x)
const deptMap = new Map();
console.log(deptMap); // Map(2) { 1 => { id: 1, dname: '인사팀' }, 2 => { id: 2, dname: '개발팀' } }  ⇐ deptMap.get(2)
assert.deepStrictEqual([...deptMap.values()], depts);
console.log(empMap); // Map(2) { 1 => {id: 1, name: 'Hong', dept: 1}, 2 => {id: 2, name: 'Kim', dept: 2}, … }
assert.deepStrictEqual([...empMap.keys()], emps.map(emp => emp.id));
assert.deepStrictEqual([...empMap.values()], emps);

console.log(empDept); // Map(4) { { id: 1, name: 'Hong' } => { id: 1, dname: '인사팀' }, { id: 2, name: 'Kim' } => { id: 2, dname: '개발팀' }, { id: 3, name: 'Park' } => { id: 2, dname: '개발팀' }, { id: 4, name: 'Choi' } => { id: 2, dname: '개발팀' } }

console.log(empDept.get(kim).dname); // '개발팀'
// 개발팀 직원 목록 출력 ⇒ Kim, Park, Choi

assert.deepStrictEqual(
  [...empDept.keys()],
  emps.map(({ id, name }) => ({ id, name }))
);
assert.strictEqual(empDept.get(kim)?.dname, devTeam.dname);
*/
const hong = {id: 1, name: 'Hong', dept: 'HR'};
const kim = {id: 2, name: 'Kim', dept: 'Server'};
const lee = {id: 3, name: 'Lee', dept: 'Front'};
const park = {id: 4, name: 'Park', dept: 'HR'};
const ko = {id: 7, name: 'Ko', dept: 'Server'};
const loon = {id: 6, name: 'Loon', dept: 'Sales'};
const choi = {id: 5, name: 'Choi', dept: 'Front'};
const users = [ hong, kim, lee, park, ko, loon, choi ];
users.uniqBy('dept'); // [ 'HR', 'Server', 'Front', 'Sales' ]

Array.prototype.uniqBy = function(prop){

}

AuthenticatorAssertionResponse.deepStrictEqual

