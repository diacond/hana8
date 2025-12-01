const upperToLower = str => 
    str.replace(/([A-Z])([a-z]*)/g,
        (_,up,low) => `${up.toLowerCase()}${low.
            toUpperCase()}`
        );
asserts.strictEqual(
    upperToLower('abc Senior Coding Learning JS'),
    '*s*-enior *c*oding *l*earning *j*-*s*-'
);

const swapCase = str =>
    str.replace(/([A-Z])([a-z])/, (m, up, low)=>
    console.log(up,low));
asserts.equal(
    swapCase('Senior Coding Learning JS'),
    'sENIOR cODING LEARNING js'
);
asserts.equal(sqapCase('Hararo 8 Class'), 'hANARO 8 cLASS');

