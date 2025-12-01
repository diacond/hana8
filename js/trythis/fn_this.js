// ⇔ function declareFn(name) {
const expressFn = function (name) {
  // if, 'use strict' ?
  this.name = name;
  console.log(this, new.target, this.name, name);
};

const arrowFn = name => {
  this.name = name;
  console.log(this, new.target, this.name, name);
};

// expressFn('expfn');
// arrowFn(afn');

// const dfn = new expressFn('D');
// const afn = new arrowFn('A'); // error!
const Dog = function (name) {
  // console.log(this, new.target, this instanceof Dog);
  this.name = name;
  this.bark = function () {
    console.log('bark=', new.target, this.name, name);
  };
  this.bark2 = () => console.log('bark2=', new.target, this.name, name);
};

const dog = Dog('Doggy');
const lucy = new Dog('Lucy');
// console.log('lucy>>', lucy instanceof Dog, lucy);
// Dog.bark(); // ?
// lucy.bark(); // ?
// lucy.bark2(); // ?
// console.log('type=', typeof dog); // ?
// console.log('type=', typeof lucy); // ?

this.x = 'module';
const Cat = name => {
  console.log('Cat>>', this, new.target);
  this.name = name;

  this.bark = function () {
    console.log('bark=', new.target, this.name, name);
  };

  this.bark2 = () => console.log('bark2=', this.name, name);

  return this;
};

const cat = Cat('Coco');
// console.log(this === cat);
// const cat = new Cat(''); // error!!
cat.bark(); // ?
cat.bark2(); // ?
// Cat.bark(); // ?
console.log('type=', typeof cat); // ?

// cf. FunctionEnvironmentRecord.[[ThisValue]]

console.log('==================');

const debounce = (cb, delay) => {
    let timer;
    return ()=>{
        if(timer) {
            clearTimeout(timer);
            // 앞에 타임아웃을 취소하는 동작
        }
        timer = setTimeout(cb, delay)
        // delay = 200 이후에 실행
    }
}

const f = function(){console.log('f>>', new Date())};
const search = debounce(f, 200);
let cnt = 0;
const intl = setInterval(search, 10);
// 10ms 마다 계속 돌고있음. 근데 마지막 호출되고 나서
// 종료해야하니까 clearTimeout으로 막아둠

const throttle = (cb, delay) => { // 1. (O) delay라는 변수명으로 수정
    let timer;
    return () => {
        if(timer) return;
        timer = setTimeout(() => {
            cb();
            timer = undefined;
        }, delay); // 2. setTimeout 구문이 끝나는 여기에 세미콜론(;)이 필요합니다.
    };
};




