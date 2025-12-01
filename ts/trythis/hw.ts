// 1. Array 인터페이스 확장 (전역 선언)
export {};

declare global {
  // 전역에서 쓸거라고 선언
  // 선언부. ts는 기본 array에 없는 함수를 쓰면 에러.
  // 먼저 배열에 어떤 함수들을 추가한다고 말해야함.
  interface Array<T> {
    firstObject: T;
    lastObject: T;

    // K는 T의 키(key)들 중 하나여야 함을 명시
    mapBy<K extends keyof T>(prop: K): T[K][];

    // value는 해당 속성의 타입과 일치하거나, includes 검색을 위해 any 허용
    filterBy<K extends keyof T>(prop: K, value: any, isIncludes?: boolean): T[];
    // 여기서 prop이랑 value랑 아무 상관없는 애다. 얘는 어떤 밸류 타입이어도 다 가능하다.
    rejectBy<K extends keyof T>(prop: K, value: any, isIncludes?: boolean): T[];

    findBy<K extends keyof T>(prop: K, value: T[K]): T | undefined;

    // prop은 "key:desc" 형태일 수 있으므로 string으로 받음
    sortBy(prop: string): T[];

    // 그룹화 키는 문자열, 숫자, 심볼이 될 수 있음
    groupBy(
      gfn: (item: T) => string | number | symbol
    ): Record<string | number | symbol, T[]>;
    // key = string, number, symbol// value - T[]
    //
  }
}

// 2. 구현부 (Prototype 확장)

Array.prototype.mapBy = function <T, K extends keyof T>(this: T[], prop: K) {
  return this.map((a) => a[prop]);
};

Array.prototype.filterBy = function <T, K extends keyof T>(
  this: T[],
  prop: K,
  value: T[K],
  isIncludes: boolean = false
) {
  const cb = isIncludes
    ? (a: T) => (a[prop] as any)?.includes?.(value) // includes가 없는 경우
    : (a: T) => a[prop] === value;

  return this.filter(cb);
};

Array.prototype.rejectBy = function <T, K extends keyof T>(
  this: T[],
  prop: K,
  value: T[K],
  isIncludes: boolean = false
) {
  const cb = isIncludes
    ? (a: T) => !(a[prop] as any)?.includes?.(value)
    : (a: T) => a[prop] !== value;

  return this.filter(cb);
};

Array.prototype.rejectBy = function <T>(
  this: T[],
  prop: keyof T,
  value: T[keyof T] & string,
  isIncludes: boolean = false
) {
  const cb = isIncludes
    ? (a: T) => !(a[prop] as any)?.includes?.(value)
    : (a: T) => a[prop] !== value;

  return this.filter(cb);
};

Array.prototype.findBy = function <T, K extends keyof T>(
  this: T[],
  prop: K,
  value: T[K]
) {
  return this.find((a) => a[prop] === value);
};

Array.prototype.sortBy = function <T>(this: T[], prop: string) {
  const [key, direction = "asc"] = prop.split(":");
  const dir = direction.toLowerCase() === "desc" ? -1 : 1;

  return this.sort((a: any, b: any) => {
    if (a[key] > b[key]) return dir;
    if (a[key] < b[key]) return -dir;
    return 0;
  });
};

Array.prototype.groupBy = function <T>(
  this: T[],
  gfn: (item: T) => string | number | symbol
) {
  const ret: Record<string | number | symbol, T[]> = {};
  for (const a of this) {
    const k = gfn(a);
    if (ret[k] === undefined) {
      ret[k] = [];
    }
    ret[k].push(a);
  }
  return ret;
};

// Getter/Setter 정의
Object.defineProperties(Array.prototype, {
  firstObject: {
    get() {
      return this[0];
    },
    set(value) {
      if (this.length > 0) {
        this[0] = value;
      } else {
        // 빈 배열일 경우
        this[0] = value;
      }
    },
  },
  lastObject: {
    get() {
      return this.at(-1);
    },
    set(value) {
      if (this.length > 0) {
        this[this.length - 1] = value;
      } else {
        this[this.length === 0 ? 0 : this.length - 1] = value;
      }
    },
  },
});

interface User {
  id: number;
  name: string;
  dept?: string;
}

const hongx: User = { id: 1, name: "Hong", dept: "Server" };
const kimx: User = { id: 2, name: "Kim", dept: "Server" };
const leex: User = { id: 3, name: "Lee", dept: "Client" };

const users: User[] = [hongx, leex, kimx];

console.log(users.mapBy("id")); // [1, 3, 2];
console.log(users.mapBy("name")); // ['Hong', 'Lee', 'Kim']);
console.log(users.filterBy("id", 2)); // [{id: 2, name: 'Kim', dept: 'Server'}]);
console.log(users.filterBy("name", "i", true)); // [{id: 2, ...}] (Kim에 'i' 포함)
console.log(users.rejectBy("id", 2)); // [hong, lee]
console.log(users.rejectBy("name", "i", true)); // [hong, lee] (Hong, Lee에는 'i'가 없음 - 대소문자 구분 시)
console.log(users.findBy("name", "Kim")); // {id: 2, name: 'Kim'...};
console.log(users.sortBy("name:desc")); // [lee, kim, hong];
console.log(users.sortBy("name")); // [hong, kim, lee]
console.log(users.groupBy((user) => user.dept || "Unknown"));
/*
{
  Server: [
    { id: 1, name: 'Hong', dept: 'Server' },
    { id: 2, name: 'Kim', dept: 'Server' },
  ],
  Client: [
    { id: 3, name: 'Lee', dept: 'Client' }
  ]
}
*/
console.log("first/last=", users.firstObject?.name, users.lastObject?.name);
users.firstObject = kimx;
users.lastObject = hongx;
console.log("first/last=", users.firstObject?.name, users.lastObject?.name);
