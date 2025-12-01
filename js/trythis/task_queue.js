setImmediate(() => console.log('setImmediate', new Date()));
setTimeout(() => console.log('setTimeout', new Date()), 0);
// process.nextTick(() => console.log('nextTick'));
process.nextTick(function(){ console.log('nextTick'); });

// i/o polling
const fs = require('fs'); // CJS
fs.readFile('hello.js', result => {
  setTimeout(() => {
    console.log('setTimeout22');
  }, 0);

  setImmediate(() => {
    console.log('setImmediate22');
  });
  process.nextTick(() => console.log('nextTick22'));
});

