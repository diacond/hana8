// const randTime = val =>
//   new Promise((resolve, reject) => {
//     const delay = Math.random() * 1000;
//     console.log(`randTime>> ${val} ${delay}`);

//     setTimeout(() => {
//       resolve(val);
//     }, delay);
//   });

// randTime(100).then(console.log);

// console.log('=================')

// const depthTimer = (depth) => {
//   return new Promise((resolve, reject) => {
//     setTimeout(() => {
//       console.log(`depth${depth}`, new Date());
      
//       if (depth >= 3) {
//         reject(new Error('Already 3-depth!!'));
//       } else {
//         resolve(depth + 1);
//       }
//     }, depth * 1000);
//   });
// };

// console.log('START!', new Date());
// depthTimer(1)
//   .then(depthTimer) 
//   .then(depthTimer) 
//   .catch(console.error);

// console.log('=================')

// const randTime = 

// promiseAll([randTime(1), randTime(2), randTime(3)]).then(arr => {
//   console.table(arr);
//   assert.deepStrictEqual(arr, [1, 2, 3]);
// }).catch(console.error);

// promiseAll([randTime(11), Promise.reject('RRR'), randTime(33)])
//   .then(array => {
//     console.log('여긴 과연 호출될까?!');
//   })
//   .catch(error => {
//     console.log('reject!!!!!!>>', error);
//   });

const randTime = sec => new Promise((resolve, reject) =>{
    console.log('randTime: ', sec)
    setTimeout(resolve,sec*1000 * Math.random(), sec);
});

const promiseAll = (parr) => new Promise((resolve,reject) => {
    let runCnt = 0;
    const results = [];
    for(let i = 0; i < parr; i++){
        parr[i].then(res => {
            results[i] = res;
            if(++runCnt === parr.length) resolves(results);
        }).catch(reject);
    }
})

promiseAll([randTime(11), Promise.reject('RRR'), randTime(33)])
  .then(array => {
    console.log('여긴 과연 호출될까?!');
  })
  .catch(error => {
    console.log('reject!!!!!!>>', error);
  });

const promiseAllSettled = parr => new Promise((resolve, reject) => {
    const results = [];
    let runCnt = 0;
    for(let i = 0; i< parr.length; i++){
        parr[i].then(value => {
            results[i] = {status : 'fuff', value};
        }).catch(reason => {
            results[i] = {status : 'rejected', reason};
        }).finally(() => {
            if(++runCnt === parr.length) resolve(results);
        })
    }
})

const f = async () => {
  const res = await fetch("https://jsonplaceholder.typicode.com/users/1");

  if (!res.ok) throw new Error("Failt to Fetch!!");

    await new Promise(resolve => setTimeout(resolve,2000));

  const data = await res.json();

  return data.name;
};

console.log(await f());



function iter(vals) {
  let i = -1;
  return {
    async next() { // async next = await을 써주는게 맞다.     
        // async - await 쓰는게 맞음
                                                       
      i += 1;
      return { value: await afterTime(vals[i]), done: i >= 3 };
    },
  };
}
(async function(){
    const it = iter([1, 2, 3]);
    console.time('iter'); 
    const {value} = it.next();
    console.log('1=', await it.next());
    console.log('2=', await it.next());
    console.log('3=', await it.next());
    console.log('4=', await it.next());
    console.timeEnd('iter');
})();
