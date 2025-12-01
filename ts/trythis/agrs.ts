type FirstArgs<F extends Function> = F extends(...args: infer ARGS) // 여기 unknown 쓰면 안되는 이유?
=> void ? ARGS[0] : never; // never - 함수가 아닌 경우
type SecondArgs<F extends Function> = F extends (...args: infer ARGS)
=> void ARGS[1] : never;
type Args<F extneds Function> = F extneds (...args: infer ARGS) => void
?ARGS

function add(a: number, b: string, c: boolean) {
  return `${a} - ${b} + ${c}`;
}

type A = FirstArgs<typeof add>; // number
type B = SecondArgs<typeof add>; // string
type C = Args<typeof add>;
// number | string | boolean

type AX = Args<typeof String.prototype.endsWith>; // ⇒ string | number | undefined
type AX2 = Args<typeof String.prototype.charAt>; // ⇒ number

let a: A = 0;
let b: B = 'abc';
let c: C = Math.random() > 0.5 ? 1 : 'abc';
console.log('🚀 abc:', a, b, c);
