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

  static sfn() {  // Dog.sfn
    return 'SFN';
  }
}
const lucy = new Dog('Lucy');
const { sfn } = Dog;
const { fn } = Dog.prototype;
const { name: aa, fn: fnnn, getName } = lucy;


console.log(aa, sfn(), fnnn(), getName); // ?

console.log(getName.bind(lucy)());
