exports.config = {
    directConnect: true,
    capabilities:{
        'browserName': 'chrome'},
    framework: 'jasmine2',
    specs: ['test.js'],
    onPrepare: async () => {
    await browser.waitForAngularEnabled(false);
    }
};

//yoni