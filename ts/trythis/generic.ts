// type User = {
//   id: number;
//   name: string;
//   12: number;
// };

// // 1) key가 number 타입이면(12) key앞에 user_를 붙이세요.
// type UserNumKeyPrefix = {
//   [k in keyof User as k extends number ? `user_${k}` : k]: User[k];
// };
// /* 결과:
// {
//   id: number;
//   name: string;
//   user_12: number;
// }
// */

// // 2) key가 string 타입인 것만 남기세요.
// type UserOnlyStrKey = {
//   [k in keyof User as k extends string ? k : never]: User[k];
// };
// // extract 쓸수도있음. 이거는 exclude의 반대 기능
// /* 결과:
// {
//   id: number;
//   name: string;
//   12는 string이 아니라서 제거됨(never)
// }
// */

// // 3) key가 string 타입인 것만 남기고 prefix(user_)를 붙이세요.
// type UserOnlyStrKeyPrefix = {
//   [k in keyof User as k extends string ? `user_${k}` : never]: User[k];
// };
//========================
// interface IUser {
//   id: number;
//   age: number;
//   name: string;
// }

// interface IDept {
//   id: number;
//   age: string;
//   dname: string;
//   captain: string;
// }

// type Change<T, K extends keyof T, U> = {
//   [k in keyof T]: k extends K ? U : T[k];
// };
// type DeptCaptain = Change<IDept, "captain", IUser>;
// type Err = Change<IDept, "xxx", IUser>;

//========================

interface IUser {
  id: number;
  age: number;
  name: string;
}

interface IDept {
  id: number;
  age: string;
  dname: string;
  captain: string;
}

type Combine<T, U> = {
  // 1. T와 U의 모든 키를 다 가져와서 루프를 돌립니다.
  [k in keyof T | keyof U]: k extends keyof T // 2. 만약 k가 T의 키라면? // k in keyof (T & U)
    ? k extends keyof U
      ? T[k] | U[k]
      : T[k] // 2-1. U의 키도 되면 -> 둘 다 합침 (Union)
    : // 2-2. U의 키는 아니면 -> T 타입만 사용
    k extends keyof U
    ? U[k]
    : never; // 3. T의 키가 아니면(즉 U에만 있으면) -> U 타입만 사용
};
type ICombined = Combine<IUser, IDept>;
// ========================================
type X = IUser & IDept;
