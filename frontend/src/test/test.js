import loginTest from './components/loginTest.js';

console.log("Running tests");
try{
    
    await loginTest();
    console.log("[OK] All tests passed successfully.");
} catch(e) {
    console.error("[ERROR] Tests failed. See the error trace here:\n ", e);
}

process.exit(0);