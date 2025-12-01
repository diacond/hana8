// const SIZE = [
//   { id: "XS", price: 8000 },
//   { id: "S", price: 10000 },
//   { id: "M", price: 12000 },
//   { id: "L", price: 14000 },
//   { id: "XL", price: 15000 },
// ] as const;

// type SizeType = (typeof SIZE)[number]["id"]; // 여기서는 인덱스

// type SizeMap = {
//   [key in SizeType]: number;
// }; // number 가 위에랑은 다르게 쓰임. 여기서는 price

// // const sizeOption1 = { XS: 1, S: 5, M: 2, L: 2, XL: 4 };
// // type Q = keyof typeof sizeOption1;
// // 이런 방법도 있음.

// const sizeOption1: SizeMap = { XS: 1, S: 5, M: 2, L: 2, XL: 4 };

// const totalPrice1 = SIZE.reduce(
//   (currPrice, size) => currPrice + sizeOption1[size.id] * size.price,
//   0
// );
// console.log("🚀 ~ totalPrice1:", totalPrice1);

// const sizeOption2: SizeMap = { XS: 2, S: 3, M: 4, L: 5, XL: 6 };
// const totalPrice2 = SIZE.reduce(
//   (currPrice, size) => currPrice + sizeOption2[size.id] * size.price,
//   0
// );
// console.log("🚀 ~ totalPrice2:", totalPrice2);
const SIZE = [
  { id: "XS", price: 8000 },
  { id: "S", price: 10000 },
  { id: "M", price: 12000 },
  { id: "L", price: 14000 },
  { id: "XL", price: 15000 },
] as const;
const sizeOption1 = { XS: 1, S: 5, M: 2, L: 2, XL: 4 };
const totalPrice1 = SIZE.reduce(
  (currPrice, size) => currPrice + sizeOption1[size.id] * size.price,
  0
);
console.log(totalPrice1);

const sizeOption2 = { XS: 2, S: 3, M: 4, L: 5, XL: 6 };
const totalPrice2 = SIZE.reduce(
  (currPrice, size) => currPrice + sizeOption2[size.id] * size.price,
  0
);
console.log(totalPrice2);
const sizeOption = { XS: 1, S: 5, M: 2, L: 2, XL: 4 };
const totalPrice = SIZE.reduce(
  (currPrice, size) => currPrice + sizeOption[size.id] * size.price,
  0
);
console.log(totalPrice); // 간단히 as const로도 해결된다.

// interface User {
//   id: number;
//   name: string;
// }

// interface Dept {
//   id: number;
//   dname: string;
//   captain: string;
// }
// //type Ud2 = (User | Dept) & {addr:string};
// type UdT = User & Dept // 다중 상속
// // interface Ud2 extends Partial<UdT>{
// //   //id:number;
// //   addr: string;
// // } // partial << 모든 걸 optional로!
// interface Ud2{
//     id:number;
//     [x:string]:number|string;
//     addr: string;
// }

// // 다음 코드가 오류가 없으면 통과!
// const ud2: Ud2 = {id: 1, name: 'HH', addr: 'Seoul'};
// const ud3: Ud2 = {id: 1, dname: 'HH', captain: 'HH', addr: 'Seoul'};
